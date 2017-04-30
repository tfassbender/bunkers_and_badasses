package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class TinyTinasMine extends Building {
	
	public TinyTinasMine() {
		buildingId = 15;
		name = "Tiny Tina's Landmienen";
		imagePath = "mine_1_small.png";
		loadVariables();
		loadImage();
	}
	
	@Override
	public Building newInstance() {
		return new TinyTinasMine();
	}
}