package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Game;

public class Zero extends Hero {
	
	private static final long serialVersionUID = 8601494966049836609L;
	
	public Zero() {
		attack = 3;
		defence = 4;
		name = "Zero";
		imagePath = "heros/zero_1.png";
		cardImagePath = "hero_cards/card_zero.png";
		loadImage();
		effectDescription = "Doppelgänger (Zug):\n\nEin beliebiger Befehlsmarker kann nachträglich (kostenlos, beliebig) verändert werden";
	}
	
	@Override
	public void executeTurn(Game game) {
		//TODO
	}
}