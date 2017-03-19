package net.jfabricationgames.bunkers_and_badasses.game;

public enum GameState {
	
	PLAN("Planungsphase"),
	ACT("Ausführungsphase"),
	FIGHT("Kampf"),
	SELECT_BONUS("Rundenbonus auswählen"),
	WAIT("Warten auf andere Spieler");
	
	private final String name;
	
	private GameState(String name) {
		this.name = name;
	}
	
	public String getPhaseName() {
		return name;
	}
}