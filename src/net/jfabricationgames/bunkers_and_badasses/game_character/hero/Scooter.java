package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Scooter extends Hero {
	
	private static final long serialVersionUID = -6292099839196271278L;

	static {
		staticImage = imageLoader.loadImage("scooter_3.png");
	}
	
	public Scooter() {
		attack = 1;
		defence = 1;
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Catch-A-Ride: Beliebige Truppen (aus einem Feld) dürfen bis zu 3 Felder weit vorrücken und die Feindliche Linie durchbrechen wenn sie dadurch ein von ihnen kontrolliertes Gebiet erreichen
	}
}