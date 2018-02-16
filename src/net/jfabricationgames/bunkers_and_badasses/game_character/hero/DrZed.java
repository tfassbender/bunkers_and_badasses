package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class DrZed extends Hero {
	
	private static final long serialVersionUID = 5153305812609894519L;

	public DrZed() {
		attack = 1;
		defence = 2;
		name = "Dr. Zed";
		imagePath = "heros/dr_zed_1.png";
		cardImagePath = "hero_cards/card_dr_zed.png";
		loadImage();
		effectDescription = "Feld Metzger (Kampf):\n\nZed kann die Hälfte deiner Truppen (aufgerundet) die sterben würden wieder zusammenflicken";
		executionType = ExecutionType.DURING_FIGHT;
	}
	
	@Override
	public void execute(Fight fight) {
		//implemented in Fight.getFallingTroopsLooser and Fight.getFallingTroopsSupport because the values are not changed but their return values
	}
}