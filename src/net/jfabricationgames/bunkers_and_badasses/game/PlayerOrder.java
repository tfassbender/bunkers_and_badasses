package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class PlayerOrder implements Serializable {
	
	private static final long serialVersionUID = 2676622781904752270L;
	
	private Map<Integer, User> order;//player order starting with 0
	private int move;
	private int passed;
	private GameTurnManager gameTurnManager;
	private GameTurnBonusManager gameTurnBonusManager;
	
	public PlayerOrder(GameTurnBonusManager gameTurnBonusManager) {
		this.gameTurnBonusManager = gameTurnBonusManager;
	}
	
	/**
	 * Select a random order for the first turn.
	 * 
	 * @param players
	 * 		The players joining the game.
	 */
	public void chooseRandomOrder(List<User> players) {
		int[] positions = new int[players.size()];
		int swap;
		int random;
		//initialize the positions array with values from 0 to players.size()
		for (int i = 0; i < positions.length; i++) {
			positions[i] = i;
		}
		for (int i = 0; i < positions.length; i++) {
			random = (int) (Math.random() * (positions.length - i)) + i;//select a random integer from i to players.size()
			swap = positions[random];//swap the chosen integer to position i
			positions[random] = positions[i];
			positions[i] = swap;
			order.put(positions[i], players.get(i));//add the color
		}
	}
	
	public User getNext() {
		//TODO
		return null;
	}
	
	public User[] getOrder() {
		//TODO
		return null;
	}
	
	public User getActivePlayer() {
		//TODO
		return null;
	}
	
	public boolean isPlayersTurn(User player) {
		return player.equals(getActivePlayer());
	}
	
	public void userPassed(User user) {
		//TODO
	}
	
	public void nextMove() {
		//TODO		
	}
	
	public void nextTurn() {
		//TODO
	}
	
	public boolean isTurnEnd() {
		//TODO
		return false;
	}
	
	public void setGameTurnManager(GameTurnManager gameTurnManager) {
		this.gameTurnManager = gameTurnManager;
	}
}