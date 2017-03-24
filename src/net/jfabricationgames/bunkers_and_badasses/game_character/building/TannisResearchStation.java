package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class TannisResearchStation extends Building {
	
	public TannisResearchStation() {
		super();
		buildingId = 13;
		extendedBuildingId = 14;
		//TODO change to real values when known
		creditMining = 1;
		eridiumMining = 1;
		extendable = true;
		name = "Tannis' Forschungslabor";
		image = imageLoader.loadImage("tannis_1_small.png");
		loadVariables();
	}
	
	@Override
	protected void loadExtendedImage() {
		//image = imageLoader.loadImage("tannis_1_small.png");
	}
	
	@Override
	public Building newInstance() {
		return new TannisResearchStation();
	}
}