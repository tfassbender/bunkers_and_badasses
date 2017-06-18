package net.jfabricationgames.bunkers_and_badasses.game_command;

import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;

public class RaidCommand extends Command {
	
	private static final long serialVersionUID = 5963435414801627767L;
	
	public RaidCommand() {
		executable = true;
		removable = true;
		identifier = UserPlanManager.COMMAND_RAID;
		imagePath = "marker_raid.png";
		loadImage();
		name = "Überfallen";
		loadVariables();
	}
	
	@Override
	public Command getInstance() {
		return new RaidCommand();
	}
}