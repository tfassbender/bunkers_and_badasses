package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Brick extends Hero {
	
	private static final long serialVersionUID = -5807273048228466283L;

	public Brick() {
		attack = 5;
		defence = 3;
		name = "Brick";
		imagePath = "heros/brick_1.png";
		cardImagePath = "hero_cards/card_brick.png";
		loadImage();
		effectDescription = "Klopp Sie (Kampf):\n\nBeim Sieg sterben beim Verlierer 3 Einheiten mehr als beim Sieger";
	}
	
	@Override
	public void execute(Fight fight) {
		//TODO
	}
}