package net.jfabricationgames.bunkers_and_badasses.game_command;

import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;

public class RetreatCommand extends Command {
	
	private static final long serialVersionUID = 6483463566186245809L;
	
	public RetreatCommand() {
		executable = false;
		removable = true;
		identifier = UserPlanManager.COMMAND_RETREAT;
		image = imageLoader.loadImage("marker_retreat.png");
		loadVariables();
	}
	
	@Override
	public Command getInstance() {
		return new RetreatCommand();
	}
}