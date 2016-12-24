package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class Game {
	
	private List<UserResource> resources;
	private GameState state;
	private PlayerOrder order;
	private List<User> players;
	private UserColorManager colorManager;
	private Board board;
	
	public List<UserResource> getResources() {
		return resources;
	}
	public void setResources(List<UserResource> resources) {
		this.resources = resources;
	}
	
	public GameState getState() {
		return state;
	}
	public void setState(GameState state) {
		this.state = state;
	}
	
	public PlayerOrder getOrder() {
		return order;
	}
	public void setOrder(PlayerOrder order) {
		this.order = order;
	}
	
	public List<User> getPlayers() {
		return players;
	}
	public void setPlayers(List<User> players) {
		this.players = players;
	}
	
	public UserColorManager getColorManager() {
		return colorManager;
	}
	public void setColorManager(UserColorManager colorManager) {
		this.colorManager = colorManager;
	}
	
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
}