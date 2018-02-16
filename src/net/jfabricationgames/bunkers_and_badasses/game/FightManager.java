package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.error.GameLockException;
import net.jfabricationgames.bunkers_and_badasses.game.GameLock.LockType;
import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.EmptyBuilding;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.TinyTinasMine;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero.ExecutionType;
import net.jfabricationgames.bunkers_and_badasses.game_command.RetreatCommand;
import net.jfabricationgames.bunkers_and_badasses.game_communication.FightTransfereMessage;
import net.jfabricationgames.bunkers_and_badasses.game_frame.FightExecutionFrame;
import net.jfabricationgames.bunkers_and_badasses.game_frame.SupportRequestFrame;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoal;
import net.jfabricationgames.bunkers_and_badasses.statistic.GameStatistic;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.client.JFGClient;

public class FightManager implements Serializable {
	
	private static final long serialVersionUID = 1371856895594412645L;
	
	private Map<Integer, List<Fight>> fights;//executed fights sorted by the game turns
	
	private Fight currentFight;
	private transient FightExecutionFrame fightExecutionFrame;
	
	//private transient User localPlayer;	
	private transient List<User> players;
	
	private transient GameTurnBonusManager gameTurnBonusManager;
	private transient GameTurnGoalManager gameTurnGoalManager;
	private PointManager pointManager;
	private transient TurnExecutionManager turnExecutionManager;
	//private Board board;
	
	private Game game;
	
	private transient JFGClient client;
	
	public FightManager(JFGClient client, Game game, List<User> players, GameTurnBonusManager gameTurnBonusManager, 
			GameTurnGoalManager gameTurnGoalManager, PointManager pointManager, TurnExecutionManager turnExecutionManager, Board board) {
		this.client = client;
		//this.localPlayer = localPlayer;
		this.game = game;
		this.players = players;
		this.gameTurnBonusManager = gameTurnBonusManager;
		this.gameTurnGoalManager = gameTurnGoalManager;
		this.pointManager = pointManager;
		this.turnExecutionManager = turnExecutionManager;
		//this.board = board;
		fights = new HashMap<Integer, List<Fight>>();
	}
	
	/**
	 * Merge the data from the new fight manager.
	 * 
	 * @param fightManager
	 * 		The new fight manager.
	 */
	public void merge(FightManager fightManager) {
		//the fight can be overwritten because this message is not sent during a fight
		this.currentFight = fightManager.getCurrentFight();
	}
	
