package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Arschgaul extends Hero {
	
	private static final long serialVersionUID = 3449354613837860505L;

	static {
		staticImage = imageLoader.loadImage("arschgaul_2.png");
	}
	
	public Arschgaul() {
		attack = 0;
		defence = 0;
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Wunderschöne Anführerin: Wähle ein Gebiet aus, dass in der gesammten Runde nicht angegriffen werden darf
	}
}