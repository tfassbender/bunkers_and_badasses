package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Bloodwing extends Hero {
	
	static {
		staticImage = imageLoader.loadImage("bloodwing_1.png");
	}
	
	public Bloodwing() {
		attack = 2;
		defence = 1;
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Luftunterstützung: Die Kampfstärke der gegnerischen Karte wird um 4 gesenkt (Angriff und Verteidigung; Der wert kann nicht unter 0 fallen)
	}
}
