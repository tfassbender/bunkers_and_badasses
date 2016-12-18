package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Lilith extends Hero {
	
	static {
		staticImage = imageLoader.loadImage("lilith_1.png");
	}
	
	public Lilith() {
		attack = 4;
		defence = 4;
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Phasewalk: Begrenzte anzahl von Truppen darf hinter der Feindlichen Linie angreifen (nur ein Feld überspringen)
	}
}