package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusAmmoBuild extends TurnBonus {
	
	public TurnBonusAmmoBuild() {
		//TODO
		imagePath = "turn_bonus_ammo_1_build.png";
		loadImage();
		description = "<html>Zusätzliche " + SkillProfileManager.AMMO_SKILL_LEVEL[1] + " Munition zu Beginn der Runde.<br/>2 Punkte Bonus für jedes Bauen oder Aufrüsten eines Gebäudes.</html>";
	}
}