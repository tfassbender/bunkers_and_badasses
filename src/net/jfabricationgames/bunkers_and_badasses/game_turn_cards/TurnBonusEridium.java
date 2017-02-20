package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusEridium extends TurnBonus {
	
	public TurnBonusEridium() {
		//TODO
		imagePath = "turn_bonus_eridium_2.png";
		loadImage();
		description = "<html>Zusätzliche " + SkillProfileManager.ERIDIUM_SKILL_LEVEL[2] + " Eridium zu Beginn der Runde.</html>";
	}
}