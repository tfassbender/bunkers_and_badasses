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
		effectDescription = "Krieg:\n\nBei erfolgreichem Kampf darf der Marschbefehl mitgenommen werden";
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Krieg: Bei erfolgreichem Kampf darf der Marschbefehl mitgenommen werden
	}
}
