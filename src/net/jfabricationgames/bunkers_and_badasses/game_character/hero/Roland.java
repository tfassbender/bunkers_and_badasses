package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Roland extends Hero {
	
	private static final long serialVersionUID = -1123087849222660325L;

	public Roland() {
		attack = 4;
		defence = 4;
		name = "Roland";
		imagePath = "roland_1.png";
		loadImage();
		effectDescription = "Anführer:\n\nBeim Sieg werden bis zu 3 kämpfenden Einheiten aufgerüstet (nicht die unterstützenden Einheiten)";
	}
	
	@Override
	public void executeFight(Fight fight) {
		//TODO
		//Anführer: Beim Sieg werden bis zu 3 kämpfenden Einheiten aufgerüstet (nicht die unterstützenden Einheiten)
	}
}