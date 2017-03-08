package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Lilith extends Hero {
	
	private static final long serialVersionUID = -8941435166298469244L;

	public Lilith() {
		attack = 4;
		defence = 4;
		name = "Lilith";
		image = imageLoader.loadImage("lilith_1.png");
		effectDescription = "Phasewalk:\n\nBis zu 5 Truppen (Normale oder Badasses) können ein feindliches Feld überspringen und hinter der feindlichen Linie angreifen";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Phasewalk: Begrenzte anzahl von Truppen darf hinter der Feindlichen Linie angreifen (nur ein Feld überspringen)
	}
}