package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class TinyTinasMine extends Building {
	
	public TinyTinasMine() {
		buildingId = 15;
		name = "Tiny Tina's Landmienen";
		image = imageLoader.loadImage("mine_1_small.png");
		loadVariables();
	}
	
	@Override
	public Building newInstance() {
		return new TinyTinasMine();
	}
}