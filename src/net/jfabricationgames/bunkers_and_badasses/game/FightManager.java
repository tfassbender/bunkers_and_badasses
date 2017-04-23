package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_communication.FightTransfereMessage;
import net.jfabricationgames.bunkers_and_badasses.game_frame.FightExecutionFrame;
import net.jfabricationgames.bunkers_and_badasses.game_frame.SupportRequestFrame;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoal;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.client.JFGClient;

public class FightManager {
	
	private Map<Integer, List<Fight>> fights;//executed fights sorted by the game turns
	
	private Fight currentFight;
	private FightExecutionFrame fightExecutionFrame;
	
	private User localPlayer;	
	private List<User> players;
	
	private GameTurnBonusManager gameTurnBonusManager;
	private GameTurnGoalManager gameTurnGoalManager;
	private PointManager pointManager;
	
	private JFGClient client;
	
	public FightManager(JFGClient client, User localPlayer, List<User> players, GameTurnBonusManager gameTurnBonusManager, 
			GameTurnGoalManager gameTurnGoalManager, PointManager pointManager) {
		this.client = client;
		this.localPlayer = localPlayer;
		this.players = players;
		this.gameTurnBonusManager = gameTurnBonusManager;
		this.gameTurnGoalManager = gameTurnGoalManager;
		this.pointManager = pointManager;
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
	public void startFight(Field startField, Field targetField, int normalTroops, int badassTroops) {
		currentFight = new Fight();
		currentFight.setAttackingField(startField);
		currentFight.setDefendingField(targetField);
		currentFight.setAttackingNormalTroops(normalTroops);
		currentFight.setAttackingBadassTroops(badassTroops);
		addPossibleSupportFields();
		fightExecutionFrame.setVisible(true);
		fightExecutionFrame.requestFocus();
		fightExecutionFrame.update();
		showSupportRequests();
		FightTransfereMessage message = new FightTransfereMessage(currentFight, true);
		client.sendMessage(message);
	}
	
	public void endFight() {
		giveOutPoints();
		//TODO
	}
	public void giveOutPoints() {
		//points for attacker and winner
		pointManager.addPoints(currentFight.getAttackingPlayer(), Game.getGameVariableStorage().getFightAttackerPoints());
		pointManager.addPoints(currentFight.getWinningPlayer(), Game.getGameVariableStorage().getFightWinnerPoints());
		//points for supporters
		List<User> supporters = new ArrayList<User>();
		if (currentFight.getWinner() == Fight.ATTACKERS) {
			for (Field field : currentFight.getAttackSupporters()) {
				if (!field.getAffiliation().equals(currentFight.getAttackingPlayer())) {
					supporters.add(field.getAffiliation());
				}
			}
		}
		else {
			for (Field field : currentFight.getDefenceSupporters()) {
				if (!field.getAffiliation().equals(currentFight.getDefendingPlayer())) {
					supporters.add(field.getAffiliation());
				}
			}
		}
		for (User user : supporters) {
			pointManager.addPoints(user, Game.getGameVariableStorage().getFightSupporterPoints());
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
	 * Send an update to the (fight-)starting player or the other player if the local player is the starter.
	 */
	public void sendUpdate() {
		FightTransfereMessage message = new FightTransfereMessage(currentFight, currentFight.getAttackingPlayer().equals(localPlayer));
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
		if (fight.getAttackingPlayer().equals(localPlayer)) {
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
			if (fight.getAttackSupporters().size() + fight.getDefenceSupporters().size() + fight.getSupportRejections().size() == fight.getPossibleSupporters().size()) {
				//all supporters answered -> set battle state
				fight.setBattleState(Fight.STATE_HEROS);
			}			
		}
		//merge the defenders hero card (if one)
		if (fight.isDefendingHeroChosen()) {
			currentFight.setDefendingHeroChosen(true);
			currentFight.setDefendingHero(fight.getDefendingHero());
			currentFight.setUseDefendingHeroEffect(fight.isUseDefendingHeroEffect());
			if (currentFight.isAttackingHeroChosen()) {
				currentFight.setBattleState(Fight.STATE_RETREAT_FIELD);
			}
		}
		//merge the retreat field
		if (fight.isRetreatFieldChosen()) {
			currentFight.setRetreatFieldChosen(true);
			currentFight.setRetreatField(fight.getRetreatField());
			currentFight.setBattleState(Fight.STATE_FALLEN_TROOP_SELECTION);
		}
		//merge the falling troops (chosen by the winner)
		if (fight.isFallingTroopsChosen()) {
			currentFight.setFallingTroopsChosen(true);
			currentFight.setFallingTroopsTotal(fight.getFallingTroopsTotal());
			currentFight.setFallingTroopsLooser(fight.getFallingTroopsLooser());
			currentFight.setFallingTroopsSupport(fight.getFallingTroopsSupport());
			currentFight.setBattleState(Fight.STATE_FALLEN_TROOP_REMOVING);
		}
		//merge the fallen troops (as selected by all losing players)
		if (fight.isFallenTroopsChosen()) {
			Map<Field, int[]> fallenTroops = currentFight.getFallenTroops();
			for (Field field : fight.getFallenTroops().keySet()) {
				fallenTroops.put(field, fight.getFallenTroops().get(field));
			}
			if (currentFight.getFallenTroops().keySet().size() == currentFight.getFallingTroopsSupport().keySet().size() + 2) {
				//all support fields, attacking and defending field have chosen their falling troops
				currentFight.setBattleState(Fight.STATE_FIGHT_ENDED);
			}
		}
		FightTransfereMessage message = new FightTransfereMessage(currentFight, true);
		client.sendMessage(message);
	}
	/**
	 * Just override the current fight with the update from the server.
	 * 
	 * @param fight
	 * 		The fight update from the server.
	 */
	private void overrideFight(Fight fight) {
		boolean fightStarted = currentFight == null;
		this.currentFight = fight;
		if (fightStarted) {
			fightExecutionFrame.setVisible(true);
			fightExecutionFrame.requestFocus();
			showSupportRequests();
		}
	}
	
	private void addPossibleSupportFields() {
		List<Field> supporters = currentFight.getPossibleSupporters();
		for (Field field : currentFight.getDefendingField().getNeighbours()) {
			if (field.getCommand().isSupport()) {
				supporters.add(field);				
			}
		}
	}
	
	private void showSupportRequests() {
		for (Field field : currentFight.getPossibleSupporters()) {
			if (field.getAffiliation().equals(localPlayer)) {
				new SupportRequestFrame(currentFight, field, this).setVisible(true);
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
}