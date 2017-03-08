package net.jfabricationgames.bunkers_and_badasses.game_command;

public class SupportCommand extends Command {
	
	private static final long serialVersionUID = -512872688838354972L;
	
	public SupportCommand() {
		creditsNeeded = true;
		ammoNeeded = true;
		executable = false;
		image = imageLoader.loadImage("marker_support.png");
	}
}