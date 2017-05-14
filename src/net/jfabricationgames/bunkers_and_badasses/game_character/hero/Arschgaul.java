package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Arschgaul extends Hero {
	
	private static final long serialVersionUID = 3449354613837860505L;

	public Arschgaul() {
		attack = 0;
		defence = 0;
		name = "Prinzessin Arschgaul";
		imagePath = "arschgaul_2.png";
		loadImage();
		effectDescription = "Wunderschöne Anführerin:\n\nWähle ein Gebiet aus, dass in der gesammten Runde nicht angegriffen werden darf";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Wundersch�ne Anf�hrerin: W�hle ein Gebiet aus, dass in der gesammten Runde nicht angegriffen werden darf
	}
}