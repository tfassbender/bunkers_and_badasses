package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Brick extends Hero {
	
	private static final long serialVersionUID = -5807273048228466283L;

	static {
		staticImage = imageLoader.loadImage("brick_1.png");
	}
	
	public Brick() {
		attack = 5;
		defence = 3;
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Klopp Sie: Beim Sieg sterben beim Verlierer 3 Einheiten mehr als beim Sieger
	}
}