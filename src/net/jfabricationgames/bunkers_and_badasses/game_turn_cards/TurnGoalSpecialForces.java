package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalSpecialForces extends TurnGoal {

	public TurnGoalSpecialForces() {
		imagePath = "turn_goal_special_forces.png";
		loadImage();
		description = "<html>Spezialeinheit:<br/>"
				+ "Elitekämpfer greifen ins Kampfgeschehen ein [3 Punkte für jede<br/>"
				+ "eingesetzte Heldenkarte (egal ob im Kampf oder als Zug)]</html>";
	}
	
	//TODO override methods
}