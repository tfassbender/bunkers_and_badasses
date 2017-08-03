package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnBonusEridium extends TurnBonus {
	
	private static final long serialVersionUID = 3568127802166688832L;
	
	public TurnBonusEridium() {
		bonusId = 7;
		loadVariables();
		imagePath = "turn_bonus_eridium_2.png";
		loadImage();
		name = "Eridium 2";
		description = "<html>Zusätzliche " + eridium + " Eridium zu Beginn der Runde.</html>";
	}
}