	/**
	 * Start a new fight and inform the other players.
	 * 
	 * @param startField
	 * 		The field from which the fight was stared.
	 * 
	 * @param targetField
	 * 		The field that is fought for.
	 * 
	 * @param normalTroops
	 * 		The normal troops the attacking player wants to use.
	 * 
	 * @param badassTroops
	 * 		The badass troops the attacking player wants to use.
	 */
	public void startFight(Field startField, Field targetField, int normalTroops, int badassTroops) throws GameLockException {
		//check whether the target field is locked by Arschgaul's effect
		User defender = targetField.getAffiliation();
		List<GameLock> locks = game.getGameLockManager().getGameLocks(defender, targetField);
		if (!locks.isEmpty()) {
			GameLock searchedLock = new GameLock(null, 0, LockType.ARSCHGAULS_FIELD_LOCK, targetField);
			int lockId = locks.indexOf(searchedLock);
			if (lockId != -1) {
				throw new GameLockException("The field is locked by Arschgauls effect and can not be attacked.", locks.get(lockId).getErrorMessage());
			}			
		}
		currentFight = new Fight();
		currentFight.setAttackingField(startField);
		currentFight.setDefendingField(targetField);
		currentFight.setAttackingNormalTroops(normalTroops);
		currentFight.setAttackingBadassTroops(badassTroops);
		currentFight.setAttackingPlayer(startField.getAffiliation());
		currentFight.setDefendingPlayer(targetField.getAffiliation());
		currentFight.calculateCurrentStrength();
		if (targetField.getCommand() instanceof RetreatCommand && currentFight.retreatPossible()) {
			//the attacked player retreated -> let him choose the retreat field
			currentFight.setBattleState(Fight.STATE_RETREAT_FIELD);
			currentFight.setWinner(Fight.ATTACKERS);
			currentFight.setFallingTroopsChosen(true);
			currentFight.setFallingTroopsTotal(0);
			currentFight.setFallingTroopsLooser(0);
			currentFight.setFallingTroopsSupport(new HashMap<Field, Integer>());
			Map<Field, int[]> fallenTroops = new HashMap<Field, int[]>();
			fallenTroops.put(startField, new int[2]);
			fallenTroops.put(targetField, new int[2]);
			currentFight.setFallenTroops(fallenTroops);
			currentFight.setFallenTroopsChosen(true);
			fightExecutionFrame.clearAll();//resets stored fields
			fightExecutionFrame.setVisible(true);
			fightExecutionFrame.requestFocus();
			fightExecutionFrame.update();
		}
		else {
			if (targetField.getAffiliation() == null) {
				currentFight.setDefendingHeroChosen(true);//skags can't chose heros
				currentFight.setAttackingHeroChosen(true);
			}
			else {
				//check if one of the players has a hero lock (Angels effect)
				List<GameLock> attackersLocks = game.getGameLockManager().getGameLocks(currentFight.getAttackingPlayer());
				List<GameLock> defendersLocks = game.getGameLockManager().getGameLocks(currentFight.getDefendingPlayer());
				GameLock searchedLock = new GameLock(null, 0, LockType.ANGELS_HERO_LOCK, null);//error message and counter are not checked in equals
				if (attackersLocks.contains(searchedLock)) {
					//defender can't use heros against this attacker
					currentFight.setDefendingHero(null);
					currentFight.setDefendingHeroChosen(true);
				}
				if (defendersLocks.contains(searchedLock)) {
					//attacker can't use heros against this defender
					currentFight.setAttackingHero(null);
					currentFight.setAttackingHeroChosen(true);
				}
			}
			addPossibleSupportFields();
			if (currentFight.getPossibleSupporters().isEmpty()) {
				currentFight.setBattleState(Fight.STATE_HEROS);
				if (currentFight.getDefendingField().getAffiliation() == null) {
					update();
				}
			}
			fightExecutionFrame.clearAll();//resets stored fields
			fightExecutionFrame.setVisible(true);
			fightExecutionFrame.requestFocus();
			fightExecutionFrame.update();
			showSupportRequests();
		}
		FightTransfereMessage message = new FightTransfereMessage(currentFight, true);
		client.sendMessage(message);
	}
	
