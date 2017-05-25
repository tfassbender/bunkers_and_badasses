package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalArmAGeddon extends TurnGoal {
	
	private static final long serialVersionUID = 3446648916742339794L;
	
	public TurnGoalArmAGeddon() {
		imagePath = "turn_goal_arm_a_geddon.png";
		loadImage();
		description = "<html>Arm-A-Geddon:<br/>"
				+ "Dr. Zed braucht Ersatzk�rperteile. Daf�r m�ssen nur <br/>"
				+ "ein paar Leute sterben... [1 Punkt pro im Kampf fallender<br/>"
				+ " Truppe (gegnerische und eigene Truppen; Punkte nur f�r <br/>"
				+ "den Angreifer)]</html>";
	}
	
	@Override
	public void receivePointsFight(User user, Fight fight) {
		if (fight.getAttackingPlayer().equals(user)) {
			int fallenTroops = 0;
			Map<Field, int[]> troops = fight.getFallenTroops();
			for (Field field : troops.keySet()) {
				fallenTroops += troops.get(field)[0];
				fallenTroops += troops.get(field)[1];
			}
			pointManager.addPoints(user, fallenTroops);
		}
	}
}