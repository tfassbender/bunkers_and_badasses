package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class CrazyEarlsBlackMarket extends Building {
	
	public CrazyEarlsBlackMarket() {
		buildingId = 1;
		extendedBuildingId = 2;
		extendable = true;
		name = "Crazy Earl's Schwarzmarkt";
		imagePath = "earl_1_small.png";
		expandedImagePath = "earl_1_exp_small.png";
		loadVariables();
		loadImage();
	}
	
	@Override
	public Building newInstance() {
		return new CrazyEarlsBlackMarket();
	}
}