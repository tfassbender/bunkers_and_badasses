package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MoxxisTavern extends Building {
	
	private static final long serialVersionUID = -3175792660828458725L;

	public MoxxisTavern() {
		buildingId = 6;
		extendedBuildingId = 7;
		extendable = true;
		commandExecutable = true;
		name = "Moxxis' Taverne";
		imagePath = "moxxis_1_small.png";
		expandedImagePath = "moxxis_1_exp_small.png";
		loadVariables();
		loadImage();
	}
	
	@Override
	public Building newInstance() {
		return new MoxxisTavern();
	}
}