package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Scooter extends Hero {
	
	private static final long serialVersionUID = -6292099839196271278L;

	public Scooter() {
		attack = 1;
		defence = 1;
		name = "Scooter";
		imagePath = "scooter_3.png";
		loadImage();
		effectDescription = "Catch-A-Ride:\n\nBeliebige Truppen (aus einem Feld) dürfen bis zu 2 Felder weit vorrücken und die Feindliche Linie durchbrechen wenn sie dadurch ein von ihnen kontrolliertes oder leeres Gebiet erreichen";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Catch-A-Ride: Beliebige Truppen (aus einem Feld) dürfen bis zu 2 Felder weit vorrücken und die Feindliche Linie durchbrechen wenn sie dadurch ein von ihnen kontrolliertes oder leeres Gebiet erreichen
	}
}