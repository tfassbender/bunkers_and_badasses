package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Game;

public class Lilith extends Hero {
	
	private static final long serialVersionUID = -8941435166298469244L;

	public Lilith() {
		attack = 4;
		defence = 4;
		name = "Lilith";
		imagePath = "heros/lilith_1.png";
		cardImagePath = "hero_cards/card_lilith.png";
		loadImage();
		effectDescription = "Phasewalk:\n\nBis zu 5 Truppen (Normale oder Badasses) können ein feindliches Feld überspringen und hinter der feindlichen Linie angreifen";
	}
	
	@Override
	public void executeTurn(Game game) {
		//TODO
		//Phasewalk: Begrenzte anzahl von Truppen darf hinter der Feindlichen Linie angreifen (nur ein Feld �berspringen)
	}
}