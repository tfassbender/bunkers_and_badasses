package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalStrategy extends TurnGoal {

	public TurnGoalStrategy() {
		imagePath = "turn_goal_strategy.png";
		loadImage();
		description = "<html>Strategie:<br/>"
				+ "Nichts übereilen. Strategie dauert seine Zeit. [Beim Beenden<br/>"
				+ "der Runde erhällt jeder Spieler so viele Punkte wie Spieler vor<br/>"
				+ "ihm schon gepasst haben]</html>";
	}
	
	@Override
	public void receivePointsPassing(User user, int passingOrder, int players) {
		pointManager.addPoints(user, passingOrder-1);
	}
}