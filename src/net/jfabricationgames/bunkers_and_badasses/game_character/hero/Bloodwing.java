package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Bloodwing extends Hero {
	
	private static final long serialVersionUID = 5514081444025611690L;

	public Bloodwing() {
		attack = 2;
		defence = 1;
		name = "Bloodwing";
		imagePath = "bloodwing_1.png";
		loadImage();
		effectDescription = "Luftunterstützung:\n\nDie Kampfstärke der gegnerischen Karte wird um 3 gesenkt (Angriff und Verteidigung; Der wert kann nicht unter 0 fallen)";
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Luftunterstützung: Die Kampfstärke der gegnerischen Karte wird um 4 gesenkt (Angriff und Verteidigung; Der wert kann nicht unter 0 fallen)
	}
}
