package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalWildHunt extends TurnGoal {

	public TurnGoalWildHunt() {
		imagePath = "turn_goal_wild_hunt.png";
		loadImage();
		description = "<html>Gro�wildjagt:<br/>"
				+ "Hast du das Gef�hl du hast zu viel Munition und zu<br/>"
				+ "wenig Punkte? Dann baller ein paar Skags weg! [1 Punkt<br/>"
				+ "pro geg�teter neutraler Einheit]</html>";
	}
	
	//TODO override methods
}