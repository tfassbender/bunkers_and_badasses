package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;

public class GameTurnManager implements Serializable {
	
	private static final long serialVersionUID = -5200571555220181340L;
	
	private int turn;
	
	public GameTurnManager() {
		turn = 1;
	}
	
	public int getTurn() {
		return turn;
	}
	public void nextTurn() {
		turn++;
	}
}