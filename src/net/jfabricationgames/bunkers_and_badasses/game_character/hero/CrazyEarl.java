package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Game;

public class CrazyEarl extends Hero {
	
	private static final long serialVersionUID = -1504504671280272179L;

	public CrazyEarl() {
		attack = 0;
		defence = 3;
		name = "Crazy Earl";
		imagePath = "heros/earl_1.png";
		cardImagePath = "hero_cards/card_crazy_earl.png";
		loadImage();
		effectDescription = "Schwarzmarkt (Zug):\n\nDu erhällst gratis Eridium (15) und Credits (50)";
	}
	
	@Override
	public void executeTurn(Game game) {
		//TODO
		//Schwarzmarkt: Gratis Eridium erhalten
	}
}