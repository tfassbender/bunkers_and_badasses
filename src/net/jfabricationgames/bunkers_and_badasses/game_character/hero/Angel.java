package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Angel extends Hero {
	
	static {
		staticImage = imageLoader.loadImage("angel_1.png");
	}
	
	public Angel() {
		attack = 2;
		defence = 2;
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Perfekte Aufkl�hrung: In der gesammten Runde d�rfen im Kampf keine Karten gegen dich ausgespielt werden
	}
}