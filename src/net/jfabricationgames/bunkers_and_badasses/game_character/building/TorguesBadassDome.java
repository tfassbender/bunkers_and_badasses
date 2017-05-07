package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class TorguesBadassDome extends Building {
	
	private static final long serialVersionUID = -5733694029357921058L;

	public TorguesBadassDome() {
		buildingId = 16;
		badassTroopsRecruitable = true;
		name = "Mr. Trogue's Badassdome of Badassitude";
		imagePath = "torgue_1_small.png";
		loadVariables();
		loadImage();
	}
	
	@Override
	public Building newInstance() {
		return new TorguesBadassDome();
	}
}