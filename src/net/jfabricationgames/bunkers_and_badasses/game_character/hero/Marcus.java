package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.jfabricationgames.bunkers_and_badasses.game_command.Command;
import net.jfabricationgames.bunkers_and_badasses.game_command.SupportCommand;
import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class Marcus extends Hero {
	
	private static final long serialVersionUID = -2964770085479731401L;

	public Marcus() {
		attack = 1;
		defence = 2;
		name = "Marcus";
		imagePath = "heros/marcus_1.png";
		cardImagePath = "hero_cards/card_marcus.png";
		loadImage();
		effectDescription = "Nachladen:\n\nDu darfst (gratis) einen Unterstützungsbefehl auf eines deiner Felder (ohne Befehl) platzieren";
		componentsNeeded = Arrays.asList(ExecutionComponent.FIELD_START, ExecutionComponent.SELECTION_COMMAND);
		executionType = ExecutionType.TURN_EFFECT;
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			ExecutionData data = new ExecutionData();
			Command command = new SupportCommand();
			data.setPossibleCommands(Arrays.asList(command));
			data.setPossibleStartFields(game.getBoard().getFields().stream().filter(localPlayersField).
					filter(hasCommand.negate()).collect(Collectors.toList()));
			data.setCommand(command);
			return data;
		}
		else {
			return executionData;
		}
	}
	@Override
	public boolean execute(ExecutionData executionData) {
		if (executionData.getStartField() == null) {
			new ErrorDialog("Du solltest ein Feld auswählen in dem der Unterstützungsbefehl erteilt werden soll.\n\n"
					+ "Schnell, bevor Marcus merkt, dass er etwas gratis tut!").setVisible(true);
			return false;
		}
		if (executionData.getCommand() == null) {
			//the support command is the only command possible
			executionData.setCommand(new SupportCommand());
		}
		executionData.getStartField().setCommand(executionData.getCommand());
		addHeroEffectExecutionToStatistics(executionData);
		game.getPlayerOrder().nextMove();
		game.getTurnExecutionManager().commit();
		game.getGameFrame().updateAllFrames();
		return true;
	}
}