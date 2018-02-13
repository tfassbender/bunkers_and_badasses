package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class Arschgaul extends Hero {
	
	private static final long serialVersionUID = 3449354613837860505L;

	public Arschgaul() {
		attack = 0;
		defence = 0;
		name = "Prinzessin Arschgaul";
		imagePath = "heros/arschgaul_2.png";
		cardImagePath = "hero_cards/card_prinzessin_arschgaul.png";
		loadImage();
		effectDescription = "Wunderschöne Anführerin (Zug):\n\nWähle ein Gebiet aus, dass in der gesammten Runde nicht angegriffen werden darf";
		componentsNeeded = Arrays.asList(ExecutionComponent.FIELD_TARGET);
		executionType = ExecutionType.TURN_EFFECT;
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			ExecutionData data = new ExecutionData();
			data.setPossibleTargetFields(game.getBoard().getFields().stream().filter(localPlayersField).collect(Collectors.toList()));
			return data;
		}
		else {
			return executionData;
		}
	}
	@Override
	public boolean execute(ExecutionData executionData) {
		if (executionData.getTargetFields().isEmpty()) {
			new ErrorDialog("Du solltest ein Ziel aussuchen wenn du willst das die Prinzessin einen Angriff dort verhindert.").setVisible(true);
			return false;
		}
		if (executionData.getTargetFields().size() > 1) {
			new ErrorDialog("Du kannst nur ein Feld aussuchen das die Prinzessin schützen soll.").setVisible(true);
			return false;
		}
		Field target = executionData.getTargetFields().get(0);
		if (target.getAffiliation() == null || !target.getAffiliation().equals(game.getLocalUser())) {
			new ErrorDialog("Die Prinzessin kann nur eines Deiner Felder vor einem Angriff schützen.");
			return false;
		}
		//TODO make the field untouchable for one turn
		return true;
	}
}