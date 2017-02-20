package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalCatchARide extends TurnGoal {

	public TurnGoalCatchARide() {
		imagePath = "turn_goal_catch_a_ride.png";
		loadImage();
		description = "<html>Catch-A-Ride:<br/>"
				+ "Lasst die Armeen nicht einschlafen! Etwas Bewegung muss sein!<br/>"
				+ "[2 Punkt für jeden normalen Marsch; 3 Punkte für jeden weiten<br/>"
				+ "Marsch (durch Catch-A-Rides oder Spezialeffekte)]</html>";
	}
	
	//TODO override methods
}