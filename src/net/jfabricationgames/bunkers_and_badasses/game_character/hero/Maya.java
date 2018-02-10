package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class Maya extends Hero {
	
	private static final long serialVersionUID = -8029606369601412517L;

	public Maya() {
		attack = 4;
		defence = 3;
		name = "Maya";
		imagePath = "heros/maya_1.png";
		cardImagePath = "hero_cards/card_maya.png";
		loadImage();
		effectDescription = "Phaselock (Zug):\n\nEin Gegner wird in einem seiner Felder Festgehalten (Der Befehlsmarker wird entfernt; Der Gegner kann sich aber verteidigen)";
		componentsNeeded = Arrays.asList(ExecutionComponent.FIELD_TARGET);
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			executionData = new ExecutionData();
		}
		//the possible targets are always all enemy fields with commands
		executionData.setPossibleTargetFields(game.getBoard().getFields().stream().filter(localPlayersField.negate()).filter(hasCommand).collect(Collectors.toList()));
		return executionData;
	}
	@Override
	public boolean execute(ExecutionData executionData) {
		if (executionData.getTargetFields().isEmpty()) {
			new ErrorDialog("Du musst schon ein Feld aussuchen in dem Du den Gegner festsetzen willst.").setVisible(true);
			return false;
		}
		if (executionData.getTargetFields().size() > 1) {
			new ErrorDialog("Du kannst den Gegner nur in einem Feld festhalten.").setVisible(true);
			return false;
		}
		executionData.getTargetFields().get(0).setCommand(null);
		addHeroEffectExecutionToStatistics(executionData);
		game.getPlayerOrder().nextMove();
		game.getTurnExecutionManager().commit();
		game.getGameFrame().updateAllFrames();
		return true;
	}
}