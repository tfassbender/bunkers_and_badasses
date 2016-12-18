package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class RolandsTurret extends Building {
	
	static {
		staticImage = imageLoader.loadImage("turret_1_small.png");
	}
	
	public RolandsTurret() {
		super();
		//TODO change to real values when known
		additionalDefence = 1;
		extensionPrice = new int[] {1, 1, 1};
		extendable = true;
	}
	
	@Override
	public void extend() {
		additionalDefence = 2;
	}
}