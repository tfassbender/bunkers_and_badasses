package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Claptrap extends Hero {
	
	private static final long serialVersionUID = 3985651599282268746L;

	static {
		staticImage = imageLoader.loadImage("claptrap_1.png");
	}
	
	public Claptrap() {
		attack = 2;
		defence = 2;
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Downgrade: Alle (begrenzt ?) gegnerischen (aufgerüsteten) Truppen werden sofort gedowngraded
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Downgrade: Alle (begrenzt ?) gegnerischen (aufgerüsteten) Truppen werden sofort gedowngraded
	}
}