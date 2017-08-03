package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnBonusCredits extends TurnBonus {
	
	private static final long serialVersionUID = 4533987507635344336L;
	
	public TurnBonusCredits() {
		bonusId = 3;
		loadVariables();
		imagePath = "turn_bonus_credits_2.png";
		loadImage();
		name = "Credits 2";
		description = "<html>Zusätzliche " + credits + " Credits zu Beginn der Runde.</html>";
	}
}