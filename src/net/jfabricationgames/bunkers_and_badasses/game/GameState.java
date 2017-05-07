package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;

public enum GameState implements Serializable {
	
	PLAN("Planungsphase"),
	ACT("Ausf�hrungsphase"),
	FIGHT("Kampf"),
	SELECT_BONUS("Rundenbonus ausw�hlen"),
	WAIT("Warten auf andere Spieler");
	
	private final String name;
	
	private GameState(String name) {
		this.name = name;
	}
	
	public String getPhaseName() {
		return name;
	}
}