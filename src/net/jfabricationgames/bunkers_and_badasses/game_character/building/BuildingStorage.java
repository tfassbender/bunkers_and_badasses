package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class BuildingStorage {
	
	public static final int RECRUITABLE_TROOPS = 0;
	public static final int MINING_CREDITS = 1;
	public static final int MINING_AMMO = 2;
	public static final int MINING_ERIDIUM = 3;
	public static final int LAND_MINE_VICTIMS = 4;
	public static final int ADDITIONAL_DEFENCE = 5;
	public static final int MOVE_DISTANCE = 6;
	public static final int POINTS = 7;
	
	//store the variables (loaded from the database) by the building id and the variable id
	private int[][] buildingVariables;
	private int[][] buildingPrices;
	private int[][] buildingExtensionPrices;
	
	public BuildingStorage() {
		buildingVariables = new int[17][8];
		buildingPrices = new int[17][3];
		buildingExtensionPrices = new int[17][3];
	}
	
	public int[][] getBuildingVariables() {
		return buildingVariables;
	}
	public int[][] getBuildingPrices() {
		return buildingPrices;
	}
	public int[][] getBuildingExtensionPrices() {
		return buildingExtensionPrices;
	}
}