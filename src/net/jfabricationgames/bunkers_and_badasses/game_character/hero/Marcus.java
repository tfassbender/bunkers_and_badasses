package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Marcus extends Hero {
	
	private static final long serialVersionUID = -2964770085479731401L;

	public Marcus() {
		attack = 1;
		defence = 2;
		name = "Marcus";
		imagePath = "heros/marcus_1.png";
		cardImagePath = "hero_cards/card_marcus.png";
		loadImage();
		effectDescription = "Nachladen:\n\nDu erhällst gratis Munition";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Nachladen: Gratis Munition erhalten
	}
}