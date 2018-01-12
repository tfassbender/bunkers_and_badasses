package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game.PointManager;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnBonusMineNeutrals extends TurnBonus {
	
	private static final long serialVersionUID = -1303890713691071951L;
	
	public TurnBonusMineNeutrals() {
		bonusId = 11;
		loadVariables();
		collectCommands = 1;
		imagePath = "turn_bonus_mine_command_neutrals.png";
		loadImage();
		name = "Resourcen Befehl - Neutrale";
		description = "<html>1 zusätzlicher Resourcen-Gewinnungsbefehl.<br/>1 Punkt Bonus für getötete neutrale Einheit.</html>";
	}
	
	@Override
	public void receivePointsFight(User user, Fight fight) {
		if (fight.getAttackingPlayer().equals(user) && fight.getDefendingField().getAffiliation() == null && fight.getWinner() == Fight.ATTACKERS) {
			game.getPointManager().addPoints(user, fight.getDefendingStrength(), getClass(), "killed neutral troops", PointManager.PointType.BONUS);
		}
	}
}