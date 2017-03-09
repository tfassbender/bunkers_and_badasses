package net.jfabricationgames.bunkers_and_badasses.game;

public enum GameState {
	
	PLAN("Planungsphase"),
	ACT("Ausf�hrungsphase"),
	FIGHT("Kampf");
	
	private final String name;
	
	private GameState(String name) {
		this.name = name;
	}
	
	public String getPhaseName() {
		return name;
	}
}