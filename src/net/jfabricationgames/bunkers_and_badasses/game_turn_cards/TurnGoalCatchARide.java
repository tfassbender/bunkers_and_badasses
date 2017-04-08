package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ScootersCatchARide;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalCatchARide extends TurnGoal {

	public TurnGoalCatchARide() {
		imagePath = "turn_goal_catch_a_ride.png";
		loadImage();
		description = "<html>Catch-A-Ride:<br/>"
				+ "Lasst die Armeen nicht einschlafen! Etwas Bewegung muss sein!<br/>"
				+ "[2 Punkt für jeden normalen Marsch; 3 Punkte für jeden weiten<br/>"
				+ "Marsch (durch Catch-A-Rides oder Spezialeffekte)]</html>";
	}
	
	@Override
	public void receivePointsMoving(User user, Field startField, boolean fieldConquered) {
		if (startField.getBuilding() instanceof ScootersCatchARide) {
			pointManager.addPoints(user, 3);
		}
		else {
			pointManager.addPoints(user, 2);
		}
	}
}