package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalBlitzkrieg extends TurnGoal {
	
	public TurnGoalBlitzkrieg() {
		imagePath = "turn_goal_blitzkrieg.png";
		loadImage();
		description = "<html>Blitzkrieg:<br/>"
				+ "Was dauert da so lange? Weiter jetzt! [Beim beenden der Runde<br/>"
				+ "erhällt jeder Spieler so viele Punkte wie noch Spieler vorhanden<br/>"
				+ "sind die noch nicht gepasst haben]</html>";
	}
	
	@Override
	public void receivePointsPassing(User user, int passingOrder, int players) {
		pointManager.addPoints(user, players-passingOrder);
	}
}