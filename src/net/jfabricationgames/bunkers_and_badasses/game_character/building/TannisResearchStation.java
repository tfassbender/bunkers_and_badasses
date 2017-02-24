package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class TannisResearchStation extends Building {
	
	public TannisResearchStation() {
		super();
		buildingId = 13;
		extendedBuildingId = 14;
		//TODO change to real values when known
		creditMining = 1;
		eridiumMining = 1;
		extensionPrice = new int[] {1, 0, 1};
		extendable = true;
		image = imageLoader.loadImage("tannis_1_small.png");
		loadVariables();
	}
	
	@Override
	protected void loadExtendedImage() {
		//image = imageLoader.loadImage("tannis_1_small.png");
	}
}