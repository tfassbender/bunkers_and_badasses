package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Wilhelm extends Hero {
	
	private static final long serialVersionUID = -3924768912224518016L;

	public Wilhelm() {
		attack = 4;
		defence = 3;
		name = "Wilhelm";
		imagePath = "heros/wilhelm_1.png";
		cardImagePath = "hero_cards/card_wilhelm.png";
		loadImage();
		effectDescription = "Krieg (Kampf):\n\nBeim Sieg dürfen nur die Hälfte der Truppen (aufgerundet) weiterziehen, aber der Marschbefehl darf mitgenommen werden";
	}
	
	@Override
	public void execute(Fight fight) {
		//TODO
	}
}
