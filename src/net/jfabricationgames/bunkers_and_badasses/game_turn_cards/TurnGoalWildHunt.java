package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalWildHunt extends TurnGoal {

	private static final long serialVersionUID = 7588035027020774251L;
	
	public TurnGoalWildHunt() {
		imagePath = "turn_goal_wild_hunt.png";
		loadImage();
		description = "<html>Großwildjagt:<br/>"
				+ "Hast du das Gefühl du hast zu viel Munition und zu<br/>"
				+ "wenig Punkte? Dann baller ein paar Skags weg! [2 Punkte<br/>"
				+ "pro gegöteter neutraler Einheit]</html>";
	}
	
	public void receivePointsFight(User user, Fight fight) {
		if (fight.getAttackingPlayer().equals(user) && fight.getDefendingField().getAffiliation() == null && fight.getWinner() == Fight.ATTACKERS) {
			game.getPointManager().addPoints(user, 2*fight.getDefendingStrength());
		}
	}
}