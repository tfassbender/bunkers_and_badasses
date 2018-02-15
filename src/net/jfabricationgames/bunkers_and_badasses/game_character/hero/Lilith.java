package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.swing.SpinnerNumberModel;

import net.jfabricationgames.bunkers_and_badasses.error.GameLockException;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ArschgaulsPalace;
import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class Lilith extends Hero {
	
	private static final long serialVersionUID = -8941435166298469244L;

	public Lilith() {
		attack = 4;
		defence = 4;
		name = "Lilith";
		imagePath = "heros/lilith_1.png";
		cardImagePath = "hero_cards/card_lilith.png";
		loadImage();
		effectDescription = "Phasewalk (Zug):\n\nEine kleine Infiltrationseinheit (Kampfstärke von bis zu 5) darf ein Feld Überspringen "
				+ "um hinter der Feindlichen Linie anzugreifen (Außer Arschgauls Palast)";
		componentsNeeded = Arrays.asList(ExecutionComponent.FIELD_START, ExecutionComponent.FIELD_TARGET, 
				ExecutionComponent.SPINNER_NUMBER_NORMAL, ExecutionComponent.SPINNER_NUMBER_BADASS);
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
				//start field is chosen -> calculate the possible target fields
				executionData.setPossibleTargetFields(executionData.getStartField().getNeighbours().parallelStream().
						flatMap(f2 -> f2.getNeighbours().parallelStream()).distinct().filter(enemyPlayersField).
						filter(hasArschgaulsPalace.negate()).collect(Collectors.toList()));
				//remove all selected target fields that are not possible anymore
				executionData.setTargetFields(executionData.getTargetFields().stream().
						filter(field -> executionData.getPossibleTargetFields().contains(field)).collect(Collectors.toList()));
				//change the spinner models for the starting field
				int min = 0;
				int maxNormal = 5;
				int maxBadass = 2;
				int valNormal = Math.max(min, Math.min(executionData.getStartFieldNormalTroops(), maxNormal));
				int valBadass = Math.max(min, Math.min(executionData.getStartFieldBadassTroops(), maxBadass));
				executionData.setStartFieldNormalTroops(valNormal);
				executionData.setStartFieldBadassTroops(valBadass);
				executionData.setStartFieldNormalTroopsModel(new SpinnerNumberModel(valNormal, min, maxNormal, 1));
				executionData.setStartFieldBadassTroopsModel(new SpinnerNumberModel(valBadass, min, maxBadass, 1));
			}
			return executionData;			
		}
	}
	@Override
	public boolean execute(ExecutionData executionData) {
		if (executionData.getStartField() == null) {
			new ErrorDialog("Du musst schon ein Feld aussuchen aus dem Deine Truppen sich Bewegen sollen.\n\n"
					+ "Das ist keine freiwillige Aktion hier.").setVisible(true);
			return false;
		}
		if (executionData.getTargetFields().isEmpty()) {
			new ErrorDialog("Du solltest ein Ziel aussuchen wenn du willst das deine Truppen sich bewegen.\n\n"
					+ "Oder Du lässt sie einfach im Kreis rennen. Das kann auch lustig sein.\n"
					+ "Nicht unbedingt zielführend... aber lustig.").setVisible(true);
			return false;
		}
		if (executionData.getTargetFields().size() > 1) {
			new ErrorDialog("Du kannst nicht in einem Zug zwei Gegner angreifen.\n\n"
					+ "Lass den armen Schweinen doch auch mal ne Chance.").setVisible(true);
			return false;
		}
		if (executionData.getStartFieldNormalTroops() + 2* executionData.getStartFieldBadassTroops() > 5) {
			new ErrorDialog("Du kannst in diesem Angriff maximal Truppen der Stärke 5 mitnehmen.\n\n"
					+ "Schwertransport-Phasen ist nicht im Budget.").setVisible(true);
			return false;
		}
		if (executionData.getTargetFields().get(0).getBuilding() instanceof ArschgaulsPalace) {
			new ErrorDialog("Du kannst nicht Arschgauls Palast angreifen.\n\n"
					+ "Was soll der Scheiß?!\nDu erschreckst noch die Prinzessin!").setVisible(true);
			return false;
		}
		if (executionData.getStartFieldNormalTroops() + 2* executionData.getStartFieldBadassTroops() <= 0) {
			new ErrorDialog("Du solltest auch Truppen aussuchen wenn du willst dass die sich bewegen.\n\n"
					+ "Das ist keine freiwillige Aktion hier.").setVisible(true);
			return false;
		}
		Field start = executionData.getStartField();
		Field target = executionData.getTargetFields().get(0);
		int normalTroops = executionData.getStartFieldNormalTroops();
		int badassTroops = executionData.getStartFieldBadassTroops();
		if (target.getTroops().isEmpty()) {
			//no enemy troops but an enemy field -> just conquer the field
			game.getBoard().moveTroops(start, target, normalTroops, badassTroops);
			//only do this in the implementation of Lilith (because it's not executed when she starts a fight)
			game.getHeroCardManager().heroCardUsed(this, game.getLocalUser());
			addHeroEffectExecutionToStatistics(executionData);
			game.getPlayerOrder().nextMove();
			game.getTurnExecutionManager().commit();
			return true;
		}
		else {
			try {
				game.getFightManager().startFight(start, target, normalTroops, badassTroops);
				//choose the retreat field (as the start field) but keep to boolean to not chosen so it only works when the player selects no other retreat field
				game.getFightManager().getCurrentFight().setRetreatField(start);
				return true;
			}
			catch (GameLockException gle) {
				new ErrorDialog(gle.getErrorText()).setVisible(true);
				return false;
			}
		}
	}
}