package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalRecruit extends TurnGoal {

	private static final long serialVersionUID = -1220061583864766693L;
	
	public TurnGoalRecruit() {
		imagePath = "turn_goal_recruit.png";
		loadImage();
		description = "<html>Rekrutierung:<br/>"
				+ "Viele neue Selbstmordkandidaten sind auf Pandora eingetroffen,<br/>"
				+ "die Rekrutiert werden wollen. [1 Punkt pro rekrutierter oder<br/>"
				+ "aufgerüsteter Einheit]</html>";
	}
	
	@Override
	public void receivePointsRecruitment(User user, int recruitedTroops) {
		game.getPointManager().addPoints(user, recruitedTroops);
	}
}