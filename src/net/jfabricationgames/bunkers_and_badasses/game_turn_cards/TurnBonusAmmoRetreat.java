package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusAmmoRetreat extends TurnBonus {
	
	private static final long serialVersionUID = 6148797172444935136L;
	
	public TurnBonusAmmoRetreat() {
		bonusId = 2;
		loadVariables();
		imagePath = "turn_bonus_ammo_1_retreat_command.png";
		loadImage();
		name = "Munition - Rückzugs Befehl";
		description = "<html>Zusätzliche " + SkillProfileManager.AMMO_SKILL_LEVEL[1] + " Munition zu Beginn der Runde.<br/>1 zusätzlicher Rückzugsbefehl.</html>";
	}
}