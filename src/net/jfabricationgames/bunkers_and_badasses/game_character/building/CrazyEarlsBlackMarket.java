package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class CrazyEarlsBlackMarket extends Building {
	
	static {
		staticImage = imageLoader.loadImage("earl_1_small.png");
	}
	
	public CrazyEarlsBlackMarket() {
		super();
		//TODO change to real values when known
		eridiumMining = 1;
		extensionPrice = new int[] {1, 0, 1};
		extendable = true;
	}
	
	@Override
	public void extend() {
		eridiumMining = 2;
	}
}