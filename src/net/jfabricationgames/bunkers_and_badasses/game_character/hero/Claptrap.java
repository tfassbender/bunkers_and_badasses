package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Claptrap extends Hero {
	
	private static final long serialVersionUID = 3985651599282268746L;

	public Claptrap() {
		attack = 2;
		defence = 2;
		name = "Claptrap";
		imagePath = "heros/claptrap_1.png";
		cardImagePath = "hero_cards/card_claptrap.png";
		loadImage();
		effectDescription = "Downgrade (Kampf):\n\nBis zu 3 gegnerische (Badass-) Truppen werden gedowngraded (vor dem Kampf)";
	}
	
	@Override
	public void execute(Fight fight) {
		//TODO
	}
}