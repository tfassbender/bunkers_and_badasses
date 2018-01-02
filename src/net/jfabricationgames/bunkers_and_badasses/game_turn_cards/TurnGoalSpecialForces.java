package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalSpecialForces extends TurnGoal {

	private static final long serialVersionUID = 4653372825610010368L;
	
	public TurnGoalSpecialForces() {
		imagePath = "turn_goal_special_forces.png";
		loadImage();
		description = "<html>Spezialeinheit:<br/>"
				+ "Elitekämpfer greifen ins Kampfgeschehen ein [3 Punkte für jede<br/>"
				+ "eingesetzte Heldenkarte (egal ob im Kampf oder als Zug)]</html>";
	}
	
	public void receivePointsFight(User user, Fight fight) {
		if ((user.equals(fight.getAttackingPlayer()) && fight.getAttackingHero() != null) || 
				(fight.getDefendingPlayer() != null && user.equals(fight.getDefendingPlayer()) && fight.getDefendingHero() != null)) {
			game.getPointManager().addPoints(user, 3, getClass(), "heros used");
		}
	}
}