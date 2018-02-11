package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.jfabricationgames.bunkers_and_badasses.game_command.BuildCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.CollectCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.DefendCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.MarchCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.RaidCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.RecruitCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.RetreatCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.SupportCommand;
import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class Zero extends Hero {
	
	private static final long serialVersionUID = 8601494966049836609L;
	
	public Zero() {
		attack = 3;
		defence = 4;
		name = "Zero";
		imagePath = "heros/zero_1.png";
		cardImagePath = "hero_cards/card_zero.png";
		loadImage();
		effectDescription = "Doppelgänger (Zug):\n\nEin beliebiger Befehlsmarker kann nachträglich (kostenlos, beliebig) verändert werden";
		componentsNeeded = Arrays.asList(ExecutionComponent.FIELD_START, ExecutionComponent.SELECTION_COMMAND);
		executionType = ExecutionType.TURN_EFFECT;
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			ExecutionData data = new ExecutionData();
			data.setPossibleCommands(Arrays.asList(new BuildCommand(), new CollectCommand(), new DefendCommand(), new MarchCommand(), 
					new RaidCommand(), new RecruitCommand(), new RetreatCommand(), new SupportCommand()));
			data.setPossibleStartFields(game.getBoard().getFields().stream().filter(localPlayersField).filter(hasCommand).collect(Collectors.toList()));
			return data;
		}
		else {
			return executionData;
		}
	}
	@Override
	public boolean execute(ExecutionData executionData) {
		if (executionData.getStartField() == null) {
			new ErrorDialog("Du solltest ein Feld auswählen in dem der Befehlsmarker verändert werden soll.").setVisible(true);
			return false;
		}
		if (executionData.getCommand() == null) {
			new ErrorDialog("Du solltest einen Befehl auswählen der anstatt dem aktuellen Befehl auf dem Feld platziert werden soll.\n\n"
					+ "Oder Du tauschst den Befehl gegen sich selbst aus.\nDas schlägt auch die Zeit tot.").setVisible(true);
			return false;
		}
		executionData.getStartField().setCommand(executionData.getCommand());
		addHeroEffectExecutionToStatistics(executionData);
		game.getPlayerOrder().nextMove();
		game.getTurnExecutionManager().commit();
		game.getGameFrame().updateAllFrames();
		return true;
	}
}