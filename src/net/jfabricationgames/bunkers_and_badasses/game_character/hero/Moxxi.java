package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Moxxi extends Hero {
	
	private static final long serialVersionUID = -4123023043047504302L;

	public Moxxi() {
		attack = 3;
		defence = 2;
		name = "Moxxi";
		imagePath = "moxxi_1.png";
		loadImage();
		effectDescription = "Anwerbung:\n\n3 neu rekrutierte Truppen können beliebig auf dem Feld verteilt werden";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Anwerbung: 3 normale Truppen beliebig auf dem Feld verteilen
	}
}