package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class ArschgaulsPalace extends Building {
	
	private static final long serialVersionUID = -7387992314174725842L;

	public ArschgaulsPalace() {
		buildingId = 0;
		name = "Arschgaul's Palast";
		commandExecutable = true;
		imagePath = "arschgaul_1_small.png";
		loadVariables();
		loadImage();
	}
	
	@Override
	public Building newInstance() {
		return new ArschgaulsPalace();
	}
}