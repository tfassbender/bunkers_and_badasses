package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MoxxisTavern extends Building {
	
	static {
		staticImage = imageLoader.loadImage("moxxis_1_small.png");
	}
	
	public MoxxisTavern() {
		super();
		//TODO change to real values when known
		recruitableTroops = 2;
		extensionPrice = new int[] {1, 1, 1};
		extendable = true;
	}
	
	@Override
	public void extend() {
		recruitableTroops = 3;
	}
}