package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.swing.SpinnerNumberModel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class Modecai extends Hero {
	
	private static final long serialVersionUID = -3664578792584135642L;

	public Modecai() {
		attack = 4;
		defence = 2;
		name = "Modecai";
		imagePath = "heros/modecai_1.png";
		cardImagePath = "hero_cards/card_modecai.png";
		loadImage();
		effectDescription = "Sniper (Zug):\n\nBis zu 3 beliebige (normale) Einheiten dürfen irgentwo vom Feld genommen werden (auch mehrere Felder; die Felder dürfen dannach nicht leer sein)";
		componentsNeeded = Arrays.asList(ExecutionComponent.FIELD_TARGET, ExecutionComponent.SPINNER_NUMBER_PER_FIELD_NORMAL);
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			ExecutionData data = new ExecutionData();
			data.setPossibleTargetFields(game.getBoard().getFields().stream().filter(localPlayersField.negate()).
					filter(moreThanOneBandit).collect(Collectors.toList()));
			data.setTargetFieldNormalTroopsModel(new SpinnerNumberModel(0, 0, 3, 1));
			return data;
		}
		else {
			if (!executionData.getTargetFields().isEmpty()) {
				if (executionData.getTargetFields().size() == 1) {
					Field selected = executionData.getTargetFields().get(0);
					int min = 0;
					int max = Math.max(0, Math.min(3, selected.getNormalTroops()-1));
					Integer value = executionData.getTargetFieldsNormalTroops().get(selected);
					if (value == null) {
						value = new Integer(0);
					}
					int val = Math.max(min, Math.min(max, value.intValue()));
					executionData.getTargetFieldsNormalTroops().put(selected, val);
					executionData.setTargetFieldNormalTroopsModel(new SpinnerNumberModel(val, min, max, 1));
				}
				else if (executionData.getTargetFields().size() <= 3) {
					int min = 0;
					int max = Math.min(1, executionData.getTargetFields().stream().mapToInt(field -> field.getNormalTroops()-1).min().orElse(0));
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
		boolean anyFieldEmpty = executionData.getTargetFieldsNormalTroops().entrySet().stream().mapToInt(entry -> entry.getKey().getNormalTroops() + entry.getKey().getBadassTroops() - entry.getValue()).anyMatch(i -> i <= 0);
		if (targetCount <= 0) {
			new ErrorDialog("Du musst schon Ziele aussuchen, wenn Du willst das sie Abgeschossen werden.\n\nDu kannst auch dafür bezahlen, "
					+ "dass Modecai ein paar Moorhühner schießt... aber ob das dich wirklich weiter bringt...").setVisible(true);
			return false;
		}
		if (targetCount > 3) {
			new ErrorDialog("Du kannst maximal 3 Ziele aussuchen.\n\nSniper Munition wächst nunmal nicht an Bäumen.").setVisible(true);
			return false;
		}
		if (anyFieldEmpty) {
			new ErrorDialog("Du kannst nicht alle Truppen in einem Feld auslöschen.\n\nWer soll die Geschichte denn erzählen, wenn keiner überlebt?!").setVisible(true);
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