package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalSupport extends TurnGoal {

	private static final long serialVersionUID = -2378622878360708902L;
	
	public TurnGoalSupport() {
		imagePath = "turn_goal_support.png";
		loadImage();
		description = "<html>Unterstützung:<br/>"
				+ "Truppen brauchen Unterstützung [2 Punkte für Unterstützung<br/>"
				+ "eigener Truppen; 3 Punkte für Unterstützung gegnerischer Truppen]</html>";
	}
	
	@Override
	public void receivePointsFight(User user, Fight fight) {
		int support = 0;
		for (Field field : fight.getAttackSupporters()) {
			if (field.getAffiliation().equals(user)) {
				support++;
			}
		}
		for (Field field : fight.getDefenceSupporters()) {
			if (field.getAffiliation().equals(user)) {
				support++;
			}
		}
		if (user.equals(fight.getAttackingPlayer()) || fight.getDefendingPlayer() != null && user.equals(fight.getDefendingPlayer())) {
			game.getPointManager().addPoints(user, support*2, getClass(), "supported own troops");			
		}
		else {
			game.getPointManager().addPoints(user, support*3, getClass(), "supported enemy troops");
		}
	}
}