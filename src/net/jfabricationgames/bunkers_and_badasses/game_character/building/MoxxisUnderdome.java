package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MoxxisUnderdome extends Building {
	
	private static final long serialVersionUID = -5161948325226351128L;

	public MoxxisUnderdome() {
		buildingId = 8;
		extendedBuildingId = 9;
		extendable = true;
		name = "Mad Moxxis' Underdome";
		imagePath = "underdome_1_small.png";
		expandedImagePath = "underdome_1_exp_small.png";
		loadVariables();
		loadImage();
	}
	
	@Override
	public Building newInstance() {
		return new MoxxisUnderdome();
	}
}