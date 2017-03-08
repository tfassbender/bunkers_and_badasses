package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Springs extends Hero {
	
	private static final long serialVersionUID = -5937437808209595383L;

	public Springs() {
		attack = 1;
		defence = 2;
		name = "Springs";
		image = imageLoader.loadImage("springs_1.png");
		effectDescription = "Raketentechnickerin:\n\nBeliebig viele Einheiten aus einem Gebiet können in andere schon kontrollierte gebiete verschoben werden (nicht im Kampf einsetzbar)";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Raketentechnickerin: Beliebig viele Einheiten aus einem Gebiet können in andere schon kontrollierte gebiete verschoben werden (nicht im Kampf einsetzbar)
	}
}