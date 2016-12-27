package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameOverview;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

public class GameLoadRequestMessage implements JFGServerMessage, JFGClientMessage {
	
	private static final long serialVersionUID = 2618838452517465888L;
	
	private Game loadedGame;
	private boolean loadedSuccessful;
	
	private GameOverview overview;//identifies the game to be loaded by it's id in the database
	
	public GameLoadRequestMessage(GameOverview overview) {
		this.overview = overview;
	}
	
	public Game getLoadedGame() {
		return loadedGame;
	}
	public void setLoadedGame(Game loadedGame) {
		this.loadedGame = loadedGame;
	}
	
	public GameOverview getOverview() {
		return overview;
	}
	public void setOverview(GameOverview overview) {
		this.overview = overview;
	}
	
	public boolean isLoadedSuccessful() {
		return loadedSuccessful;
	}
	public void setLoadedSuccessful(boolean loadedSuccessful) {
		this.loadedSuccessful = loadedSuccessful;
	}
}