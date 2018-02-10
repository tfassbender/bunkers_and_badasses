package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Ellie extends Hero {
	
	private static final long serialVersionUID = 8110478891996236498L;

	public Ellie() {
		attack = 2;
		defence = 1;
		name = "Ellie";
		imagePath = "heros/ellie_1.png";
		cardImagePath = "hero_cards/card_ellie.png";
		loadImage();
		effectDescription = "Klanfede (Zug):\n\nIn 2 aneinander grenzenden, gegnerischen Gebieten sterben jeweils bis zu 2 Einheiten (die Felder dürfen dannach nicht leer sein)";
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