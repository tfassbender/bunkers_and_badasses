package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class DrZed extends Hero {
	
	private static final long serialVersionUID = 5153305812609894519L;

	public DrZed() {
		attack = 1;
		defence = 2;
		name = "Dr. Zed";
		image = imageLoader.loadImage("dr_zed_1.png");
		effectDescription = "Feld Metzger:\n\nZed kann die Hälfte der Truppen (aufgerundet) die in einem Kampf sterben würden wieder zusammenflicken";
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Feld Metzger: Zed kann die Hälfte der Truppen (aufgerundet) die sterben würden wieder zusammenflicken
	}
}