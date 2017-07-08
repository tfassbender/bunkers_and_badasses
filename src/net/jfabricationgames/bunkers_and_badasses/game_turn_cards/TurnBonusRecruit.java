package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnBonusRecruit extends TurnBonus {
	
	private static final long serialVersionUID = -6671618932613848500L;
	
	public TurnBonusRecruit() {
		bonusId = 12;
		loadVariables();
		recruitmentCommands = 1;
		imagePath = "turn_bonus_recruit_command.png";
		loadImage();
		name = "Rekrutierungs Befehl";
		description = "<html>1 zusätzlicher Rekrutierungsbefehl.</html>";
	}
}