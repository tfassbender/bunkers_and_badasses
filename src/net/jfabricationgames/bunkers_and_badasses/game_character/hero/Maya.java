package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Maya extends Hero {
	
	private static final long serialVersionUID = -8029606369601412517L;

	static {
		staticImage = imageLoader.loadImage("maya_1.png");
	}
	
	public Maya() {
		attack = 4;
		defence = 3;
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Phaselock: Gegner ist (in einem Ausgewählten Feld) Bewegungsunfähig (Marker kann nicht eingesetzt werden; Gegner kann sich aber verteidigen)
	}
}