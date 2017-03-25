package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusCredits extends TurnBonus {
	
	public TurnBonusCredits() {
		bonusId = 3;
		loadVariables();
		imagePath = "turn_bonus_credits_2.png";
		loadImage();
		name = "Credits 2";
		description = "<html>Zusätzliche " + SkillProfileManager.CREDITS_SKILL_LEVEL[2] + " Credits zu Beginn der Runde.</html>";
	}
}