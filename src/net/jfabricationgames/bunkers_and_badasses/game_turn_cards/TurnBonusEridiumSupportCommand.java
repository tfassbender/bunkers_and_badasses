package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusEridiumSupportCommand extends TurnBonus {
	
	public TurnBonusEridiumSupportCommand() {
		//TODO
		imagePath = "turn_bonus_eridium_1_support_command.png";
		loadImage();
		name = "Eridium - Unterst�tzungs Befehl";
		description = "<html>Zus�tzliche " + SkillProfileManager.ERIDIUM_SKILL_LEVEL[1] + " Eridium zu Beginn der Runde.<br/>1 zus�tzlicher Unterst�tzungsbefehl.</html>";
	}
}