package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnBonusMineNeutrals extends TurnBonus {
	
	private static final long serialVersionUID = -1303890713691071951L;
	
	public TurnBonusMineNeutrals() {
		bonusId = 11;
		loadVariables();
		imagePath = "turn_bonus_mine_command_neutrals.png";
		loadImage();
		name = "Resourcen Befehl - Neutrale";
		description = "<html>1 zus�tzlicher Resourcen-Gewinnungsbefehl.<br/>1 Punkt Bonus f�r get�tete neutrale Einheit.</html>";
	}
	
	@Override
	public void receivePointsFight(User user, Fight fight) {
		if (fight.getAttackingPlayer().equals(user) && fight.getDefendingField().getAffiliation() == null && fight.getWinner() == Fight.ATTACKERS) {
			pointManager.addPoints(user, fight.getDefendingStrength());
		}
	}
}