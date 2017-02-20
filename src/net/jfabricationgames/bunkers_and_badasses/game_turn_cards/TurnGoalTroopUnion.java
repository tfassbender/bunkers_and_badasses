package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalTroopUnion extends TurnGoal {

	public TurnGoalTroopUnion() {
		imagePath = "turn_goal_troop_union.png";
		loadImage();
		description = "<html>Truppenverband:<br/>"
				+ "Einer f�r alle; Und alle f�r mich [Alle Spieler erhallten am<br/>"
				+ "Ende der Runde Punkte f�r ihren gr��ten Truppenzusammenschluss:<br/>"
				+ "1 Punkt pro Truppe im Zusammenschluss (egal ob normale oder<br/>"
				+ "verst�rkte Einheit)]</html>";
	}
	
	//TODO override methods
}