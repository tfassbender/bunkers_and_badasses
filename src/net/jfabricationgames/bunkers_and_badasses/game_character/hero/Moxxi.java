package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.SpinnerNumberModel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class Moxxi extends Hero {
	
	private static final long serialVersionUID = -4123023043047504302L;

	public Moxxi() {
		attack = 3;
		defence = 2;
		name = "Moxxi";
		imagePath = "heros/moxxi_1.png";
		cardImagePath = "hero_cards/card_mad_moxxi.png";
		loadImage();
		effectDescription = "Anwerbung (Zug):\n\n3 normale Truppen beliebig auf deine Felder verteilen";
		componentsNeeded = Arrays.asList(ExecutionComponent.FIELD_TARGET, ExecutionComponent.SPINNER_NUMBER_PER_FIELD_NORMAL);
		executionType = ExecutionType.TURN_EFFECT;
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			ExecutionData data = new ExecutionData();
			data.setPossibleTargetFields(game.getBoard().getFields().stream().filter(localPlayersField).collect(Collectors.toList()));
			data.setTargetFieldNormalTroopsModel(new SpinnerNumberModel(0, 0, 3, 1));
			return data;
		}
		else {
			if (!executionData.getTargetFields().isEmpty()) {
				if (executionData.getTargetFields().size() == 1) {
					Field selected = executionData.getTargetFields().get(0);
					int min = 0;
					int max = 3;
					Optional<Integer> value = Optional.ofNullable(executionData.getTargetFieldsNormalTroops().get(selected));
					int val = Math.max(min, Math.min(max, value.orElse(0)));
					executionData.getTargetFieldsNormalTroops().put(selected, val);
					executionData.setTargetFieldNormalTroopsModel(new SpinnerNumberModel(val, min, max, 1));
				}
				else if (executionData.getTargetFields().size() <= 3) {
					int min = 0;
					int max = 1;
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
			new ErrorDialog("Du solltest Felder und Truppen aussuchen, wenn du willst das da Truppen rekrutiert werden.").setVisible(true);
			return false;
		}
		if (targetCount > 3) {
			new ErrorDialog("Du kannst maximal 3 Truppen rekrutieren.\n\nDiese Selbstmordkandidaten wachsen nunmal nicht an Bäumen.").setVisible(true);
			return false;
		}
		executionData.getTargetFieldsNormalTroops().forEach((field, value) -> field.addNormalTroops(value));
		return true;
	}
}