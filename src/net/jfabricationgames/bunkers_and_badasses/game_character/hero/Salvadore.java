package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Salvadore extends Hero {
	
	private static final long serialVersionUID = -8121803559587845378L;

	public Salvadore() {
		attack = 4;
		defence = 3;
		name = "Salvadore";
		imagePath = "heros/salvador_1.png";
		cardImagePath = "hero_cards/card_salvador.png";
		loadImage();
		effectDescription = "Sperrfeuer (Kampf):\n\nBei Niederlage dürfen nur die Hälfte der Truppen (abgerundet) weiter Ziehen (auch 0 möglich; das Feld wird aber erobert)";
	}
	
	@Override
	public void execute(Fight fight) {
		//TODO
	}
}