	/**
	 * End the fight by calculating all players points, moving the troops and ending the players turn.
	 */
	public void endFight() {
		giveOutPoints();
		//remove the fallen troops
		int[] fallenTroops;
		Field localField;
		int[] totalKilledTroops = new int[2];
		int neutralTroopsKilled = 0;
		//the fields are sometimes added twice; fix it by remembering the names...
		List<String> troopsRemoved = new ArrayList<String>();//the names of all fields where troops were removed
		for (Field field : currentFight.getFallenTroops().keySet()) {
			if (troopsRemoved != null && field != null && !troopsRemoved.contains(field.getName())) {
				fallenTroops = currentFight.getFallenTroops().get(field);
				localField = game.getBoard().getFieldByName(field.getName());
				//System.out.println("Removing troops from " + localField.getName() + ": " + fallenTroops[0] + ", " + fallenTroops[1]);
				try {
					localField.removeNormalTroops(fallenTroops[0]);
					localField.removeBadassTroops(fallenTroops[1]);
					if (localField.getAffiliation() != null && !localField.getAffiliation().equals(currentFight.getAttackingPlayer())) {
						totalKilledTroops[0] += fallenTroops[0];
						totalKilledTroops[1] += fallenTroops[1];
					}
					else if (localField.getAffiliation() == null) {
						neutralTroopsKilled += fallenTroops[0];
					}
				}
				catch (IllegalArgumentException iae) {
					iae.printStackTrace();
				}
				troopsRemoved.add(field.getName());
			}
		}
		//add statistics
		GameStatistic stats = game.getStatisticManager().getStatistics(currentFight.getAttackingPlayer());
		GameStatistic statsDefender = game.getStatisticManager().getStatistics(currentFight.getDefendingPlayer());
		stats.setTroops_killed_normal(stats.getTroops_killed_normal() + totalKilledTroops[0]);
		stats.setTroops_killed_badass(stats.getTroops_killed_badass() + totalKilledTroops[1]);
		stats.setTroops_killed_neutral(stats.getTroops_killed_neutral() + neutralTroopsKilled);
		if (currentFight.getWinner() == Fight.ATTACKERS) {
			stats.setBattles_won(stats.getBattles_won() + 1);
			if (statsDefender != null) {
				statsDefender.setBattles_lost(statsDefender.getBattles_lost() + 1);
			}
		}
		else {
			stats.setBattles_lost(stats.getBattles_lost() + 1);
			if (statsDefender != null) {
				statsDefender.setBattles_won(statsDefender.getBattles_won() + 1);
			}
		}
		for (Field field : currentFight.getAttackSupporters()) {
			GameStatistic statsSupporter = game.getStatisticManager().getStatistics(field.getAffiliation());
			if (field.getAffiliation().equals(currentFight.getAttackingPlayer())) {
				statsSupporter.setSupport_given_self(statsSupporter.getSupport_given_self() + 1);
				stats.setSupport_received_self(stats.getSupport_received_self() + 1);
			}
			else {
				statsSupporter.setSupport_given_other(statsSupporter.getSupport_given_other() + 1);
				stats.setSupport_received_other(stats.getSupport_received_other() + 1);
			}
		}
		for (Field field : currentFight.getDefenceSupporters()) {
			GameStatistic statsSupporter = game.getStatisticManager().getStatistics(field.getAffiliation());
			if (field.getAffiliation().equals(currentFight.getDefendingPlayer())) {
				statsSupporter.setSupport_given_self(statsSupporter.getSupport_given_self() + 1);
				statsDefender.setSupport_received_self(statsDefender.getSupport_received_self() + 1);
			}
			else {
				statsSupporter.setSupport_given_other(statsSupporter.getSupport_given_other() + 1);
				statsDefender.setSupport_received_self(statsDefender.getSupport_received_other() + 1);
			}
		}
		for (Field field : currentFight.getSupportRejections()) {
			GameStatistic statsRejection = game.getStatisticManager().getStatistics(field.getAffiliation());
			statsRejection.setSupport_rejected(statsRejection.getSupport_rejected() + 1);
		}
		//move the attacking troops to the new field and the loosing troops to the retreat field 
		if (currentFight.getWinner() == Fight.ATTACKERS) {
			//give out points for movements
			game.getGameTurnGoalManager().receivePointsMoving(game.getLocalUser(), currentFight.getAttackingField(), true);
			//move the loosing troops to the retreat field
			Field retreatField = null;
			if (currentFight.getRetreatField() != null) {
				retreatField = game.getBoard().getFieldByName(currentFight.getRetreatField().getName());//right retreat field reference
			}
			Field defendingField = currentFight.getDefendingField();
			Field attackingField = currentFight.getAttackingField();
			if (retreatField != null) {
				game.getBoard().moveTroops(defendingField, retreatField, defendingField.getNormalTroops(), defendingField.getBadassTroops());
			}
			else {
				defendingField.removeAllTroops();
			}
			//move the surviving attackers to their new field
			fallenTroops = currentFight.getFallenTroops().get(attackingField);
			int[] movingTroops = {currentFight.getAttackingNormalTroops() - fallenTroops[0], currentFight.getAttackingBadassTroops() - fallenTroops[1]};
			game.getBoard().moveTroops(attackingField, defendingField, movingTroops[0], movingTroops[1]);
			//make the mine explode if there is one on the field
			if (defendingField.getBuilding() != null && defendingField.getBuilding() instanceof TinyTinasMine) {
				int mineVictims = defendingField.getBuilding().getLandMineVictims();
				//kill two invading troops
				if (defendingField.getNormalTroops() >= mineVictims) {
					//two normal troops
					defendingField.removeNormalTroops(mineVictims);
				}
				else if (defendingField.getNormalTroops() > 0) {
					//one normal and one badass troops
					int normalTroops = defendingField.getNormalTroops();
					defendingField.removeNormalTroops(normalTroops);
					if (defendingField.getBadassTroops() > 0) {
						defendingField.removeBadassTroops(mineVictims - normalTroops);
					}
				}
				else {
					//up to two badasses
					for (int i = 0; i < mineVictims; i++) {
						if (defendingField.getBadassTroops() > 0) {
							defendingField.removeBadassTroops(1);
						}						
					}
				}
				//remove the mine
				defendingField.setBuilding(new EmptyBuilding());
			}
			//remove any command that could be on the conquered field
			defendingField.setCommand(null);
		}
		else {
			//defenders won
			int[] fallen = currentFight.getFallenTroops().get(currentFight.getAttackingField());
			int normalTroopsRetreat = currentFight.getAttackingNormalTroops() - fallen[0];
			int badassTroopsRetreat = currentFight.getAttackingBadassTroops() - fallen[1];
			game.getBoard().moveTroops(currentFight.getAttackingField(), currentFight.getRetreatField(), normalTroopsRetreat, badassTroopsRetreat);
		}
		//hero effects
		currentFight.executeHeroEffects(ExecutionType.AFTER_FIGHT);
		//remove the used heros from the players hands
		if (currentFight.getAttackingHero() != null) {
			game.getHeroCardManager().heroCardUsed(currentFight.getAttackingHero(), currentFight.getAttackingPlayer());
			if (currentFight.isUseAttackingHeroEffect()) {
				stats.setHeros_used_effect(stats.getHeros_used_effect() + 1);
			}
			else {
				stats.setHeros_used_battle(stats.getHeros_used_battle() + 1);
			}
		}
		if (currentFight.getDefendingHero() != null) {
			game.getHeroCardManager().heroCardUsed(currentFight.getDefendingHero(), currentFight.getDefendingPlayer());
			if (statsDefender != null) {
				if (currentFight.isUseDefendingHeroEffect()) {
					statsDefender.setHeros_used_effect(statsDefender.getHeros_used_effect() + 1);
				}
				else {
					statsDefender.setHeros_used_battle(statsDefender.getHeros_used_battle() + 1);
				}
			}
		}
		game.setState(GameState.ACT);
		//update the statistics
		game.getStatisticManager().updateValues(game);
		//end the players turn
		game.getPlayerOrder().nextMove();
		game.getGameFrame().updateAllFrames();
		turnExecutionManager.commit();
		currentFight = null;//delete the current fight
	}
	
