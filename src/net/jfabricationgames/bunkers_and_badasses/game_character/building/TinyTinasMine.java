package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class TinyTinasMine extends Building {
	
	public TinyTinasMine() {
		super();
		buildingId = 15;
		landMineVictims = 2;
		name = "Tiny Tina's Landmienen";
		image = imageLoader.loadImage("mine_1_small.png");
		loadVariables();
	}
}