package net.jfabricationgames.bunkers_and_badasses.game_command;

public class MarchCommand extends Command {
	
	private static final long serialVersionUID = 2510796564904838924L;
	
	public MarchCommand() {
		ammoNeeded = true;
		creditsNeeded = true;
		executable = true;
		image = imageLoader.loadImage("marker_march.png");
	}
}