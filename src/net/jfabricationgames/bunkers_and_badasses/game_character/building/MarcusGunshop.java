package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MarcusGunshop extends Building {
	
	public MarcusGunshop() {
		buildingId = 4;
		extendedBuildingId = 5;
		extendable = true;
		name = "Marcus' Waffenladen";
		image = imageLoader.loadImage("marcus_gunship_1_small.png");
		loadVariables();
	}
	
	@Override
	protected void loadExtendedImage() {
		//image = imageLoader.loadImage("marcus_gunship_1_small.png");
	}
	
	@Override
	public Building newInstance() {
		return new MarcusGunshop();
	}
}