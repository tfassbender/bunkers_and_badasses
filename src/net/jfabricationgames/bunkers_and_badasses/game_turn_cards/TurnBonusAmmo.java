package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusAmmo extends TurnBonus {
	
	public TurnBonusAmmo() {
		bonusId = 0;
		loadVariables();
		imagePath = "turn_bonus_ammo_2.png";
		loadImage();
		name = "Munition 2";
		description = "<html>Zusätzliche " + SkillProfileManager.AMMO_SKILL_LEVEL[2] + " Munition zu Beginn der Runde.</html>";
	}
}