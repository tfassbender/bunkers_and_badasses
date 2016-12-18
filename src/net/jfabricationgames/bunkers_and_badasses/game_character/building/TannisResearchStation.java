package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class TannisResearchStation extends Building {
	
	static {
		staticImage = imageLoader.loadImage("tannis_1_small.png");
	}
	
	public TannisResearchStation() {
		super();
		//TODO change to real values when known
		creditMining = 1;
		eridiumMining = 1;
		extensionPrice = new int[] {1, 0, 1};
		extendable = true;
	}
	
	@Override
	public void extend() {
		creditMining = 2;
		eridiumMining = 2;
	}
}