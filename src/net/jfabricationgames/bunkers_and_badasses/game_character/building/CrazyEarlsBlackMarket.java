package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class CrazyEarlsBlackMarket extends Building {
	
	public CrazyEarlsBlackMarket() {
		super();
		buildingId = 1;
		extendedBuildingId = 2;
		//TODO change to real values when known
		eridiumMining = 1;
		extensionPrice = new int[] {1, 0, 1};
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