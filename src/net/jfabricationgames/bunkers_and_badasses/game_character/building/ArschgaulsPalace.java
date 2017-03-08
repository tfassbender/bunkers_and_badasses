package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class ArschgaulsPalace extends Building {
	
	public ArschgaulsPalace() {
		super();
		buildingId = 0;
		recruitableTroops = 3;
		//TODO change to real values when known
		ammoMining = 1;
		creditMining = 1;
		eridiumMining = 1;
		points = 1;
		name = "Arschgaul's Palast";
		//Unsure if attackable
		image = imageLoader.loadImage("arschgaul_1_small.png");
		loadVariables();
	}
}