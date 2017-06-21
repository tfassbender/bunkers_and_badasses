package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class SirHammerlock extends Hero {
	
	private static final long serialVersionUID = 6591200292813086025L;

	public SirHammerlock() {
		attack = 1;
		defence = 2;
		name = "Sir Hammerlock";
		imagePath = "heros/hammerlock_1.png";
		cardImagePath = "hero_cards/card_sir_hammerlock.png";
		loadImage();
		effectDescription = "Großwildjagt:\n\nBis zu 3 beliebige Neutrale Einheiten werden getötet";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Gro�wildjagt: Bis zu 3 beliebige Neutrale Einheiten werden get�tet
	}
}