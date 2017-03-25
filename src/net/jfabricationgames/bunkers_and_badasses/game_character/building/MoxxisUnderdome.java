package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MoxxisUnderdome extends Building {
	
	public MoxxisUnderdome() {
		buildingId = 8;
		extendedBuildingId = 9;
		extendable = true;
		name = "Mad Moxxis' Underdome";
		image = imageLoader.loadImage("underdome_1_small.png");
		loadVariables();
	}
	
	@Override
	protected void loadExtendedImage() {
		//image = imageLoader.loadImage("underdome_1_small.png");
	}
	
	@Override
	public Building newInstance() {
		return new MoxxisUnderdome();
	}
}