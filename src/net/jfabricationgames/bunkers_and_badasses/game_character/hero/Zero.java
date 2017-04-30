package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Zero extends Hero {
	
	private static final long serialVersionUID = 8601494966049836609L;
	
	public Zero() {
		attack = 3;
		defence = 4;
		name = "Zero";
		imagePath = "zero_1.png";
		loadImage();
		effectDescription = "Doppelg�nger:\n\nEin beliebiger Befehlsmarker kann nachtr�glich ver�ndert werden";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Doppelg�nger: Ein beliebiger Befehlsmarker kann nachtr�glich ver�ndert werden
	}
}