package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnBonusDefendFight extends TurnBonus {
	
	public TurnBonusDefendFight() {
		//TODO
		imagePath = "turn_bonus_defend_command_fight.png";
		loadImage();
		name = "Verteidungungs Befehl - Kampf";
		description = "<html>1 zus�tzlicher Verteidigungsbefehl.<br/>2 Punkte Bonus f�r jeden gewonnenen Kampf.</html>";
	}
}