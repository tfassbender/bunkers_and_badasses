package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class MarcusGunshop extends Building {
	
	static {
		staticImage = imageLoader.loadImage("marcus_gunship_1_small.png");
	}
	
	public MarcusGunshop() {
		super();
		//TODO change to real values when known
		ammoMining = 1;
		extensionPrice = new int[] {1, 0, 1};
		extendable = true;
	}
	
	@Override
	public void extend() {
		ammoMining = 2;
	}
}