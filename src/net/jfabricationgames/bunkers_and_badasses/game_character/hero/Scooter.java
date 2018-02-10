package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.function.Predicate;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;

public class Scooter extends Hero {
	
	private static final long serialVersionUID = -6292099839196271278L;
	
	private Predicate<Field> maxThreeFieldsFromLocalUsersField = field -> field.getNeighbours().parallelStream().
			flatMap(f2 -> f2.getNeighbours().parallelStream()).flatMap(f3 -> f3.getNeighbours().parallelStream()).anyMatch(localPlayersField);

	public Scooter() {
		attack = 1;
		defence = 1;
		name = "Scooter";
		imagePath = "heros/scooter_3.png";
		cardImagePath = "hero_cards/card_scooter.png";
		loadImage();
		effectDescription = "Catch-A-Ride (Zug):\n\nBeliebig viele Truppen (aus einem Feld) dürfen bis zu 3 Felder weit vorrücken (und die Feindliche Linie durchbrechen, wenn sie ein eigenes Feld erreichen)";
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