package net.jfabricationgames.bunkers_and_badasses.game_command;

import net.jfabricationgames.bunkers_and_badasses.game_character.building.ArschgaulsPalace;

public class DefendCommand extends Command {
	
	private static final long serialVersionUID = -6699207826006728668L;
	
	public DefendCommand() {
		creditsNeeded = true;
		ammoNeeded = true;
		executable = false;
		executionBuildings.add(ArschgaulsPalace.class);
		image = imageLoader.loadImage("marker_defend.png");
	}
}