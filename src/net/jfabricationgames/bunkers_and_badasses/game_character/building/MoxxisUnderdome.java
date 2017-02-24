package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MoxxisUnderdome extends Building {
	
	public MoxxisUnderdome() {
		super();
		buildingId = 8;
		extendedBuildingId = 9;
		//TODO change to real values when known
		creditMining = 1;
		extensionPrice = new int[] {1, 0, 1};
		extendable = true;
		image = imageLoader.loadImage("underdome_1_small.png");
		loadVariables();
	}
	
	@Override
	protected void loadExtendedImage() {
		//image = imageLoader.loadImage("underdome_1_small.png");
	}
}