package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusEridiumSupport extends TurnBonus {
	
	public TurnBonusEridiumSupport() {
		//TODO
		imagePath = "turn_bonus_eridium_1_support.png";
		loadImage();
		name = "Eridium - Unterst�tzung";
		description = "<html>Zus�tzliche " + SkillProfileManager.ERIDIUM_SKILL_LEVEL[1] + " Eridium zu Beginn der Runde.<br/>1 Punkt Bonus f�r jede Unterst�tzung.</html>";
	}
}