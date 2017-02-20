package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalTroopUnion extends TurnGoal {

	public TurnGoalTroopUnion() {
		imagePath = "turn_goal_troop_union.png";
		loadImage();
		description = "<html>Truppenverband:<br/>"
				+ "Einer für alle; Und alle für mich [Alle Spieler erhallten am<br/>"
				+ "Ende der Runde Punkte für ihren größten Truppenzusammenschluss:<br/>"
				+ "1 Punkt pro Truppe im Zusammenschluss (egal ob normale oder<br/>"
				+ "verstärkte Einheit)]</html>";
	}
	
	//TODO override methods
}