package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Roland extends Hero {
	
	private static final long serialVersionUID = -1123087849222660325L;

	public Roland() {
		attack = 4;
		defence = 4;
		name = "Roland";
		imagePath = "heros/roland_1.png";
		cardImagePath = "hero_cards/card_roland.png";
		loadImage();
		effectDescription = "Anführer (Kampf):\n\nBeim Sieg werden bis zu 3 der kämpfenden Einheiten aufgerüstet (nicht die unterstützenden Einheiten)";
	}
	
	@Override
	public void execute(Fight fight) {
		//TODO
	}
}