package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Axton extends Hero {
	
	static {
		staticImage = imageLoader.loadImage("axton_1.png");
	}
	
	public Axton() {
		attack = 4;
		defence = 3;
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Stratege: Die gegnerische Karte kann nicht mehr gespielt werden (falls sie noch nicht gespielt wurde)
	}
}