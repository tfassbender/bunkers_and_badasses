package net.jfabricationgames.bunkers_and_badasses.game_command;

import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ArschgaulsPalace;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.MoxxisTavern;

public class RecruitCommand extends Command {
	
	private static final long serialVersionUID = -5452757372655089485L;
	
	public RecruitCommand() {
		executable = true;
		removable = true;
		identifier = UserPlanManager.COMMAND_RECRUIT;
		executionBuildings.add(ArschgaulsPalace.class);
		executionBuildings.add(MoxxisTavern.class);
		image = imageLoader.loadImage("marker_recruit.png");
		loadVariables();
	}
	
	@Override
	public Command getInstance() {
		return new RecruitCommand();
	}
}