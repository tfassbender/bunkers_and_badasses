package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalBolwerk extends TurnGoal {
	
	private static final long serialVersionUID = 2973075463316860518L;
	
	public TurnGoalBolwerk() {
		imagePath = "turn_goal_bolwerk.png";
		loadImage();
		description = "<html>Bolwerk:<br/>"
				+ "Haltet den Vormarsch der feindlichen Truppen auf!<br/>"
				+ "[3 Punkte für jede erfolgreiche Verteidigung]</html>";
	}
	
	@Override
	public void receivePointsFight(User user, Fight fight) {
		if (user.equals(fight.getDefendingPlayer()) && fight.getWinner() == Fight.DEFENDERS) {
			game.getPointManager().addPoints(user, 3);
		}
	}
}