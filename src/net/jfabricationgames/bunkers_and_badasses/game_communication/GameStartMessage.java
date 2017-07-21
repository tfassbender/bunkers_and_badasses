package net.jfabricationgames.bunkers_and_badasses.game_communication;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class GameStartMessage extends BunkersAndBadassesMessage {
	
	private static final long serialVersionUID = -6508499475274385404L;
	
	private int gameId;//the game's id in the database 
	private int boardId;//the map's id in the database
	private boolean loaded;//indicates whether the game is new or loaded
	
	private List<User> players;
	
	private User startingPlayer;
	
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public List<User> getPlayers() {
		return players;
	}
	public void setPlayers(List<User> players) {
		this.players = players;
	}
	
	public User getStartingPlayer() {
		return startingPlayer;
	}
	public void setStartingPlayer(User startingPlayer) {
		this.startingPlayer = startingPlayer;
	}
}