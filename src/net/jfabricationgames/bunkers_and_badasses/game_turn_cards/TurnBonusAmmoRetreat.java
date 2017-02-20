package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusAmmoRetreat extends TurnBonus {
	
	public TurnBonusAmmoRetreat() {
		//TODO
		imagePath = "turn_bonus_ammo_1_retreat_command.png";
		loadImage();
		description = "<html>Zusätzliche " + SkillProfileManager.AMMO_SKILL_LEVEL[1] + " Munition zu Beginn der Runde.<br/>1 zusätzlicher Rückzugsbefehl.</html>";
	}
}