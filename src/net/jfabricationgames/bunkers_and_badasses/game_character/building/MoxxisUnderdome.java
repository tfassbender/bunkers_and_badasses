package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MoxxisUnderdome extends Building {
	
	static {
		staticImage = imageLoader.loadImage("underdome_1_small.png");
	}
	
	public MoxxisUnderdome() {
		super();
		//TODO change to real values when known
		creditMining = 1;
		extensionPrice = new int[] {1, 0, 1};
		extendable = true;
	}
	
	@Override
	public void extend() {
		creditMining = 2;
	}
}