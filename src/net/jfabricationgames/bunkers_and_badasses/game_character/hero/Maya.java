package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Maya extends Hero {
	
	private static final long serialVersionUID = -8029606369601412517L;

	public Maya() {
		attack = 4;
		defence = 3;
		name = "Maya";
		image = imageLoader.loadImage("maya_1.png");
		effectDescription = "Phaselock:\n\nEin Gegner wird in einem Ausgewählten Feld festgehalten und ist Bewegungsunfähig (Der Marker kann nicht eingesetzt werden und wird entfernt; Der Gegner kann sich aber verteidigen)";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//Phaselock: Gegner ist (in einem Ausgewählten Feld) Bewegungsunfähig (Marker kann nicht eingesetzt werden; Gegner kann sich aber verteidigen)
	}
}