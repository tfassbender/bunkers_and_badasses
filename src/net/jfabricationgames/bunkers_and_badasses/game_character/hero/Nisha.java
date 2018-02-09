package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Nisha extends Hero {
	
	private static final long serialVersionUID = -267769346062408045L;

	public Nisha() {
		attack = 3;
		defence = 2;
		name = "Nisha";
		imagePath = "heros/nisha_1.png";
		cardImagePath = "hero_cards/card_nisha.png";
		loadImage();
		effectDescription = "Blitzangriff (Kampf):\n\nIm Kampf kann keine der beiden Seiten unterstützt werden";
	}
	
	@Override
	public void execute(Fight fight) {
		//TODO
	}
}