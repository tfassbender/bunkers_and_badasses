package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Salvadore extends Hero {
	
	private static final long serialVersionUID = -8121803559587845378L;

	public Salvadore() {
		attack = 4;
		defence = 3;
		name = "Salvadore";
		image = imageLoader.loadImage("salvadore_1.png");
		effectDescription = "Sperrfeuer:\n\nBei Niederlage dürfen nur die Hälfte der Truppen (abgerundet) weiter ziehen";
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Sperrfeuer: Bei Niederlage dürfen nur die Hälfte der Truppen (abgerundet) weiter Ziehen
	}
}