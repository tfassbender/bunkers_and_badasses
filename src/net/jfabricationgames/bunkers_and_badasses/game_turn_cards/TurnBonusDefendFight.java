package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnBonusDefendFight extends TurnBonus {
	
	private static final long serialVersionUID = -8529176704498269445L;
	
	public TurnBonusDefendFight() {
		bonusId = 6;
		loadVariables();
		defenseCommands = 1;
		imagePath = "turn_bonus_defend_command_fight.png";
		loadImage();
		name = "Verteidungungs Befehl - Kampf";
		description = "<html>1 zusätzlicher Verteidigungsbefehl.<br/>2 Punkte Bonus für jeden gewonnenen Kampf.</html>";
	}
	
	@Override
	public void receivePointsFight(User user, Fight fight) {
		if (fight.getWinningPlayer().equals(user)) {
			pointManager.addPoints(user, 2);
		}
	}
}