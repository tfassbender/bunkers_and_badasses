package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalSupport extends TurnGoal {

	public TurnGoalSupport() {
		imagePath = "turn_goal_support.png";
		loadImage();
		description = "<html>Unterstützung:<br/>"
				+ "Truppen brauchen Unterstützung [2 Punkte für Unterstützung<br/>"
				+ "eigener Truppen; 3 Punkte für Unterstützung gegnerischer Truppen]</html>";
	}
	
	//TODO override methods
}