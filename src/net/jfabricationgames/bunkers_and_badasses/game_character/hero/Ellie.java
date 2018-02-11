package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class Ellie extends Hero {
	
	private static final long serialVersionUID = 8110478891996236498L;

	public Ellie() {
		attack = 2;
		defence = 1;
		name = "Ellie";
		imagePath = "heros/ellie_1.png";
		cardImagePath = "hero_cards/card_ellie.png";
		loadImage();
		effectDescription = "Klanfede (Zug):\n\nIn 2 aneinander grenzenden, gegnerischen Gebieten sterben jeweils bis zu 2 Einheiten (die Felder dürfen dannach nicht leer sein)";
		componentsNeeded = Arrays.asList(ExecutionComponent.FIELD_TARGET);
		executionType = ExecutionType.TURN_EFFECT;
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			ExecutionData data = new ExecutionData();
			data.setPossibleTargetFields(game.getBoard().getFields().stream().filter(localPlayersField.negate()).
					filter(moreThanTwoBandits).filter(nextToOtherEnemiesField).collect(Collectors.toList()));
			return data;
		}
		else {
			return executionData;
		}
	}
	@Override
	public boolean execute(ExecutionData executionData) {
		if (executionData.getTargetFields().size() <= 1) {
			new ErrorDialog("Du musst zwei gegnerische Felder auswählen, wenn die eine Klanfede beginnen sollen.\n\n"
					+ "Oder Du wartest bis sie sich selbst erschießen.\nDas geht meistens schneller als man denkt.").setVisible(true);
			return false;
		}
		if (executionData.getTargetFields().size() > 2) {
			new ErrorDialog("Du kannst nicht mehr als 2 gegnerische Felder auswählen.\n\n"
					+ "Das soll hier nur eine kleine Klanfede werden. Es ist doch schon Krieg.").setVisible(true);
			return false;
		}
		Field target1 = executionData.getTargetFields().get(0);
		Field target2 = executionData.getTargetFields().get(1);
		if (!target1.getNeighbours().contains(target2)) {
			new ErrorDialog("Die ausgewählten Felder müssen nebeneinander liegen, damit die eine Klanfede anfangen können.\n\n"
					+ "Fernbeziehungen zählen hier nichts.").setVisible(true);
			return false;
		}
		if (!Arrays.asList(target1, target2).stream().allMatch(moreThanTwoBandits)) {
			new ErrorDialog("Die ausgewählten Felder müssen beide mehr als zwei Banditen enthalten.\n\n"
					+ "Wie sollen die sonst ihre Klanfede fortführen, wenn keiner mehr übrig ist?!").setVisible(true);
			return false;
		}
		if (target1.getAffiliation() == null || target2.getAffiliation() == null) {
			new ErrorDialog("Du kannst keine Klanfede mit einem Leeren oder neutralen Feld anzetteln.").setVisible(true);
			return false;
		}
		if (target1.getAffiliation().equals(target2.getAffiliation())) {
			new ErrorDialog("Du kannst keine Klanfede zwischen zwei Feldern des selben Spielers anzetteln.").setVisible(true);
			return false;
		}
		target1.removeNormalTroops(2);
		target2.removeNormalTroops(2);
		addHeroEffectExecutionToStatistics(executionData);
		game.getPlayerOrder().nextMove();
		game.getTurnExecutionManager().commit();
		game.getGameFrame().updateAllFrames();
		return true;
	}
}