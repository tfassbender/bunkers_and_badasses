package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class DrZed extends Hero {
	
	static {
		staticImage = imageLoader.loadImage("dr_zed_1.png");
	}
	
	public DrZed() {
		attack = 1;
		defence = 2;
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Feld Metzger: Zed kann die Hälfte der Truppen (aufgerundet) die sterben würden wieder zusammenflicken
	}
}