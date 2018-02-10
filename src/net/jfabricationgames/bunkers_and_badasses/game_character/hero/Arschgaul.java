package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Arschgaul extends Hero {
	
	private static final long serialVersionUID = 3449354613837860505L;

	public Arschgaul() {
		attack = 0;
		defence = 0;
		name = "Prinzessin Arschgaul";
		imagePath = "heros/arschgaul_2.png";
		cardImagePath = "hero_cards/card_prinzessin_arschgaul.png";
		loadImage();
		effectDescription = "Wunderschöne Anführerin (Zug):\n\nWähle ein Gebiet aus, dass in der gesammten Runde nicht angegriffen werden darf";
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		
		return null;
	}
	@Override
	public boolean execute(ExecutionData executionData) {
		
		return false;
	}
}