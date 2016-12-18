package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class TinyTinasMine extends Building {
	
	static {
		staticImage = imageLoader.loadImage("mine_1_small.png");
	}
	
	public TinyTinasMine() {
		super();
		landMineVictims = 2;
	}
}