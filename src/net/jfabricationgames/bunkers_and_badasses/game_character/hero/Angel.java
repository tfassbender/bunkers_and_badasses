package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Game;

public class Angel extends Hero {
	
	private static final long serialVersionUID = -7693149616225773067L;
	
	public Angel() {
		attack = 2;
		defence = 2;
		name = "Angel";
		imagePath = "heros/angel_1.png";
		cardImagePath = "hero_cards/card_angel.png";
		loadImage();
		effectDescription = "Perfekte Aufklährung (Zug):\n\nIn der gesammten Runde dürfen im Kampf keine Karten gegen dich eingesetzt werden";
	}
	
	@Override
	public void executeTurn(Game game) {
		
	}
}