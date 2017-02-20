package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalWildHunt extends TurnGoal {

	public TurnGoalWildHunt() {
		imagePath = "turn_goal_wild_hunt.png";
		loadImage();
		description = "<html>Großwildjagt:<br/>"
				+ "Hast du das Gefühl du hast zu viel Munition und zu<br/>"
				+ "wenig Punkte? Dann baller ein paar Skags weg! [1 Punkt<br/>"
				+ "pro gegöteter neutraler Einheit]</html>";
	}
	
	//TODO override methods
}