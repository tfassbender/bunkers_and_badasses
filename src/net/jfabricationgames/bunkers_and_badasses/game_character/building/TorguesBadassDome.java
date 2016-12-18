package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class TorguesBadassDome extends Building {
	
	static {
		staticImage = imageLoader.loadImage("torgue_1_small.png");
	}
	
	public TorguesBadassDome() {
		super();
		badassTroopsRecruitable = true;
	}
}