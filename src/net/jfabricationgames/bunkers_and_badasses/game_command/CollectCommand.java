package net.jfabricationgames.bunkers_and_badasses.game_command;

import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;

public class CollectCommand extends Command {
	
	private static final long serialVersionUID = 8884267415050473296L;
	
	public CollectCommand() {
		executable = true;
		removable = true;
		identifier = UserPlanManager.COMMAND_COLLECT;
		image = imageLoader.loadImage("marker_mine.png");
		loadVariables();
	}
	
	@Override
	public Command getInstance() {
		return new CollectCommand();
	}
}