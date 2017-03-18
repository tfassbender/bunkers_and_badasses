package net.jfabricationgames.bunkers_and_badasses.game_command;

import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;

public class MarchCommand extends Command {
	
	private static final long serialVersionUID = 2510796564904838924L;
	
	public MarchCommand() {
		ammoNeeded = true;
		creditsNeeded = true;
		executable = true;
		removable = false;
		identifier = UserPlanManager.COMMAND_MARCH;
		image = imageLoader.loadImage("marker_march.png");
	}
}