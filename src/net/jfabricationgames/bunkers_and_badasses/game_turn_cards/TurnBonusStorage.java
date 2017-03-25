package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnBonusStorage {
	
	public static final int CREDITS = 0;
	public static final int AMMO = 1;
	public static final int ERIDIUM = 2;
	
	private int[][] resources;
	
	public TurnBonusStorage() {
		resources = new int[13][3];
	}
	
	public int[][] getResources() {
		return resources;
	}
}