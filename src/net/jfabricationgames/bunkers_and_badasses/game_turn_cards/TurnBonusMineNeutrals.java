package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnBonusMineNeutrals extends TurnBonus {
	
	public TurnBonusMineNeutrals() {
		//TODO
		imagePath = "turn_bonus_mine_command_neutrals.png";
		loadImage();
		name = "Resourcen Befehl - Neutrale";
		description = "<html>1 zusätzlicher Resourcen-Gewinnungsbefehl.<br/>1 Punkt Bonus für getötete neutrale Einheit.</html>";
	}
}