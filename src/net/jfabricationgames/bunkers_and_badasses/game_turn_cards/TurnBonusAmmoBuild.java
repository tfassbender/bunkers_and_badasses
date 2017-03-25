package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusAmmoBuild extends TurnBonus {
	
	public TurnBonusAmmoBuild() {
		bonusId = 1;
		loadVariables();
		imagePath = "turn_bonus_ammo_1_build.png";
		loadImage();
		name = "Munition - Aufbau";
		description = "<html>Zusätzliche " + SkillProfileManager.AMMO_SKILL_LEVEL[1] + " Munition zu Beginn der Runde.<br/>2 Punkte Bonus für jedes Bauen oder Aufrüsten eines Gebäudes.</html>";
	}
}