package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.ArrayList;
import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoal;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class GameTurnGoalManager {
	
	private List<TurnGoal> turnGoals;
	
	private static final List<TurnGoal> TURN_GOALS = createTurnGoalList();
	
	private GameTurnManager gameTurnManager;
	
	public GameTurnGoalManager(GameTurnManager gameTurnManager, PointManager pointManager) {
		this.gameTurnManager = gameTurnManager;
		for (TurnGoal goal : TURN_GOALS) {
			goal.setPointManager(pointManager);
		}
	}
	
	private static List<TurnGoal> createTurnGoalList() {
		List<TurnGoal> goals = new ArrayList<TurnGoal>();
		//TODO add the turn goal classes when implemented.
		return goals;
	}
	
	public void chooseTurnGoals() {
		TurnGoal[] goals = new TurnGoal[TURN_GOALS.size()];
		for (int i = 0; i < goals.length; i++) {
			goals[i] = TURN_GOALS.get(i);
		}
		TurnGoal swap;
		int choosable = goals.length;
		int random;
		for (int i = 0; i < 10; i++) {//TODO change from 10 to turn number when known
			random = (int) (Math.random() * (choosable- i)) + i;//select a random integer from i to choosable
			swap = goals[random];//swap the chosen color to position i
			goals[random] = goals[i];
			goals[i] = swap;
			turnGoals.add(goals[i]);
		}
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
}