package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Athena extends Hero {
	
	private static final long serialVersionUID = -2811444506343891702L;

	public Athena() {
		attack = 3;
		defence = 5;
		name = "Athena";
		imagePath = "athena_1.png";
		loadImage();
		effectDescription = "Aspis:\n\nBei einer Niederlage können keine deiner Truppen getötet werden";
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Aspis: Bei Niederlage fallen keine Truppen 
	}
}