package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Game;

public class Modecai extends Hero {
	
	private static final long serialVersionUID = -3664578792584135642L;

	public Modecai() {
		attack = 4;
		defence = 2;
		name = "Modecai";
		imagePath = "heros/modecai_1.png";
		cardImagePath = "hero_cards/card_modecai.png";
		loadImage();
		effectDescription = "Sniper (Zug):\n\nBis zu 3 beliebige (normale) Einheiten dürfen irgentwo vom Feld genommen werden (auch mehrere Felder; die Felder dürfen dannach nicht leer sein)";
	}
	
	@Override
	public void executeTurn(Game game) {
		//TODO
	}
}