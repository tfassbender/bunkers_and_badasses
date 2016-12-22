package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Nisha extends Hero {
	
	private static final long serialVersionUID = -267769346062408045L;

	static {
		staticImage = imageLoader.loadImage("nisha_1.png");
	}
	
	public Nisha() {
		attack = 3;
		defence = 2;
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Blitzangriff: Bei Verteidigung tauschen Angreifer und Verteidiger (Ändert Punkteverhältniss); Nach dem Kampf darf daher keine Bewegung stattfinden (egal wer gewinnt)
	}
}