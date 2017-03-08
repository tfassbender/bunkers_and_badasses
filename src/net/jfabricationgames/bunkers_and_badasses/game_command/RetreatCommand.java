package net.jfabricationgames.bunkers_and_badasses.game_command;

public class RetreatCommand extends Command {
	
	private static final long serialVersionUID = 6483463566186245809L;
	
	public RetreatCommand() {
		creditsNeeded = true;
		ammoNeeded = true;
		executable = false;
		image = imageLoader.loadImage("marker_retreat.png");
	}
}