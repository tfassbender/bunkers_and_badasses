package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class CrazyEarlsBlackMarket extends Building {
	
	public CrazyEarlsBlackMarket() {
		buildingId = 1;
		extendedBuildingId = 2;
		extendable = true;
		name = "Crazy Earl's Schwarzmarkt";
		image = imageLoader.loadImage("earl_1_small.png");
		loadVariables();
	}
	
	@Override
	protected void loadExtendedImage() {
		//image = imageLoader.loadImage("earl_1_small.png");
	}
	
	@Override
	public Building newInstance() {
		return new CrazyEarlsBlackMarket();
	}
}