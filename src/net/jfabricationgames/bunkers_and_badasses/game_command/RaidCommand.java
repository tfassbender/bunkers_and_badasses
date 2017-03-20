package net.jfabricationgames.bunkers_and_badasses.game_command;

import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;

public class RaidCommand extends Command {
	
	private static final long serialVersionUID = 5963435414801627767L;
	
	public RaidCommand() {
		ammoNeeded = true;
		creditsNeeded = true;
		executable = true;
		removable = true;
		identifier = UserPlanManager.COMMAND_RAID;
		image = imageLoader.loadImage("marker_raid.png");
	}
	
	@Override
	public Command getInstance() {
		return new RaidCommand();
	}
}