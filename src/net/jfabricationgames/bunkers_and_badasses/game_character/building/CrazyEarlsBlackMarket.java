package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class CrazyEarlsBlackMarket extends Building {
	
	private static final long serialVersionUID = -4191238998602148831L;

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