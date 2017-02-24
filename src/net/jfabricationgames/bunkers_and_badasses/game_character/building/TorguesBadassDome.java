package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class TorguesBadassDome extends Building {
	
	public TorguesBadassDome() {
		super();
		buildingId = 16;
		badassTroopsRecruitable = true;
		image = imageLoader.loadImage("torgue_1_small.png");
		loadVariables();
	}
}