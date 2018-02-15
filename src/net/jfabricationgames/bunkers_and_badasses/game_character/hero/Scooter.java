package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.SpinnerNumberModel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class Scooter extends Hero {
	
	private static final long serialVersionUID = -6292099839196271278L;
	
	public Scooter() {
		attack = 1;
		defence = 1;
		name = "Scooter";
		imagePath = "heros/scooter_3.png";
		cardImagePath = "hero_cards/card_scooter.png";
		loadImage();
		effectDescription = "Catch-A-Ride (Zug):\n\nDeine Truppen dürfen bis zu 3 Felder weit vorrücken und die Feindliche Linie durchbrechen "
				+ "(wenn sie ein eigenes oder leeres Feld erreichen)";
		componentsNeeded = Arrays.asList(ExecutionComponent.FIELD_START, ExecutionComponent.FIELD_TARGET, 
				ExecutionComponent.SPINNER_NUMBER_PER_FIELD_NORMAL, ExecutionComponent.SPINNER_NUMBER_PER_FIELD_BADASS);
		executionType = ExecutionType.TURN_EFFECT;
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			//send the start execution data with only the possible start fields
			ExecutionData data = new ExecutionData();
			data.setPossibleStartFields(game.getBoard().getFields().stream().filter(localPlayersField).
					filter(fieldEmpty.negate()).collect(Collectors.toList()));
			return data;
		}
		else {
			if (executionData.getStartField() != null) {
				executionData.setPossibleTargetFields(executionData.getStartField().getNeighbours().parallelStream().
						flatMap(f2 -> f2.getNeighbours().parallelStream()).flatMap(f3 -> f3.getNeighbours().parallelStream()).distinct().
						filter(localPlayersField.or(fieldEmpty).and(enemyPlayersField.negate())).collect(Collectors.toList()));
				//remove all selected target fields that are not possible anymore
				executionData.setTargetFields(executionData.getTargetFields().stream().
						filter(field -> executionData.getPossibleTargetFields().contains(field)).collect(Collectors.toList()));
				executionData.getTargetFieldsNormalTroops().entrySet().
				removeIf(entry -> !executionData.getPossibleTargetFields().contains(entry.getKey()));
				executionData.getTargetFieldsBadassTroops().entrySet().
				removeIf(entry -> !executionData.getPossibleTargetFields().contains(entry.getKey()));
			}
			if (!executionData.getTargetFields().isEmpty()) {
				if (executionData.getTargetFields().size() > 1) {
					//limit the selection to 1 (only one selection at a time, multiple fields at the end)
					executionData.setTargetFields(executionData.getTargetFields().stream().limit(1).collect(Collectors.toList()));
					return executionData;
				}
				else {
					Field selectedField = executionData.getTargetFields().get(0);
					int min = 0;
					int maxNormal = executionData.getStartField().getNormalTroops();
					int maxBadass = executionData.getStartField().getBadassTroops();
					Optional<Integer> valueNormal = Optional.ofNullable(executionData.getTargetFieldsNormalTroops().get(selectedField));
					Optional<Integer> valueBadass = Optional.ofNullable(executionData.getTargetFieldsBadassTroops().get(selectedField));
					int valNormal = valueNormal.orElse(0);
					int valBadass = valueBadass.orElse(0);
					executionData.setTargetFieldNormalTroopsModel(new SpinnerNumberModel(valNormal, min, maxNormal, 1));
					executionData.setTargetFieldBadassTroopsModel(new SpinnerNumberModel(valBadass, min, maxBadass, 1));
				}
			}
			return executionData;
		}
	}
	@Override
	public boolean execute(ExecutionData executionData) {
		int totalNormalTroopsMoved = executionData.getTargetFieldsNormalTroops().entrySet().stream().mapToInt(entry -> entry.getValue().intValue()).sum();
		int totalBadassTroopsMoved = executionData.getTargetFieldsBadassTroops().entrySet().stream().mapToInt(entry -> entry.getValue().intValue()).sum();
		List<Field> possibleTargets = executionData.getStartField().getNeighbours().parallelStream().
				flatMap(f2 -> f2.getNeighbours().parallelStream()).flatMap(f3 -> f3.getNeighbours().parallelStream()).distinct().
				filter(localPlayersField.or(fieldEmpty).and(enemyPlayersField.negate())).collect(Collectors.toList());
		boolean unreachableFields = Stream.concat(executionData.getTargetFieldsNormalTroops().entrySet().stream(), executionData.getTargetFieldsBadassTroops().entrySet().stream()).
				anyMatch(entry -> !possibleTargets.contains(entry.getKey()));
		if (totalNormalTroopsMoved + totalBadassTroopsMoved <= 0) {
			new ErrorDialog("Du solltest auch Truppen aussuchen wenn du willst dass die sich bewegen.\n\n"
					+ "Das ist keine freiwillige Aktion hier.").setVisible(true);
			return false;
		}
		if (totalNormalTroopsMoved > executionData.getStartField().getNormalTroops() || totalBadassTroopsMoved > executionData.getStartField().getBadassTroops()) {
			new ErrorDialog("Du kannst nicht mehr Truppen bewegen als in dem Feld vorhanden sind.\n\n"
					+ "Diese Selbstrmordkandidaten wachsen nunmal nicht an Bäumen.").setVisible(true);
			return false;
		}
		if (unreachableFields) {
			new ErrorDialog("Du kannst mindestens eins der Ausgewählten Felder nicht erreichen.").setVisible(true);
			return false;
		}
		executionData.getTargetFieldsNormalTroops().forEach((target, val) -> game.getBoard().moveTroops(executionData.getStartField(), target, val.intValue(), 0));
		executionData.getTargetFieldsBadassTroops().forEach((target, val) -> game.getBoard().moveTroops(executionData.getStartField(), target, 0, val.intValue()));
		return true;
	}
}