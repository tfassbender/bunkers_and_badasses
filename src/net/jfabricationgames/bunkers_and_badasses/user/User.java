package net.jfabricationgames.bunkers_and_badasses.user;

public class User {
	
	private String username;
	private boolean online;
	private boolean inGame;
	
	public User(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public boolean isInGame() {
		return inGame;
	}
	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}
}