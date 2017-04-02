package net.jfabricationgames.bunkers_and_badasses.game_character.troop;

public class TroopStorage {
	
	public static final int BASE_COSTS_CREDITS = 0;
	public static final int BASE_COSTS_AMMO = 1;
	public static final int RECRUIT_COSTS_CREDITS = 2;
	public static final int RECRUIT_COSTS_AMMO = 3;
	public static final int RECRUIT_COSTS_ERIDIUM = 4;
	
	public static final int NORMAL_TROOP = 1;
	public static final int BADASS_TROOP = 2;
	public static final int NEUTRAL_TROOP = 0;
	
	private int[][] troopCosts;
	
	public TroopStorage() {
		troopCosts = new int[3][5];
	}
	
	public int[][] getTroopCosts() {
		return troopCosts;
	}
}