package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Brick extends Hero {
	
	private static final long serialVersionUID = -5807273048228466283L;

	public Brick() {
		attack = 5;
		defence = 3;
		name = "Brick";
		imagePath = "brick_1.png";
		loadImage();
		effectDescription = "Klopp Sie:\n\nBeim Sieg sterben beim Verlierer 3 Einheiten mehr als beim Sieger";
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Klopp Sie: Beim Sieg sterben beim Verlierer 3 Einheiten mehr als beim Sieger
	}
}