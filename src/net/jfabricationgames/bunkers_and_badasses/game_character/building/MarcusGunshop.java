package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MarcusGunshop extends Building {
	
	public MarcusGunshop() {
		buildingId = 4;
		extendedBuildingId = 5;
		extendable = true;
		name = "Marcus' Waffenladen";
		imagePath = "marcus_gunship_1_small.png";
		expandedImagePath = "marcus_gunship_1_exp_small.png";
		loadVariables();
		loadImage();
	}
	
	@Override
	public Building newInstance() {
		return new MarcusGunshop();
	}
}