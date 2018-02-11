package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.SpinnerNumberModel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class MrTorgue extends Hero {
	
	private static final long serialVersionUID = 2488254569700119667L;

	public MrTorgue() {
		attack = 3;
		defence = 3;
		name = "Mr. Trogue";
		imagePath = "heros/mr_torgue_1.png";
		cardImagePath = "hero_cards/card_mr_torgue.png";
		loadImage();
		effectDescription = "EXPLOSIONSGERÄUSCH (Zug):\n\nBis zu 3 beliebige normale Truppen werden zu BADASSES aufgerüstet";
		componentsNeeded = Arrays.asList(ExecutionComponent.FIELD_TARGET, ExecutionComponent.SPINNER_NUMBER_PER_FIELD_NORMAL);
		executionType = ExecutionType.TURN_EFFECT;
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			ExecutionData data = new ExecutionData();
			data.setPossibleTargetFields(game.getBoard().getFields().stream().filter(localPlayersField).
					filter(normalTroopsOnField).collect(Collectors.toList()));
			data.setTargetFieldNormalTroopsModel(new SpinnerNumberModel(0, 0, 3, 1));
			return data;
		}
		else {
			if (!executionData.getTargetFields().isEmpty()) {
				if (executionData.getTargetFields().size() == 1) {
					Field selected = executionData.getTargetFields().get(0);
					int min = 0;
					int max = selected.getNormalTroops();
					Optional<Integer> value = Optional.of(executionData.getTargetFieldsNormalTroops().get(selected));
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
		boolean notEnoughBandits = executionData.getTargetFieldsNormalTroops().entrySet().stream().
				mapToInt(entry -> entry.getKey().getNormalTroops() - entry.getValue()).anyMatch(i -> i < 0);
		if (targetCount <= 0) {
			new ErrorDialog("Du solltest Felder und Truppen aussuchen, wenn du willst das da Truppen aufgerüstet werden.").setVisible(true);
			return false;
		}
		if (targetCount > 3) {
			new ErrorDialog("Du kannst maximal 3 Truppen zu Badasses aufrüsten.\n\n"
					+ "Diese Selbstmordkandidaten wachsen nunmal nicht an Bäumen.").setVisible(true);
			return false;
		}
		if (notEnoughBandits) {
			new ErrorDialog("Du kannst nicht mehr Truppen in einem Feld aufrüsten als in dem Feld vorhanden sind.\n\n"
					+ "Diese Selbstmordkandidaten wachsen nunmal nicht an Bäumen.").setVisible(true);
			return false;
		}
		executionData.getTargetFieldsNormalTroops().forEach((field, value) -> field.removeNormalTroops(value));
		executionData.getTargetFieldsNormalTroops().forEach((field, value) -> field.addBadassTroops(value));
		addHeroEffectExecutionToStatistics(executionData);
		game.getPlayerOrder().nextMove();
		game.getTurnExecutionManager().commit();
		game.getGameFrame().updateAllFrames();
		return true;
	}
}