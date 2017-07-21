package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameOverview;

public class GameLoadRequestMessage extends BunkersAndBadassesMessage {
	
	private static final long serialVersionUID = 2618838452517465888L;
	
	private Game loadedGame;
	private boolean loadedSuccessful;
	//private int players;
	
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
	
	/*public int getPlayers() {
		return players;
	}
	public void setPlayers(int players) {
		this.players = players;
	}*/
	
	public boolean isLoadedSuccessful() {
		return loadedSuccessful;
	}
	public void setLoadedSuccessful(boolean loadedSuccessful) {
		this.loadedSuccessful = loadedSuccessful;
	}
}