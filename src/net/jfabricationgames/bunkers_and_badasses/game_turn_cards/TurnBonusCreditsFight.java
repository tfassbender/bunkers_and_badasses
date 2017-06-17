package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnBonusCreditsFight extends TurnBonus {
	
	private static final long serialVersionUID = 1239777117378119537L;
	
	public TurnBonusCreditsFight() {
		bonusId = 4;
		loadVariables();
		imagePath = "turn_bonus_credits_1_fight.png";
		loadImage();
		name = "Credits - Kampf";
		description = "<html>Zusätzliche " + SkillProfileManager.CREDITS_SKILL_LEVEL[1] + " Credits zu Beginn der Runde.<br/>2 Punkte Bonus für jeden gewonnenen Kampf.</html>";
	}
	
	@Override
	public void receivePointsFight(User user, Fight fight) {
		if (fight.getWinningPlayer().equals(user)) {
			pointManager.addPoints(user, 2);
		}
	}
}