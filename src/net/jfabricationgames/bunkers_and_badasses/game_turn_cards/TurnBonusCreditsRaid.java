package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusCreditsRaid extends TurnBonus {
	
	private static final long serialVersionUID = 3160996383856321547L;
	
	public TurnBonusCreditsRaid() {
		bonusId = 5;
		loadVariables();
		raidCommands = 1;
		imagePath = "turn_bonus_credits_1_raid_command.png";
		loadImage();
		name = "Credits - Überfall Befehl";
		description = "<html>Zusätzliche " + SkillProfileManager.CREDITS_SKILL_LEVEL[1] + " Credits zu Beginn der Runde.<br/>1 zusätzlicher Überfallbefehl</html>";
	}
}