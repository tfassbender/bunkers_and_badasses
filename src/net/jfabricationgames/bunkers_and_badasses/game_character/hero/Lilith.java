package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Lilith extends Hero {
	
	private static final long serialVersionUID = -8941435166298469244L;

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