	public void giveOutPoints() {
		//points for attacker and winner
		pointManager.addPoints(currentFight.getAttackingPlayer(), Game.getGameVariableStorage().getFightAttackerPoints(), getClass(), "attacking player", PointManager.PointType.FIGHT);
		if (currentFight.getWinningPlayer() != null) {//skags could win
			pointManager.addPoints(currentFight.getWinningPlayer(), Game.getGameVariableStorage().getFightWinnerPoints(), getClass(), "fight winning player", PointManager.PointType.FIGHT);
		}
		//points for supporters
		List<User> supporters = new ArrayList<User>();
		if (currentFight.getWinner() == Fight.ATTACKERS) {
			for (Field field : currentFight.getAttackSupporters()) {
				//no points for self-support
				if (!field.getAffiliation().equals(currentFight.getAttackingPlayer())) {
					supporters.add(field.getAffiliation());
				}
			}
		}
		else {
			for (Field field : currentFight.getDefenceSupporters()) {
				if (currentFight.getDefendingPlayer() != null && !field.getAffiliation().equals(currentFight.getDefendingPlayer())) {
					supporters.add(field.getAffiliation());
				}
			}
		}
		for (User user : supporters) {
			pointManager.addPoints(user, Game.getGameVariableStorage().getFightSupporterPoints(), getClass(), "fight supporting player", PointManager.PointType.FIGHT);
		}
		//points for turn goals and turn bonuses
		TurnBonus bonus;
		TurnGoal goal = gameTurnGoalManager.getTurnGoal();
		for (User player : players) {
			bonus = gameTurnBonusManager.getUsersBonus(player);
			bonus.receivePointsFight(player, currentFight);
			goal.receivePointsFight(player, currentFight);
		}
	}
	
