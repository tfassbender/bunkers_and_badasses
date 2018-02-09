package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Game;

public class Moxxi extends Hero {
	
	private static final long serialVersionUID = -4123023043047504302L;

	public Moxxi() {
		attack = 3;
		defence = 2;
		name = "Moxxi";
		imagePath = "heros/moxxi_1.png";
		cardImagePath = "hero_cards/card_mad_moxxi.png";
		loadImage();
		effectDescription = "Anwerbung (Zug):\n\n3 normale Truppen beliebig auf deine Felder verteilen";
	}
	
	@Override
	public void executeTurn(Game game) {
		//TODO
	}
}