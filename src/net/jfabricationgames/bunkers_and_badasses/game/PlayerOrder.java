package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.error.TurnOrderException;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class PlayerOrder implements Serializable {
	
	private static final long serialVersionUID = 2676622781904752270L;
	
	private Map<Integer, User> order;//player order starting with 0
	private Map<Integer, User> nextOrder;
	private int move;
	private final int players;
	
	public PlayerOrder(int players) {
		order = new HashMap<Integer, User>();
		nextOrder = new HashMap<Integer, User>();
		this.players = players;
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
			order.put(positions[i], players.get(i));//add the player order
		}
	}
	
	/**
	 * Get the next player.
	 * 
	 * @return
	 * 		The next player.
	 */
	public User getNext() {
		User next = null;
		int nextMove = move+1;
		while (order.get(nextMove % players) == null) {
			nextMove++;
			if (nextMove > move+players) {
				throw new TurnOrderException("No next Player available. All players have passed.");
			}
		}
		return next;
	}
	
	/**
	 * Get the order of the players in this turn.
	 * If the players have already passed they don't show up in the array.
	 * 
	 * @return
	 * 		The player's order.
	 */
	public User[] getOrder() {
		User[] users = new User[order.size()-nextOrder.size()];
		int index = 0;
		for (int i : order.keySet()) {
			if (nextOrder.get(i) == null) {
				users[index] = order.get(i);
				index++;
			}
		}
		return users;
	}
	/**
	 * Get the player order of the next turn.
	 * 
	 * @return
	 * 		The player order as an array.
	 */
	public User[] getNextTurnOrder() {
		User[] users = new User[order.size()];
		for (int i : nextOrder.keySet()) {
			users[i] = nextOrder.get(i);
		}
		return users;
	}
	
	public User getActivePlayer() throws TurnOrderException {
		User user = order.get(move % players);
		if (user == null) {
			throw new TurnOrderException("No active player found.");
		}
		return user;
	}
	
	public boolean isPlayersTurn(User player) {
		return player.equals(getActivePlayer());
	}
	
	/**
	 * Inform the player order that a user has passed and set counter to the next move.
	 * 
	 * @param user
	 * 		The user that has passed.
	 */
	public void userPassed(User user) {
		int playersPassed = nextOrder.size();
		nextOrder.put(playersPassed, user);
		if (!isTurnEnd()) {
			nextMove();
		}
	}
	
	public void nextMove() throws TurnOrderException {
		move++;
		int additions = 0;//the additional moves to skip the players that have already passed
		while (order.get(move % players) == null) {
			move++;
			additions++;
			if (additions == players) {
				throw new TurnOrderException("No next move possible. All players have passed.");
			}
		}
	}
	
	/**
	 * Start the next turn and set the active player to the first player of this turn.
	 */
	public void nextTurn() {
		order = nextOrder;
		nextOrder = new HashMap<Integer, User>();
		move = 0;
	}
	
	/**
	 * Check whether all players have passed.
	 * 
	 * @return
	 * 		Returns true if all players have passed.
	 */
	public boolean isTurnEnd() {
		return nextOrder.size() == players;
	}
}