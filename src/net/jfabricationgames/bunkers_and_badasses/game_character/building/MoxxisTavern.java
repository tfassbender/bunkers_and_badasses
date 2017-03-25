package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MoxxisTavern extends Building {
	
	public MoxxisTavern() {
		buildingId = 6;
		extendedBuildingId = 7;
		extendable = true;
		commandExecutable = true;
		name = "Moxxis' Taverne";
		image = imageLoader.loadImage("moxxis_1_small.png");
		loadVariables();
	}
	
	@Override
	protected void loadExtendedImage() {
		//image = imageLoader.loadImage("moxxis_1_small.png");
	}
	
	@Override
	public Building newInstance() {
		return new MoxxisTavern();
	}
}