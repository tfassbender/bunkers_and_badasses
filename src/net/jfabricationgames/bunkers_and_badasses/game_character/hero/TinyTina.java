package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class TinyTina extends Hero {
	
	private static final long serialVersionUID = -7358701771719372456L;

	public TinyTina() {
		attack = 2;
		defence = 3;
		name = "Tiny Tina";
		imagePath = "heros/tina_1.png";
		cardImagePath = "hero_cards/card_tiny_tina.png";
		loadImage();
		effectDescription = "Kabummabumms (Zug):\n\nEin beliebiges Gebäude (außer Arschgauls Palast) in einem angrenzenden Feld wird zerstört";
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