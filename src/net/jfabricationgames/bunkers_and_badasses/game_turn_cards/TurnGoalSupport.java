package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalSupport extends TurnGoal {

	public TurnGoalSupport() {
		imagePath = "turn_goal_support.png";
		loadImage();
		description = "<html>Unterst�tzung:<br/>"
				+ "Truppen brauchen Unterst�tzung [2 Punkte f�r Unterst�tzung<br/>"
				+ "eigener Truppen; 3 Punkte f�r Unterst�tzung gegnerischer Truppen]</html>";
	}
	
	//TODO override methods
}