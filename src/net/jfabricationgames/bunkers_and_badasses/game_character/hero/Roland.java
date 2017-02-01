package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Roland extends Hero {
	
	private static final long serialVersionUID = -1123087849222660325L;

	static {
		staticImage = imageLoader.loadImage("roland_1.png");
	}
	
	public Roland() {
		attack = 4;
		defence = 4;
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Anf�hrer: Beim Sieg werden alle (begrenzt ?) k�mpfenden Einheiten aufger�stet (nicht die unterst�tzenden Einheiten)
	}
}