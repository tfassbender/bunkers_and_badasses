package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;

public class GameTurnManager implements Serializable {
	
	private static final long serialVersionUID = -5200571555220181340L;
	
	private int turn;
	
	private static int numTurns = 10;//the number of turns in the game
	
	private PlayerOrder playerOrder;
	private GameTurnGoalManager gameTurnGoalManager;
	private UserResourceManager resourceManager;
	
	public GameTurnManager(PlayerOrder playerOrder, GameTurnGoalManager gameTurnGoalManager, UserResourceManager resourceManager) {
		this.playerOrder = playerOrder;
		this.gameTurnGoalManager = gameTurnGoalManager;
		this.resourceManager = resourceManager;
		turn = 1;
	}
	
	public int getTurn() {
		return turn;
	}
	public void nextTurn() {
		turn++;
	}
	public static int getNumTurns() {
		return numTurns;
	}
	
	public PlayerOrder getPlayerOrder() {
		return playerOrder;
	}
	
	public GameTurnGoalManager getGameTurnGoalManager() {
		return gameTurnGoalManager;
	}
	
	public UserResourceManager getResourceManager() {
		return resourceManager;
	}
}