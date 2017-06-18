package net.jfabricationgames.bunkers_and_badasses.game_command;

import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;

public class SupportCommand extends Command {
	
	private static final long serialVersionUID = -512872688838354972L;
	
	public SupportCommand() {
		executable = false;
		removable = false;
		support = true;
		identifier = UserPlanManager.COMMAND_SUPPORT;
		imagePath = "marker_support.png";
		loadImage();
		name = "Unterstützen";
		loadVariables();
	}
	
	@Override
	public Command getInstance() {
		return new SupportCommand();
	}
}