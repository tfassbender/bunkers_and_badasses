package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class RolandsTurret extends Building {
	
	public RolandsTurret() {
		buildingId = 10;
		extendedBuildingId = 11;
		extendable = true;
		name = "Roland's Geschützturm";
		imagePath = "turret_1_small.png";
		expandedImagePath = "turret_1_exp_small.png";
		loadVariables();
		loadImage();
	}
	
	@Override
	public Building newInstance() {
		return new RolandsTurret();
	}
}