	/**
	 * Update the current fight and then send an update.
	 */
	public void update() {
		boolean fightEnded = false;
		if (currentFight.getAttackingPlayer().equals(game.getLocalUser())) {
			//check for a changed battle state and then send an update
			if (currentFight.allSupportersAnswered() && currentFight.getBattleState() < Fight.STATE_HEROS) {
				//all supporters answered -> set battle state
				currentFight.calculateCurrentStrength();
				currentFight.setBattleState(Fight.STATE_HEROS);
			}
			if (currentFight.isAttackingHeroChosen() && currentFight.isDefendingHeroChosen() && currentFight.getBattleState() < Fight.STATE_RETREAT_FIELD && currentFight.getBattleState() > Fight.STATE_SUPPORT) {
				//hero effects (Axton, Nisha)
				currentFight.executeHeroEffects(ExecutionType.BEFORE_FIGHT);
				currentFight.calculateCurrentStrength();
				currentFight.calculateWinner();
				currentFight.setBattleState(Fight.STATE_RETREAT_FIELD);
				//auto-set the values for fight against skags
				if (currentFight.getDefendingPlayer() == null) {
					if (currentFight.getWinner() == Fight.ATTACKERS) {
						//attacking player wins
						int fallingTroopsWinner = currentFight.calculateFallingTroopsSkagFight();
						currentFight.setRetreatField(null);//no retreat for skags
						currentFight.setFallingTroopsTotal(fallingTroopsWinner);
						currentFight.setFallingTroopsWinner(fallingTroopsWinner);
						currentFight.setFallingTroopsLooser(currentFight.getDefendingStrength());
						Map<Field, int[]> fallenTroops = new HashMap<Field, int[]>();
						fallenTroops.put(currentFight.getDefendingField(), new int[2]);//no retreat -> all skags fall
						currentFight.addFallenTroops(fallenTroops);
						currentFight.setBattleState(Fight.STATE_FALLEN_TROOP_REMOVING);
					}
					//let the player choose the retreat field when the skags win
				}
			}
			if (currentFight.isRetreatFieldChosen() && currentFight.getBattleState() < Fight.STATE_FALLEN_TROOP_SELECTION) {
				currentFight.setBattleState(Fight.STATE_FALLEN_TROOP_SELECTION);
				if (currentFight.getDefendingPlayer() == null) {
					//player lost against skags
					int[] lostTroops = currentFight.calculateFallingTroopsSkagFightLost();
					currentFight.setFallingTroopsTotal(lostTroops[0]);
					currentFight.setFallingTroopsLooser(lostTroops[1]);
					Map<Field, int[]> fallenTroops = new HashMap<Field, int[]>();
					fallenTroops.put(currentFight.getDefendingField(), new int[] {currentFight.getDefendingField().getNormalTroops()-1, 0});//one skag survives
					currentFight.addFallenTroops(fallenTroops);
					currentFight.setBattleState(Fight.STATE_FALLEN_TROOP_REMOVING);						
				}
			}
			if (currentFight.isFallingTroopsChosen() && currentFight.getBattleState() < Fight.STATE_FALLEN_TROOP_REMOVING) {
				currentFight.setBattleState(Fight.STATE_FALLEN_TROOP_REMOVING);
			}
			if (currentFight.isFallenTroopsChosen()) {
				if (currentFight.isAllFallenTroopsChosen()) {
					//all support fields, attacking and defending field have chosen their falling troops
					currentFight.setBattleState(Fight.STATE_FIGHT_ENDED);
					fightEnded = true;
				}
			}
			sendUpdate();
		}
		else {
			//just send an update and let the attacking player merge it
			sendUpdate();
		}
		fightExecutionFrame.update();
		if (fightEnded) {
			//execute the fight end after the update was sent
			endFight();
		}
	}
	/**
	 * Send an update to the (fight-)starting player or the other player if the local player is the starter.
	 */
	private void sendUpdate() {
		FightTransfereMessage message = new FightTransfereMessage(currentFight, currentFight.getAttackingPlayer().equals(game.getLocalUser()));
		client.resetOutput();
		client.sendMessage(message);
	}
	
