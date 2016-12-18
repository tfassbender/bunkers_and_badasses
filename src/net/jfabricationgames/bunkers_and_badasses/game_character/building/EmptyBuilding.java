package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class EmptyBuilding extends Building {
	
	static {
		staticImage = imageLoader.loadImage("empty.png");
	}
	
	public EmptyBuilding() {
		super();
	}
}