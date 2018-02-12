package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.SpinnerNumberModel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class Springs extends Hero {
	
	private static final long serialVersionUID = -5937437808209595383L;

	public Springs() {
		attack = 1;
		defence = 2;
		name = "Springs";
		imagePath = "heros/springs_1.png";
		cardImagePath = "hero_cards/card_springs.png";
		loadImage();
		effectDescription = "Raketentechnickerin (Zug):\n\nBeliebig viele Einheiten aus einem Gebiet können in andere schon kontrollierte gebiete verschoben werden";
		executionType = ExecutionType.TURN_EFFECT;
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			//send the start execution data with only the possible start fields
			ExecutionData data = new ExecutionData();
			data.setPossibleStartFields(game.getBoard().getFields().stream().filter(localPlayersField).filter(fieldEmpty.negate()).collect(Collectors.toList()));
			data.setPossibleTargetFields(game.getBoard().getFields().stream().filter(localPlayersField).collect(Collectors.toList()));
			return data;
		}
		else {
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
					Optional<Integer> valueNormal = Optional.of(executionData.getTargetFieldsNormalTroops().get(selectedField));
					Optional<Integer> valueBadass = Optional.of(executionData.getTargetFieldsBadassTroops().get(selectedField));
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
		executionData.getTargetFieldsNormalTroops().forEach((target, val) -> game.getBoard().moveTroops(executionData.getStartField(), target, val.intValue(), 0));
		executionData.getTargetFieldsBadassTroops().forEach((target, val) -> game.getBoard().moveTroops(executionData.getStartField(), target, 0, val.intValue()));
		addHeroEffectExecutionToStatistics(executionData);
		game.getPlayerOrder().nextMove();
		game.getTurnExecutionManager().commit();
		game.getGameFrame().updateAllFrames();
		return true;
	}
}