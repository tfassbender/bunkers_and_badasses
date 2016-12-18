package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Modecai extends Hero {
	
	static {
		staticImage = imageLoader.loadImage("modecai_1.png");
	}
	
	public Modecai() {
		attack = 4;
		defence = 2;
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Sniper: Zwei beliebige (normale) Einheiten dürfen irgentwo vom Feld genommen werden (auch mehrere Felder; die Felder dürfen dannach nicht leer sein)
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Sniper: Zwei beliebige (normale) Einheiten dürfen irgentwo vom Feld genommen werden (auch mehrere Felder; die Felder dürfen dannach nicht leer sein)
	}
}