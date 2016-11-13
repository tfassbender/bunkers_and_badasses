package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class PlayerOrder {
	
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