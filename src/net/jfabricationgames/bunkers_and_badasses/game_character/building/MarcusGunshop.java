package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MarcusGunshop extends Building {
	
	public MarcusGunshop() {
		super();
		buildingId = 4;
		extendedBuildingId = 5;
		//TODO change to real values when known
		ammoMining = 1;
		extensionPrice = new int[] {1, 0, 1};
		extendable = true;
		image = imageLoader.loadImage("marcus_gunship_1_small.png");
		loadVariables();
	}
	
	@Override
	protected void loadExtendedImage() {
		//image = imageLoader.loadImage("marcus_gunship_1_small.png");
	}
}