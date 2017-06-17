package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusEridiumSupportCommand extends TurnBonus {
	
	private static final long serialVersionUID = -7923650801555109288L;
	
	public TurnBonusEridiumSupportCommand() {
		bonusId = 9;
		loadVariables();
		imagePath = "turn_bonus_eridium_1_support_command.png";
		loadImage();
		name = "Eridium - Unterstützungs Befehl";
		description = "<html>Zusätzliche " + SkillProfileManager.ERIDIUM_SKILL_LEVEL[1] + " Eridium zu Beginn der Runde.<br/>1 zusätzlicher Unterstützungsbefehl.</html>";
	}
}