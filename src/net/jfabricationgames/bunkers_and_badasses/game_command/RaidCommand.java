package net.jfabricationgames.bunkers_and_badasses.game_command;

public class RaidCommand extends Command {
	
	private static final long serialVersionUID = 5963435414801627767L;
	
	public RaidCommand() {
		ammoNeeded = true;
		creditsNeeded = true;
		executable = true;
		image = imageLoader.loadImage("marker_raid.png");
	}
}