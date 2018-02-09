package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Athena extends Hero {
	
	private static final long serialVersionUID = -2811444506343891702L;

	public Athena() {
		attack = 3;
		defence = 5;
		name = "Athena";
		imagePath = "heros/athena_1.png";
		cardImagePath = "hero_cards/card_athena.png";
		loadImage();
		effectDescription = "Aspis (Kampf):\n\nBei Niederlage können keine deiner Truppen getötet werden";
	}
	
	@Override
	public void execute(Fight fight) {
		
	}
}