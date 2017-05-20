package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MarcusGunshop extends Building {
	
	private static final long serialVersionUID = 7481004449327517585L;

	public MarcusGunshop() {
		buildingId = 4;
		extendedBuildingId = 5;
		extendable = true;
		name = "Marcus' Waffenladen";
		imagePath = "marcus_gunshop_1_small.png";
		expandedImagePath = "marcus_gunshop_1_exp_small.png";
		loadVariables();
		loadImage();
	}
	
	@Override
	public Building newInstance() {
		return new MarcusGunshop();
	}
}