package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class ScootersCatchARide extends Building {
	
	private static final long serialVersionUID = 3906562384521981462L;

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