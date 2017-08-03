package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnBonusEridiumSupportCommand extends TurnBonus {
	
	private static final long serialVersionUID = -7923650801555109288L;
	
	public TurnBonusEridiumSupportCommand() {
		bonusId = 9;
		loadVariables();
		supportCommands = 1;
		imagePath = "turn_bonus_eridium_1_support_command.png";
		loadImage();
		name = "Eridium - Unterstützungs Befehl";
		description = "<html>Zusätzliche " + eridium + " Eridium zu Beginn der Runde. <br/>1 zusätzlicher Unterstützungsbefehl.</html>";
	}
}