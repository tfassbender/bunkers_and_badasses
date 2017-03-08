package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Axton extends Hero {
	
	private static final long serialVersionUID = 6188313370223500688L;

	public Axton() {
		attack = 4;
		defence = 3;
		name = "Axton";
		image = imageLoader.loadImage("axton_1.png");
		effectDescription = "Stratege:\n\nDie gegnerische Karte kann nicht mehr gespielt werden (falls sie noch nicht gespielt wurde; Der Gegner erhällt Sie zurück)";
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Stratege: Die gegnerische Karte kann nicht mehr gespielt werden (falls sie noch nicht gespielt wurde)
	}
}