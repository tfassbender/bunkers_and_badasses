package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Springs extends Hero {
	
	static {
		staticImage = imageLoader.loadImage("springs_1.png");
	}
	
	public Springs() {
		attack = 1;
		defence = 2;
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Beliebig viele Einheiten aus einem Gebiet können in andere schon kontrollierte gebiete verschoben werden (nicht im Kampf einsetzbar)
	}
}