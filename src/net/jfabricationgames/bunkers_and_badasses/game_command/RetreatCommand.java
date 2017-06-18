package net.jfabricationgames.bunkers_and_badasses.game_command;

import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;

public class RetreatCommand extends Command {
	
	private static final long serialVersionUID = 6483463566186245809L;
	
	public RetreatCommand() {
		executable = false;
		removable = true;
		identifier = UserPlanManager.COMMAND_RETREAT;
		imagePath = "marker_retreat.png";
		loadImage();
		name = "Zurückziehen";
		loadVariables();
	}
	
	@Override
	public Command getInstance() {
		return new RetreatCommand();
	}
}