package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnBonusMarch extends TurnBonus {
	
	private static final long serialVersionUID = 7633911487585007713L;
	
	public TurnBonusMarch() {
		bonusId = 10;
		loadVariables();
		imagePath = "turn_bonus_march_command.png";
		loadImage();
		name = "Marschbefehl";
		description = "<html>1 zus�tzlicher Marschbefehl.</html>";
	}
}