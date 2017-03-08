package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Ellie extends Hero {
	
	private static final long serialVersionUID = 8110478891996236498L;

	public Ellie() {
		attack = 2;
		defence = 1;
		name = "Ellie";
		image = imageLoader.loadImage("ellie_1.png");
		effectDescription = "Klanfede:\n\nIn 2 aneinander grenzenden, gegnerischen Gebieten sterben jeweils 2 Einheiten";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Klanfede: In 2 aneinander grenzenden, gegnerischen Gebieten sterben jeweils 2 Einheiten
	}
}