package net.jfabricationgames.bunkers_and_badasses.game_command;

import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;

/**
 * The resources that can be collected are stored in the command storage (int[] getResourceReception())
 */
public class CollectCommand extends Command {
	
	private static final long serialVersionUID = 8884267415050473296L;
	
	public CollectCommand() {
		executable = true;
		removable = true;
		identifier = UserPlanManager.COMMAND_COLLECT;
		imagePath = "marker_mine.png";
		loadImage();
		name = "Resourcen";
		loadVariables();
	}
	
	@Override
	public Command getInstance() {
		return new CollectCommand();
	}
}