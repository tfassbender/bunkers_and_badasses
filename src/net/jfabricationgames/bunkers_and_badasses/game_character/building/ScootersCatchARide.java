package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class ScootersCatchARide extends Building {
	
	public ScootersCatchARide() {
		super();
		buildingId = 12;
		//TODO
		moveDistance = 2;
		name = "Scooter's Catch-A-Ride";
		image = imageLoader.loadImage("catch_a_ride_2_small.png");
		loadVariables();
	}
	
	@Override
	public Building newInstance() {
		return new ScootersCatchARide();
	}
}