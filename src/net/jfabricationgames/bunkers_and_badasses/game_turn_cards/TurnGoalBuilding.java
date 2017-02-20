package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalBuilding extends TurnGoal {
	
	public TurnGoalBuilding() {
		imagePath = "turn_goal_building.png";
		loadImage();
		description = "<html>Bauabnahme:<br/>"
				+ "Eine erfolgreiche Armee braucht nicht nur Truppen sondern auch Geb�ude.<br/>"
				+ "[Alle Spieler erhallten am ende der Runde: 1 Punkt f�r jedes normale Geb�ude,<br/>"
				+ "2 Punkte f�r jedes aufger�stete Geb�ude, 3 Punkte f�r jede gehalltene Basis]</html>";
	}
	
	//TODO override methods
}