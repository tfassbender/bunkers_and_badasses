package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.SpinnerNumberModel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class SirHammerlock extends Hero {
	
	private static final long serialVersionUID = 6591200292813086025L;

	public SirHammerlock() {
		attack = 1;
		defence = 2;
		name = "Sir Hammerlock";
		imagePath = "heros/hammerlock_1.png";
		cardImagePath = "hero_cards/card_sir_hammerlock.png";
		loadImage();
		effectDescription = "Großwildjagt (Zug):\n\nBis zu 3 beliebige Neutrale Einheiten werden getötet";
		componentsNeeded = Arrays.asList(ExecutionComponent.FIELD_TARGET, ExecutionComponent.SPINNER_NUMBER_PER_FIELD_NORMAL);
		executionType = ExecutionType.TURN_EFFECT;
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			ExecutionData data = new ExecutionData();
			data.setPossibleTargetFields(game.getBoard().getFields().stream().filter(neutralTroopField).collect(Collectors.toList()));
			data.setTargetFieldNormalTroopsModel(new SpinnerNumberModel(0, 0, 3, 1));
			return data;
		}
		else {
			if (!executionData.getTargetFields().isEmpty()) {
				if (executionData.getTargetFields().size() == 1) {
					Field selected = executionData.getTargetFields().get(0);
					int min = 0;
					int max = Math.max(0, Math.min(3, selected.getNormalTroops()));
					Optional<Integer> value = Optional.of(executionData.getTargetFieldsNormalTroops().get(selected));
					int val = Math.max(min, Math.min(max, value.orElse(0)));
					executionData.getTargetFieldsNormalTroops().put(selected, val);
					executionData.setTargetFieldNormalTroopsModel(new SpinnerNumberModel(val, min, max, 1));
				}
				else if (executionData.getTargetFields().size() <= 3) {
					int min = 0;
					int max = Math.min(1, executionData.getTargetFields().stream().mapToInt(field -> field.getNormalTroops()).min().orElse(0));
					executionData.getTargetFieldsNormalTroops().forEach((field, val) -> executionData.getTargetFieldsNormalTroops().put(field, max));
					executionData.setTargetFieldNormalTroopsModel(new SpinnerNumberModel(max, min, max, 1));
				}
				else {
					executionData.setTargetFieldNormalTroopsModel(new SpinnerNumberModel(0, 0, 0, 1));
					executionData.setTargetFieldsNormalTroops(new HashMap<Field, Integer>());
				}
			}
			return executionData;
		}
	}
	@Override
	public boolean execute(ExecutionData executionData) {
		Integer targetCount = executionData.getTargetFieldsNormalTroops().entrySet().stream().mapToInt(entry -> entry.getValue().intValue()).sum();
		if (targetCount <= 0) {
			new ErrorDialog("Du musst schon Ziele suchen, wenn Du willst das sie Abgeschossen werden.\n\n"
					+ "Das ist hier eine Großwildjagt und keine Moorhuhnjagt.").setVisible(true);
			return false;
		}
		if (targetCount > 3) {
			new ErrorDialog("Du kannst maximal 3 Ziele aussuchen.\n\n"
					+ "Es muss ja noch ein Bestand für die nächste Großwildjagt übrig bleiben.").setVisible(true);
			return false;
		}
		executionData.getTargetFieldsNormalTroops().forEach((field, value) -> field.removeNormalTroops(value));
		addHeroEffectExecutionToStatistics(executionData);
		game.getPlayerOrder().nextMove();
		game.getTurnExecutionManager().commit();
		game.getGameFrame().updateAllFrames();
		return true;
	}
}