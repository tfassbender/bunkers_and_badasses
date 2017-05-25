package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnBonusEridiumSupport extends TurnBonus {
	
	private static final long serialVersionUID = 1377999226637594818L;
	
	public TurnBonusEridiumSupport() {
		bonusId = 8;
		loadVariables();
		imagePath = "turn_bonus_eridium_1_support.png";
		loadImage();
		name = "Eridium - Unterst�tzung";
		description = "<html>Zus�tzliche " + SkillProfileManager.ERIDIUM_SKILL_LEVEL[1] + " Eridium zu Beginn der Runde.<br/>1 Punkt Bonus f�r jede Unterst�tzung.</html>";
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
		pointManager.addPoints(user, support);
	}
}