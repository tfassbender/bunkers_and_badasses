package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Zero extends Hero {
	
	private static final long serialVersionUID = 8601494966049836609L;

	static {
		staticImage = imageLoader.loadImage("zero_1.png");
	}
	
	public Zero() {
		attack = 3;
		defence = 4;
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Doppelgänger: Ein beliebiger Befehlsmarker kann nachträglich verändert werden
	}
}