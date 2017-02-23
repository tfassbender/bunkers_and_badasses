package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusCreditsFight extends TurnBonus {
	
	public TurnBonusCreditsFight() {
		//TODO
		imagePath = "turn_bonus_credits_1_fight.png";
		loadImage();
		name = "Credits - Kampf";
		description = "<html>Zus�tzliche " + SkillProfileManager.CREDITS_SKILL_LEVEL[1] + " Credits zu Beginn der Runde.<br/>2 Punkte Bonus f�r jeden gewonnenen Kampf.</html>";
	}
}