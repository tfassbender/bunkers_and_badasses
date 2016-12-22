package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Athena extends Hero {
	
	private static final long serialVersionUID = -2811444506343891702L;

	static {
		staticImage = imageLoader.loadImage("athena_1.png");
	}
	
	public Athena() {
		attack = 3;
		defence = 5;
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Aspis: Bei Niederlage fallen keine Truppen 
	}
}