package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class ScootersCatchARide extends Building {
	
	public ScootersCatchARide() {
		buildingId = 12;
		name = "Scooter's Catch-A-Ride";
		imagePath = "catch_a_ride_2_small.png";
		loadVariables();
		loadImage();
	}
	
	@Override
	public Building newInstance() {
		return new ScootersCatchARide();
	}
}