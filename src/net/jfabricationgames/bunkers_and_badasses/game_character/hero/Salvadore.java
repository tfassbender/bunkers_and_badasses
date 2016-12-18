package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Salvadore extends Hero {
	
	static {
		staticImage = imageLoader.loadImage("salvadore_1.png");
	}
	
	public Salvadore() {
		attack = 4;
		defence = 3;
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Sperrfeuer: Bei Niederlage dürfen nur die Hälfte der Truppen (abgerundet) weiter Ziehen
	}
}