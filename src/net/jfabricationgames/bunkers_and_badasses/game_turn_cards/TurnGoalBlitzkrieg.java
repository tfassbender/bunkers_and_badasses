package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalBlitzkrieg extends TurnGoal {
	
	public TurnGoalBlitzkrieg() {
		imagePath = "turn_goal_blitzkrieg.png";
		loadImage();
		description = "<html>Blitzkrieg:<br/>"
				+ "Was dauert da so lange? Weiter jetzt! [Beim beenden der Runde<br/>"
				+ "erhällt jeder Spieler so viele Punkte wie noch Spieler vorhanden<br/>"
				+ "sind die noch nicht gepasst haben]</html>";
	}
	
	//TODO override methods
}