package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Nisha extends Hero {
	
	private static final long serialVersionUID = -267769346062408045L;

	public Nisha() {
		attack = 3;
		defence = 2;
		name = "Nisha";
		imagePath = "nisha_1.png";
		loadImage();
		effectDescription = "Blitzangriff:\n\nVerteidigt der Spieler der diese Karte einsetzt, tauschen Angreifer und Verteidiger wodurch Effekte und das Kr�fteverh�ltniss beeinflusst werden; Nach dem Kampf darf daher keine Bewegung stattfinden (egal wer gewinnt)";
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Blitzangriff: Bei Verteidigung tauschen Angreifer und Verteidiger (�ndert Punkteverh�ltniss); Nach dem Kampf darf daher keine Bewegung stattfinden (egal wer gewinnt)
	}
}