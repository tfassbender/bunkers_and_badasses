package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Ellie extends Hero {
	
	private static final long serialVersionUID = 8110478891996236498L;

	public Ellie() {
		attack = 2;
		defence = 1;
		name = "Ellie";
		imagePath = "heros/ellie_1.png";
		cardImagePath = "hero_cards/card_ellie.png";
		loadImage();
		effectDescription = "Klanfede:\n\nIn 2 aneinander grenzenden, gegnerischen Gebieten sterben jeweils 2 Einheiten (nur Banditen)";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Klanfede: In 2 aneinander grenzenden, gegnerischen Gebieten sterben jeweils 2 Einheiten (nur Banditen)
	}
}