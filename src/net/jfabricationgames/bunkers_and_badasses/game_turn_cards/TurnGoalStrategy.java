package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalStrategy extends TurnGoal {

	private static final long serialVersionUID = -2072170443891249406L;
	
	public TurnGoalStrategy() {
		imagePath = "turn_goal_strategy.png";
		loadImage();
		description = "<html>Strategie:<br/>"
				+ "Nichts �bereilen. Strategie dauert seine Zeit. [Beim Beenden<br/>"
				+ "der Runde erh�llt jeder Spieler so viele Punkte wie Spieler vor<br/>"
				+ "ihm schon gepasst haben]</html>";
	}
	
	@Override
	public void receivePointsPassing(User user, int passingOrder, int players) {
		pointManager.addPoints(user, passingOrder-1);
	}
}