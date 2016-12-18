package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class SirHammerlock extends Hero {
	
	static {
		staticImage = imageLoader.loadImage("hammerlock_1.png");
	}
	
	public SirHammerlock() {
		attack = 1;
		defence = 2;
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Großwildjagt: Bis zu 3 beliebige Neutrale Einheiten werden getötet
	}
}