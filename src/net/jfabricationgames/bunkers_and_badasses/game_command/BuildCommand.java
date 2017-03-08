package net.jfabricationgames.bunkers_and_badasses.game_command;

public class BuildCommand extends Command {
	
	private static final long serialVersionUID = 6254798933187602977L;
	
	public BuildCommand() {
		creditsNeeded = true;
		ammoNeeded = false;
		executable = true;
		image = imageLoader.loadImage("marker_build.png");
	}
}