package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Angel extends Hero {
	
	private static final long serialVersionUID = -7693149616225773067L;
	
	public Angel() {
		attack = 2;
		defence = 2;
		name = "Angel";
		image = imageLoader.loadImage("angel_1.png");
		effectDescription = "Perfekte Aufklährung:\n\nIn der gesammten Runde dürfen im Kampf keine Karten gegen dich ausgespielt werden";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Perfekte Aufklährung: In der gesammten Runde dürfen im Kampf keine Karten gegen dich ausgespielt werden
	}
}