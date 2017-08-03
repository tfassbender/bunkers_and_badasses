package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnBonusAmmo extends TurnBonus {
	
	private static final long serialVersionUID = 3577890652879933227L;
	
	public TurnBonusAmmo() {
		bonusId = 0;
		loadVariables();
		imagePath = "turn_bonus_ammo_2.png";
		loadImage();
		name = "Munition 2";
		description = "<html>Zusätzliche " + ammo + " Munition zu Beginn der Runde.</html>";
	}
}