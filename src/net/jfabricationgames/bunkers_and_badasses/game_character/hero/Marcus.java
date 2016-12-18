package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Marcus extends Hero {
	
	static {
		staticImage = imageLoader.loadImage("marcus_1.png");
	}
	
	public Marcus() {
		attack = 1;
		defence = 2;
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Nachladen: Gratis Munition erhalten
	}
}