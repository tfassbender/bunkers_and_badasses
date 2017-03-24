package net.jfabricationgames.bunkers_and_badasses.game_command;

public class CommandStorage {
	
	public static final int CREDITS = 0;
	public static final int AMMO = 1;
	
	private int[][] costs;
	private boolean[][] dependencies;
	
	public CommandStorage() {
		costs = new int[8][2];
		dependencies = new boolean[8][2];
	}
	
	public int[][] getCosts() {
		return costs;
	}
	public void setCosts(int[][] costs) {
		this.costs = costs;
	}
	
	public boolean[][] getDependencies() {
		return dependencies;
	}
	public void setDependencies(boolean[][] dependencies) {
		this.dependencies = dependencies;
	}
}