package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class CrazyEarl extends Hero {
	
	static {
		staticImage = imageLoader.loadImage("earl_1.png");
	}
	
	public CrazyEarl() {
		attack = 0;
		defence = 1;
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Schwarzmarkt: Gratis Eridium erhalten
	}
}