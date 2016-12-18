package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class ScootersCatchARide extends Building {
	
	static {
		staticImage = imageLoader.loadImage("catch_a_ride_2_small.png");
	}
	
	public ScootersCatchARide() {
		super();
		moveDistance = 2;
	}
}