package net.jfabricationgames.bunkers_and_badasses.game_command;

public class CollectCommand extends Command {
	
	private static final long serialVersionUID = 8884267415050473296L;
	
	public CollectCommand() {
		creditsNeeded = true;
		ammoNeeded = false;
		executable = true;
		image = imageLoader.loadImage("marker_mine.png");
	}
}