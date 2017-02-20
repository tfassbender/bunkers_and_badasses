package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalBuilding extends TurnGoal {
	
	public TurnGoalBuilding() {
		imagePath = "turn_goal_building.png";
		loadImage();
		description = "<html>Bauabnahme:<br/>"
				+ "Eine erfolgreiche Armee braucht nicht nur Truppen sondern auch Gebäude.<br/>"
				+ "[Alle Spieler erhallten am ende der Runde: 1 Punkt für jedes normale Gebäude,<br/>"
				+ "2 Punkte für jedes aufgerüstete Gebäude, 3 Punkte für jede gehalltene Basis]</html>";
	}
	
	//TODO override methods
}