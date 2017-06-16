package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalStrategy extends TurnGoal {

	private static final long serialVersionUID = -2072170443891249406L;
	
	public TurnGoalStrategy() {
		imagePath = "turn_goal_strategy.png";
		loadImage();
		description = "<html>Strategie:<br/>"
				+ "Nichts �bereilen. Strategie dauert seine Zeit. [Beim Beenden<br/>"
				+ "der Runde erh�llt jeder Spieler zwei mal so viele Punkte wie<br/>"
				+ "Spieler vor ihm schon gepasst haben]</html>";
	}
	
	@Override
	public void receivePointsPassing(User user, int passingOrder, int players) {
		pointManager.addPoints(user, 2*(passingOrder-1));
	}
}