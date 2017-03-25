package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusCreditsRaid extends TurnBonus {
	
	public TurnBonusCreditsRaid() {
		bonusId = 5;
		loadVariables();
		imagePath = "turn_bonus_credits_1_raid_command.png";
		loadImage();
		name = "Credits - �berfall Befehl";
		description = "<html>Zus�tzliche " + SkillProfileManager.CREDITS_SKILL_LEVEL[1] + " Credits zu Beginn der Runde.<br/>1 zus�tzlicher �berfallbefehl</html>";
	}
}