package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Maya extends Hero {
	
	private static final long serialVersionUID = -8029606369601412517L;

	public Maya() {
		attack = 4;
		defence = 3;
		name = "Maya";
		image = imageLoader.loadImage("maya_1.png");
		effectDescription = "Phaselock:\n\nEin Gegner wird in einem Ausgew�hlten Feld festgehalten und ist Bewegungsunf�hig (Der Marker kann nicht eingesetzt werden und wird entfernt; Der Gegner kann sich aber verteidigen)";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Phaselock: Gegner ist (in einem Ausgew�hlten Feld) Bewegungsunf�hig (Marker kann nicht eingesetzt werden; Gegner kann sich aber verteidigen)
	}
}