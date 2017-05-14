package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Modecai extends Hero {
	
	private static final long serialVersionUID = -3664578792584135642L;

	public Modecai() {
		attack = 4;
		defence = 2;
		name = "Modecai";
		imagePath = "modecai_1.png";
		loadImage();
		effectDescription = "Sniper:\n\nZwei beliebige (normale) Einheiten dürfen irgentwo vom Feld genommen werden (auch aus mehreren Feldern; die Felder dürfen dannach nicht leer sein)";
	}
	
	@Override
	public void executeFight(Fight fight) {
		
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Sniper: Zwei beliebige (normale) Einheiten d�rfen irgentwo vom Feld genommen werden (auch mehrere Felder; die Felder d�rfen dannach nicht leer sein)
	}
}