	/**
	 * Receive a fight object from the server.
	 * The (fight-)starting player merges the new information and sends the new fight object to all other players.
	 * The other players just receive the fight object and use the new one instead of their old one.
	 * 
	 * @param fight
	 * 		The fight object sent from the server.
	 */
	public void receiveFight(Fight fight) {
		if (fight.getAttackingPlayer().equals(game.getLocalUser())) {
			mergeFight(fight);
		}
		else {
			overrideFight(fight);
		}
		fightExecutionFrame.update();
	}
	/**
	 * Merge the new information about the fight to the current fight object.
	 * 
	 * @param fight
	 * 		The fight object that is to be merged with the current fight.
	 */
	private void mergeFight(Fight fight) {
		boolean fightEnded = false;
		if (currentFight != null) {
			//merge the supports and add the strength
			if (fight.getBattleState() == Fight.STATE_SUPPORT) {
				if (fight.getAttackSupporters().size() > currentFight.getAttackSupporters().size()) {
					currentFight.setAttackSupporters(fight.getAttackSupporters());
					currentFight.calculateCurrentStrength();
				}
				if (fight.getDefenceSupporters().size() > currentFight.getDefenceSupporters().size()) {
					currentFight.setDefenceSupporters(fight.getDefenceSupporters());
					currentFight.calculateCurrentStrength();
				}
				if (fight.getSupportRejections().size() > currentFight.getSupportRejections().size()) {
					currentFight.setSupportRejections(fight.getSupportRejections());
				}
				if (fight.allSupportersAnswered()) {
					//all supporters answered -> set battle state
					currentFight.setBattleState(Fight.STATE_HEROS);
				}
			}
			//merge the defenders hero card (if one)
			if (fight.isDefendingHeroChosen() && fight.getBattleState() < Fight.STATE_RETREAT_FIELD) {
				currentFight.setDefendingHeroChosen(true);
				currentFight.setDefendingHero(fight.getDefendingHero());
				currentFight.setUseDefendingHeroEffect(fight.isUseDefendingHeroEffect());
				if (currentFight.isAttackingHeroChosen()) {
					currentFight.calculateWinner();
					currentFight.setBattleState(Fight.STATE_RETREAT_FIELD);
					if (currentFight.getDefendingPlayer() == null) {
						//fight against neutral troops (only occurs here if there is support against them) 
						if (currentFight.getWinner() == Fight.ATTACKERS) {
							int fallingTroopsWinner = currentFight.calculateFallingTroopsSkagFight();
							currentFight.setRetreatFieldChosen(true);//skags can't retreat
							currentFight.setFallingTroopsTotal(fallingTroopsWinner);
							currentFight.setFallingTroopsWinner(fallingTroopsWinner);
							currentFight.setFallingTroopsLooser(currentFight.getDefendingStrength());
							Map<Field, int[]> fallenTroops = new HashMap<Field, int[]>();
							fallenTroops.put(currentFight.getDefendingField(), new int[2]);//no retreat -> all skags fall
							currentFight.addFallenTroops(fallenTroops);
							currentFight.setBattleState(Fight.STATE_FALLEN_TROOP_REMOVING);	
						}
					}
				}
			}
			//merge the retreat field
			if (fight.isRetreatFieldChosen() && fight.getBattleState() < Fight.STATE_FALLEN_TROOP_SELECTION) {
				currentFight.setRetreatFieldChosen(true);
				currentFight.setRetreatField(fight.getRetreatField());
				currentFight.setBattleState(Fight.STATE_FALLEN_TROOP_SELECTION);
			}
			//merge the falling troops (chosen by the winner)
			if (fight.isFallingTroopsChosen() && fight.getBattleState() < Fight.STATE_FALLEN_TROOP_REMOVING) {
				currentFight.setFallingTroopsChosen(true);
				currentFight.setFallingTroopsTotal(fight.getFallingTroopsTotal());
				currentFight.setFallingTroopsLooser(fight.getFallingTroopsLooser());
				currentFight.setFallingTroopsSupport(fight.getFallingTroopsSupport());
				currentFight.setBattleState(Fight.STATE_FALLEN_TROOP_REMOVING);
			}
			//merge the fallen troops (as selected by all losing players)
			if (fight.isFallenTroopsChosen() && fight.getBattleState() < Fight.STATE_FIGHT_ENDED) {
				Map<Field, int[]> fallenTroops = currentFight.getFallenTroops();
				for (Field field : fight.getFallenTroops().keySet()) {
					fallenTroops.put(field, fight.getFallenTroops().get(field));
				}
				if (currentFight.isAllFallenTroopsChosen()) {
					//all support fields, attacking and defending field have chosen their falling troops
					currentFight.setBattleState(Fight.STATE_FIGHT_ENDED);
					fightEnded = true;
				}
			}
			sendUpdate();
			if (fightEnded) {
				//execute the fight end after the update was sent
				endFight();
			}
		}
		//fightExecutionFrame.update(); //done in receive method
	}
	/**
	 * Just override the current fight with the update from the server.
	 * 
	 * @param fight
	 * 		The fight update from the server.
	 */
	private void overrideFight(Fight fight) {
		//if the fight is started there is no current fight -> just override
		if (currentFight == null) {
			this.currentFight = fight;
			fightExecutionFrame.setVisible(true);
			fightExecutionFrame.requestFocus();
			fightExecutionFrame.clearAll();//resets all saved fields
			fightExecutionFrame.update();
			showSupportRequests();
		}
		else {
			//if there is already a fight overriding causes problems (references...) so use another kind of merge
			currentFight.merge(fight);
			//fightExecutionFrame.update();//done in receive
		}
	}
	
