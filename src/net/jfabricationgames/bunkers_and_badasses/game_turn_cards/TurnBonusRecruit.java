package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnBonusRecruit extends TurnBonus {
	
	public TurnBonusRecruit() {
		bonusId = 12;
		loadVariables();
		imagePath = "turn_bonus_recruit_command.png";
		loadImage();
		name = "Rekrutierungs Befehl";
		description = "<html>1 zusätzlicher Rekrutierungsbefehl.</html>";
	}
}