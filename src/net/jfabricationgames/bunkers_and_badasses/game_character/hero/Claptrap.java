package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.SpinnerNumberModel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class Claptrap extends Hero {
	
	private static final long serialVersionUID = 3985651599282268746L;

	public Claptrap() {
		attack = 2;
		defence = 2;
		name = "Claptrap";
		imagePath = "heros/claptrap_1.png";
		cardImagePath = "hero_cards/card_claptrap.png";
		loadImage();
		effectDescription = "Downgrade (Zug):\n\nBis zu 3 gegnerische (Badass-) Truppen werden gedowngraded";
		componentsNeeded = Arrays.asList(ExecutionComponent.FIELD_TARGET, ExecutionComponent.SPINNER_NUMBER_PER_FIELD_BADASS);
		executionType = ExecutionType.TURN_EFFECT;
	}

	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			ExecutionData data = new ExecutionData();
			data.setPossibleTargetFields(game.getBoard().getFields().stream().filter(localPlayersField.negate()).
					filter(badassTroopsOnField).collect(Collectors.toList()));
			data.setTargetFieldBadassTroopsModel(new SpinnerNumberModel(0, 0, 3, 1));
			return data;
		}
		else {
			if (!executionData.getTargetFields().isEmpty()) {
				if (executionData.getTargetFields().size() == 1) {
					Field selected = executionData.getTargetFields().get(0);
					int min = 0;
					int max = Math.max(0, Math.min(3, selected.getBadassTroops()));
					Optional<Integer> value = Optional.of(executionData.getTargetFieldsBadassTroops().get(selected));
					int val = Math.max(min, Math.min(max, value.orElse(0)));
					executionData.getTargetFieldsBadassTroops().put(selected, val);
					executionData.setTargetFieldBadassTroopsModel(new SpinnerNumberModel(val, min, max, 1));
				}
				else if (executionData.getTargetFields().size() <= 3) {
					int min = 0;
					int max = Math.min(1, executionData.getTargetFields().stream().mapToInt(field -> field.getBadassTroops()).min().orElse(0));
					executionData.getTargetFieldsBadassTroops().forEach((field, val) -> executionData.getTargetFieldsBadassTroops().put(field, max));
					executionData.setTargetFieldBadassTroopsModel(new SpinnerNumberModel(max, min, max, 1));
				}
				else {
					executionData.setTargetFieldBadassTroopsModel(new SpinnerNumberModel(0, 0, 0, 1));
					executionData.setTargetFieldsBadassTroops(new HashMap<Field, Integer>());
				}
			}
			return executionData;
		}
	}
	@Override
	public boolean execute(ExecutionData executionData) {
		Integer targetCount = executionData.getTargetFieldsBadassTroops().entrySet().stream().mapToInt(entry -> entry.getValue().intValue()).sum();
		boolean notEnoughBadasses = executionData.getTargetFieldsBadassTroops().entrySet().stream().mapToInt(entry -> entry.getKey().getBadassTroops() - entry.getValue()).anyMatch(i -> i < 0);
		if (targetCount <= 0) {
			new ErrorDialog("Du musst schon Ziele aussuchen, wenn Du willst das sie Gedowngraded werden.\n\n"
					+ "Vorsicht bei der Auswahl. Wir wissen alle wie gut Claptrap ziehlt.").setVisible(true);
			return false;
		}
		if (targetCount > 3) {
			new ErrorDialog("Du kannst maximal 3 Ziele aussuchen.\n\n"
					+ "Die Kamerjäger.exe verbraucht sonst zu viel Energie für Claptrap.").setVisible(true);
			return false;
		}
		if (notEnoughBadasses) {
			new ErrorDialog("Du kannst nicht mehr Truppen Downgraden als in dem Feld vorhanden sind.\n\n"
					+ "Wozu willst du normale Truppen denn Downgraden? Claptraps?!").setVisible(true);
			return false;
		}
		executionData.getTargetFieldsBadassTroops().forEach((field, value) -> field.removeBadassTroops(value));
		executionData.getTargetFieldsBadassTroops().forEach((field, value) -> field.addNormalTroops(value));
		addHeroEffectExecutionToStatistics(executionData);
		game.getPlayerOrder().nextMove();
		game.getTurnExecutionManager().commit();
		game.getGameFrame().updateAllFrames();
		return true;
	}
}