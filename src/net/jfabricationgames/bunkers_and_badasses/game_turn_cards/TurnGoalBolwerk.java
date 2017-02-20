package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalBolwerk extends TurnGoal {
	
	public TurnGoalBolwerk() {
		imagePath = "turn_goal_bolwerk.png";
		loadImage();
		description = "<html>Bolwerk:<br/>"
				+ "Haltet den Vormarsch der feindlichen Truppen auf!<br/>"
				+ "[3 Punkte für jede erfolgreiche Verteidigung]</html>";
	}
	
	//TODO override methods
}