package net.jfabricationgames.bunkers_and_badasses.game_command;

import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ArschgaulsPalace;

public class DefendCommand extends Command {
	
	private static final long serialVersionUID = -6699207826006728668L;
	
	public DefendCommand() {
		executable = false;
		removable = false;
		identifier = UserPlanManager.COMMAND_DEFEND;
		executionBuildings.add(ArschgaulsPalace.class);
		image = imageLoader.loadImage("marker_defend.png");
		name = "Verteidigen";
		loadVariables();
	}
	
	@Override
	public Command getInstance() {
		return new DefendCommand();
	}
}