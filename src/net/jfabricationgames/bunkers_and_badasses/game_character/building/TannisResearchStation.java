package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class TannisResearchStation extends Building {
	
	public TannisResearchStation() {
		buildingId = 13;
		extendedBuildingId = 14;
		extendable = true;
		name = "Tannis' Forschungslabor";
		imagePath = "tannis_1_small.png";
		expandedImagePath = "tannis_1_exp_small.png";
		loadVariables();
		loadImage();
	}
	
	@Override
	public Building newInstance() {
		return new TannisResearchStation();
	}
}