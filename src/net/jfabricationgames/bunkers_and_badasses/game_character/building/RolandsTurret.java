package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class RolandsTurret extends Building {
	
	private static final long serialVersionUID = -6229121601099535139L;

	public RolandsTurret() {
		buildingId = 10;
		extendedBuildingId = 11;
		extendable = true;
		name = "Roland's Gesch�tzturm";
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