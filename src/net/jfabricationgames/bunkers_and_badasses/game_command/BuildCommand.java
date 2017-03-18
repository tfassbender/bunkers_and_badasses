package net.jfabricationgames.bunkers_and_badasses.game_command;

import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;

public class BuildCommand extends Command {
	
	private static final long serialVersionUID = 6254798933187602977L;
	
	public BuildCommand() {
		creditsNeeded = true;
		ammoNeeded = false;
		executable = true;
		removable = true;
		identifier = UserPlanManager.COMMAND_BUILD;
		image = imageLoader.loadImage("marker_build.png");
	}
}