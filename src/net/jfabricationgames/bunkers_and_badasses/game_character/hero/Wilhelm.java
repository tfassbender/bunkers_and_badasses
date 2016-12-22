package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Wilhelm extends Hero {
	
	private static final long serialVersionUID = -3924768912224518016L;

	static {
		staticImage = imageLoader.loadImage("wilhelm_1.png");
	}
	
	public Wilhelm() {
		attack = 4;
		defence = 3;
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Krieg: Bei erfolgreichem Kampf darf der Marschbefehl mitgenommen werden
	}
}
