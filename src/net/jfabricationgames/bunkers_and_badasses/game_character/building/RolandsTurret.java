package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class RolandsTurret extends Building {
	
	public RolandsTurret() {
		super();
		buildingId = 10;
		extendedBuildingId = 11;
		//TODO change to real values when known
		additionalDefence = 1;
		extensionPrice = new int[] {1, 1, 1};
		extendable = true;
		name = "Roland's Geschützturm";
		image = imageLoader.loadImage("turret_1_small.png");
		loadVariables();
	}
	
	@Override
	protected void loadExtendedImage() {
		//image = imageLoader.loadImage("turret_1_small.png");
	}
	
	@Override
	public Building newInstance() {
		return new RolandsTurret();
	}
}