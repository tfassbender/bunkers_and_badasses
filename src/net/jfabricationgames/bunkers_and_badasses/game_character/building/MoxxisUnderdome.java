package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MoxxisUnderdome extends Building {
	
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