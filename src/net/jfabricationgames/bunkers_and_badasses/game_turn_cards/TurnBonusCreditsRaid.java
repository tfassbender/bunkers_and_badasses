package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;

public class TurnBonusCreditsRaid extends TurnBonus {
	
	public TurnBonusCreditsRaid() {
		//TODO
		imagePath = "turn_bonus_credits_1_raid_command.png";
		loadImage();
		description = "<html>Zusätzliche " + SkillProfileManager.CREDITS_SKILL_LEVEL[1] + " Credits zu Beginn der Runde.<br/>1 zusätzlicher Überfallbefehl</html>";
	}
}