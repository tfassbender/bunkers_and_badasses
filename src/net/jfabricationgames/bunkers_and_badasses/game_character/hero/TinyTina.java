package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class TinyTina extends Hero {
	
	private static final long serialVersionUID = -7358701771719372456L;

	static {
		staticImage = imageLoader.loadImage("tina_1.png");
	}
	
	public TinyTina() {
		attack = 2;
		defence = 3;
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Kabummabumms: Ein beliebiges Gebäude (außer Arschgauls Palast) in einem angrenzenden Feld wird zerstört
	}
}