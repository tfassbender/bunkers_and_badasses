package net.jfabricationgames.bunkers_and_badasses.game_command;

import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ArschgaulsPalace;

public class BuildCommand extends Command {
	
	private static final long serialVersionUID = 6254798933187602977L;
	
	public BuildCommand() {
		executable = true;
		removable = true;
		identifier = UserPlanManager.COMMAND_BUILD;
		imagePath = "marker_build.png";
		loadImage();
		name = "Aufbauen";
		loadVariables();
	}
	
	@Override
	public Command getInstance() {
		return new BuildCommand();
	}
	
	/**
	 * Check if the command could be executed on a field. The resources needed are not checked.
	 * 
	 * Also checks that there is no build command on base fields (they won't work anyway).
	 */
	public boolean isExecutableOnField(Field field) {
		if (field.getBuilding() != null && field.getBuilding() instanceof ArschgaulsPalace) {
			return false;
		}
		else {
			return super.isExecutableOnField(field);
		}
	}
}