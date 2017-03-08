package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Claptrap extends Hero {
	
	private static final long serialVersionUID = 3985651599282268746L;

	public Claptrap() {
		attack = 2;
		defence = 2;
		name = "Claptrap";
		image = imageLoader.loadImage("claptrap_1.png");
		effectDescription = "Downgrade:\n\nBis zu 3 gegnerischen Badasses werden sofort gedowngraded (zu normalen Truppen)";
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Downgrade: Alle (begrenzt ?) gegnerischen (aufgerüsteten) Truppen werden sofort gedowngraded
	}
}