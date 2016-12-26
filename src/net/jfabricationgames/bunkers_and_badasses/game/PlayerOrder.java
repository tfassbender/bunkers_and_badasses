package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class PlayerOrder implements Serializable {
	
	private static final long serialVersionUID = 2676622781904752270L;
	
	private Map<Integer, User> order;
	private int move;
	private int passed;
	
	public User getNext() {
		//TODO
		return null;
	}
	
	public User[] getOrder() {
		//TODO
		return null;
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
}