	private void addPossibleSupportFields() {
		List<Field> supporters = currentFight.getPossibleSupporters();
		for (Field field : currentFight.getDefendingField().getNeighbours()) {
			if (field.getCommand() != null && field.getCommand().isSupport()) {
				supporters.add(field);				
			}
		}
	}
	
	private void showSupportRequests() {
		for (Field field : currentFight.getPossibleSupporters()) {
			if (field.getAffiliation() != null && field.getAffiliation().equals(game.getLocalUser())) {
				new SupportRequestFrame(field, this).setVisible(true);
			}
		}
	}
	
	public FightExecutionFrame getFightExecutionFrame() {
		return fightExecutionFrame;
	}
	public void setFightExecutionFrame(FightExecutionFrame fightExecutionFrame) {
		this.fightExecutionFrame = fightExecutionFrame;
	}
	
	public Map<Integer, List<Fight>> getFights() {
		return fights;
	}
	public void setFights(Map<Integer, List<Fight>> fights) {
		this.fights = fights;
	}
	
	public Fight getCurrentFight() {
		return currentFight;
	}
	public void setCurrentFight(Fight currentFight) {
		this.currentFight = currentFight;
	}
	
	public PointManager getPointManager() {
		return pointManager;
	}
	public void setPointManager(PointManager pointManager) {
		this.pointManager = pointManager;
	}
}