package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalBlitzkrieg extends TurnGoal {
	
	private static final long serialVersionUID = 5709931466815842046L;
	
	public TurnGoalBlitzkrieg() {
		imagePath = "turn_goal_blitzkrieg.png";
		loadImage();
		description = "<html>Blitzkrieg:<br/>"
				+ "Was dauert da so lange? Weiter jetzt! [Beim beenden der Runde<br/>"
				+ "erhällt jeder Spieler zwei mal so viele Punkte wie noch Spieler<br/>"
				+ "vorhanden sind die noch nicht gepasst haben]</html>";
	}
	
	@Override
	public void receivePointsPassing(User user, int passingOrder, int players) {
		game.getPointManager().addPoints(user, 2*(players-passingOrder));
	}
}