package net.jfabricationgames.bunkers_and_badasses.game_command;

public class CommandStorage {
	
	public static final int CREDITS = 0;
	public static final int AMMO = 1;
	public static final int ERIDIUM = 2;
	
	private int[][] costs;
	private boolean[][] dependencies;
	private int[] resourceReception;//resources you get from a collect command (id -1 in db)
	
	public CommandStorage() {
		costs = new int[8][2];
		dependencies = new boolean[8][2];
		resourceReception = new int[3];
	}
	
	public int[][] getCosts() {
		return costs;
	}
	public boolean[][] getDependencies() {
		return dependencies;
	}
	public int[] getResourceReception() {
		return resourceReception;
	}
}