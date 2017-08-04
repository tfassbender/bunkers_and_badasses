package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalConquer extends TurnGoal {

	private static final long serialVersionUID = 508841262886589248L;
	
	public TurnGoalConquer() {
		imagePath = "turn_goal_conquer.png";
		loadImage();
		description = "<html>Mein Land:<br/>"
				+ "Die Armeen müssen an Boden gewinnen! [2 Punkte für jedes<br/>"
				+ "eroberte Gebiet (Auch neutrale Gebiete)]</html>";
	}
	
	@Override
	public void receivePointsMoving(User user, Field startField, boolean fieldConquered) {
		if (fieldConquered) {
			game.getPointManager().addPoints(user, 2);
		}
	}
}