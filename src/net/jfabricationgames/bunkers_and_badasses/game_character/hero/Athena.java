package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Athena extends Hero {
	
	private static final long serialVersionUID = -2811444506343891702L;

	public Athena() {
		attack = 3;
		defence = 5;
		name = "Athena";
		imagePath = "heros/athena_1.png";
		cardImagePath = "hero_cards/card_athena.png";
		loadImage();
		effectDescription = "Aspis (Kampf):\n\nBei Niederlage können keine deiner Truppen getötet werden";
		executionType = ExecutionType.DURING_FIGHT;
	}
	
	@Override
	public void execute(Fight fight) {
		//implemented in Fight.getFallingTroopsLooser and Fight.getFallingTroopsSupport because the values are not changed but their return values
	}
}