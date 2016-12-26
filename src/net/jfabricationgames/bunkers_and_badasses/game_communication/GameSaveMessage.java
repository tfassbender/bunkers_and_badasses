package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

/**
 * Store the game in the database.
 * The Overview is created on server side.
 * 
 * After the game is stored the server sends a new GameSaveMessage back to the client.
 * This message only tells the user whether the game was saved correctly or some error occurred.
 */
public class GameSaveMessage implements JFGServerMessage, JFGClientMessage {
	
	private static final long serialVersionUID = -4591114359505923149L;

	private Game game;//the game object (images are included but not send because they are not serialized)
	
	private boolean gameEnded;
	private boolean saveSuccessful;
	
	public GameSaveMessage(Game game, boolean gameEnded) {
		this.game = game;
		this.gameEnded = gameEnded;
	}
	public GameSaveMessage(boolean saveSuccessful) {
		this.saveSuccessful = saveSuccessful;
	}
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	public boolean isGameEnded() {
		return gameEnded;
	}
	public void setGameEnded(boolean gameEnded) {
		this.gameEnded = gameEnded;
	}
	
	public boolean isSaveSuccessful() {
		return saveSuccessful;
	}
	public void setSaveSuccessful(boolean saveSuccessful) {
		this.saveSuccessful = saveSuccessful;
	}
}