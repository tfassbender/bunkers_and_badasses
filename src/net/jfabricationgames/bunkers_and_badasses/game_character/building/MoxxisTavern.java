package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MoxxisTavern extends Building {
	
	public MoxxisTavern() {
		super();
		buildingId = 6;
		extendedBuildingId = 7;
		//TODO change to real values when known
		recruitableTroops = 2;
		extensionPrice = new int[] {1, 1, 1};
		extendable = true;
		name = "Moxxis' Taverne";
		image = imageLoader.loadImage("moxxis_1_small.png");
		loadVariables();
	}
	
	@Override
	protected void loadExtendedImage() {
		//image = imageLoader.loadImage("moxxis_1_small.png");
	}
}