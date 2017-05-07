package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoal;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalArmAGeddon;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalBlitzkrieg;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalBolwerk;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalBuilding;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalCapitalism;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalCatchARide;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalConquer;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalRecruit;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalSpecialForces;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalStrategy;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalSupport;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalTroopUnion;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalWildHunt;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class GameTurnGoalManager implements Serializable {
	
	private static final long serialVersionUID = 3308092852949814804L;
	
	private List<TurnGoal> turnGoals;
	
	private static final transient List<TurnGoal> TURN_GOALS = createTurnGoalList();
	
	private GameTurnManager gameTurnManager;
	
	public GameTurnGoalManager(PointManager pointManager) {
		for (TurnGoal goal : TURN_GOALS) {
			goal.setPointManager(pointManager);
		}
	}
	
	private static List<TurnGoal> createTurnGoalList() {
		List<TurnGoal> goals = new ArrayList<TurnGoal>();
		goals.add(new TurnGoalArmAGeddon());
		goals.add(new TurnGoalBlitzkrieg());
		goals.add(new TurnGoalBolwerk());
		goals.add(new TurnGoalBuilding());
		goals.add(new TurnGoalCapitalism());
		goals.add(new TurnGoalCatchARide());
		goals.add(new TurnGoalConquer());
		goals.add(new TurnGoalRecruit());
		goals.add(new TurnGoalSpecialForces());
		goals.add(new TurnGoalStrategy());
		goals.add(new TurnGoalSupport());
		goals.add(new TurnGoalTroopUnion());
		goals.add(new TurnGoalWildHunt());
		return goals;
	}
	
	/**
	 * Merge the data from the new goal manager.
	 * 
	 * @param goalManager
	 * 		The new goal manager.
	 */
	public void merge(GameTurnGoalManager goalManager) {
		//copy the image references from the previous turn bonuses to the new ones
		boolean instanceFound;
		for (TurnGoal goal : goalManager.getAllTurnGoals()) {
			instanceFound = false;
			for (TurnGoal prev : TURN_GOALS) {//load from TURN_GOALS because these are surely loaded
				if (!instanceFound && goal.getClass().equals(prev.getClass())) {
					goal.setImage(prev.getImage());
					instanceFound = true;
				}
			}
		}
		//set the new goals
		turnGoals = goalManager.getAllTurnGoals();
	}
	
	public void chooseTurnGoals() {
		TurnGoal[] goals = new TurnGoal[TURN_GOALS.size()];
		for (int i = 0; i < goals.length; i++) {
			goals[i] = TURN_GOALS.get(i);
		}
		TurnGoal swap;
		int choosable = goals.length;
		int random;
		for (int i = 0; i < GameTurnManager.getNumTurns(); i++) {
			random = (int) (Math.random() * (choosable- i)) + i;//select a random integer from i to choosable
			swap = goals[random];//swap the chosen color to position i
			goals[random] = goals[i];
			goals[i] = swap;
			turnGoals.add(goals[i]);
		}
	}
	
	/**
	 * Get the TurnGoal object for a game turn.
	 * 
	 * @param turn
	 * 		The games turn (starting by 0)
	 * 
	 * @return
	 * 		The TurnGoal object.
	 */
	public TurnGoal getTurnGoal(int turn) {
		return turnGoals.get(turn);
	}
	/**
	 * Get the TurnGoals object for the current game turn.
	 * 
	 * @return
	 * 		The current turn's TurnGoal.
	 */
	public TurnGoal getTurnGoal() {
		return turnGoals.get(gameTurnManager.getTurn());
	}
	
	private List<TurnGoal> getAllTurnGoals() {
		return turnGoals;
	}
	
	public void receivePointsFight(User user, Fight fight) {
		turnGoals.get(gameTurnManager.getTurn()-1).receivePointsFight(user, fight);
	}
	public void receivePointsTurnEnd(User user, Game game) {
		turnGoals.get(gameTurnManager.getTurn()-1).receivePointsTurnEnd(user, game);
	}
	public void receivePointsRecruitment(User user, int recruitedTroops) {
		turnGoals.get(gameTurnManager.getTurn()-1).receivePointsRecruitment(user, recruitedTroops);
	}
	public void receivePointsPlaning(User user, int ammoConsumption) {
		turnGoals.get(gameTurnManager.getTurn()-1).receivePointsPlaning(user, ammoConsumption);
	}
	public void receivePointsPassing(User user, int passingOrder, int players) {
		turnGoals.get(gameTurnManager.getTurn()-1).receivePointsPassing(user, passingOrder, players);
	}
	public void receivePointsMoving(User user, Field startField, boolean fieldConquered) {
		turnGoals.get(gameTurnManager.getTurn()-1).receivePointsMoving(user, startField, fieldConquered);
	}
	
	public void setGameTurnManager(GameTurnManager gameTurnManager) {
		this.gameTurnManager = gameTurnManager;
	}
}