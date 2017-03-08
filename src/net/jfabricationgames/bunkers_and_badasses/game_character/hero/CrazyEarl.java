package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class CrazyEarl extends Hero {
	
	private static final long serialVersionUID = -1504504671280272179L;

	public CrazyEarl() {
		attack = 0;
		defence = 1;
		name = "Crazy Earl";
		image = imageLoader.loadImage("earl_1.png");
		effectDescription = "Schwarzmarkt:\n\nDu erh�lltst gratis Eridium";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Schwarzmarkt: Gratis Eridium erhalten
	}
}