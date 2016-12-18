package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Roland extends Hero {
	
	static {
		staticImage = imageLoader.loadImage("roland_1.png");
	}
	
	public Roland() {
		attack = 4;
		defence = 4;
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Anführer: Beim Sieg werden alle (begrenzt ?) kämpfenden Einheiten aufgerüstet (nicht die unterstützenden Einheiten)
	}
}