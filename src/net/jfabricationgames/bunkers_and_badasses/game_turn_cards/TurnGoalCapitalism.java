package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalCapitalism extends TurnGoal {

	private static final long serialVersionUID = 1351450126982643592L;
	
	public TurnGoalCapitalism() {
		imagePath = "turn_goal_capitalism.png";
		loadImage();
		description = "<html>Kapitalismus Baby!:<br/>"
				+ "Marcus muss sein Geschäft ankurbeln;<br/>"
				+ "Mit Bestechung für Munitionsverschwendung [3 Punkte pro<br/>"
				+ "100 verbrauchter Munition (in der Planungsphase)]</html>";
	}
	
	@Override
	public void receivePointsPlaning(User user, int ammoConsumption) {
		pointManager.addPoints(user, (ammoConsumption/100)*3); 
	}
}