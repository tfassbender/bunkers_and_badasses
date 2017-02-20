package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusAmmo extends TurnBonus {
	
	public TurnBonusAmmo() {
		//TODO
		imagePath = "turn_bonus_ammo_2.png";
		loadImage();
		description = "<html>Zusätzliche " + SkillProfileManager.AMMO_SKILL_LEVEL[2] + " Munition zu Beginn der Runde.</html>";
	}
}