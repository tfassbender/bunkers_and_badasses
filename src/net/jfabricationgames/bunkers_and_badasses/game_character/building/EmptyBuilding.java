package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class EmptyBuilding extends Building {
	
	public EmptyBuilding() {
		buildingId = 3;
		image = imageLoader.loadImage("empty.png");
		name = "-----";
		loadVariables();
	}
	
	@Override
	public Building newInstance() {
		return new EmptyBuilding();
	}
}