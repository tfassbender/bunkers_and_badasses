package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Moxxi extends Hero {
	
	private static final long serialVersionUID = -4123023043047504302L;

	static {
		staticImage = imageLoader.loadImage("moxxi_1.png");
	}
	
	public Moxxi() {
		attack = 3;
		defence = 2;
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Anwerbung: 3 normale Truppen beliebig auf dem Feld verteilen
	}
}