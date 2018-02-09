package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Game;

public class MrTorgue extends Hero {
	
	private static final long serialVersionUID = 2488254569700119667L;

	public MrTorgue() {
		attack = 3;
		defence = 3;
		name = "Mr. Trogue";
		imagePath = "heros/mr_torgue_1.png";
		cardImagePath = "hero_cards/card_mr_torgue.png";
		loadImage();
		effectDescription = "EXPLOSIONSGERÄUSCH (Zug):\n\nBis zu 3 beliebige normale Truppen werden zu BADASSES aufgerüstet";
	}
	
	@Override
	public void executeTurn(Game game) {
		//TODO
	}
}