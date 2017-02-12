package net.jfabricationgames.bunkers_and_badasses.game_storage;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameLoadRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameSaveMessage;
import net.jfabricationgames.jfgserver.client.JFGClient;

public class GameStore {
	
	public static final int SERVER_TIMEOUT = 30;//time till server timeout in seconds
	
	private JFGClient client;
	
	private List<Boolean> serverAnswers;
	private Game loadedGame;
	
	public GameStore(JFGClient client) {
		this.client = client;
	}
	
	/**
	 * Send a request to the server to load a game by it's game overview and wait for the server to answer.
	 * This method is modal and should be called in a thread separated from the drawing or frame functions.
	 * 
	 * @param overview
	 * 		The GameOverview the player got from the server as the game was started.
	 * 
	 * @param players
	 * 		The number of players that will probably load the game to continue it.
	 * 
	 * @return
	 * 		The loaded game from the database.
	 * 
	 * @throws GameStorageException
	 * 		A GameStorageException is thrown when the game couldn't be loaded or server times out.
	 */
	public Game loadGame(GameOverview overview) throws GameStorageException {
		loadedGame = null;//delete a loaded game that may arrived after the timeout of the last load request
		GameLoadRequestMessage loadRequest = new GameLoadRequestMessage(overview);
		client.sendMessage(loadRequest);
		long timestamp = System.currentTimeMillis();
		try {
			//wait for the servers answer or abort after 30 seconds
			while (serverAnswers.isEmpty() && (System.currentTimeMillis()-timestamp) < SERVER_TIMEOUT * 1000) {
				Thread.sleep(250);
			}
			if (serverAnswers.isEmpty()) {
				//server timed out
				throw new GameStorageException("The server timed out after " + SERVER_TIMEOUT + " seconds");
			}
			else if (!serverAnswers.get(0)) {
				throw new GameStorageException("The server send a negative answer to the game load request");
			}
		}
		catch (InterruptedException ie) {
			throw new GameStorageException("Storing interrupted: no server answer received", ie);
		}
		return loadedGame;
	}
	
	/**
	 * Try to store the game in the database.
	 * This method waits for the servers answer and should therefore be called in a thread separated from the frame functions.
	 * 
	 * @param game
	 * 		The game that is stored.
	 * 
	 * @param gameEnded
	 * 		Indicates whether the game has ended and a statistic for the game should be created.
	 * 
	 * @throws GameStorageException
	 * 		A GameStorageException is thrown when the game couldn't be stored.
	 */
	public void storeGame(Game game, boolean gameEnded) throws GameStorageException {
		game.getBoard().setStoreImage(false);//don't send the image to the server
		GameSaveMessage save = new GameSaveMessage(game, gameEnded);
		client.sendMessage(save);//send the game to the server
		game.getBoard().setStoreImage(true);//enable the image sending again after sending the game 
		long timestamp = System.currentTimeMillis();
		try {
			//wait for the servers answer or abort after 30 seconds
			while (serverAnswers.isEmpty() && (System.currentTimeMillis()-timestamp) < SERVER_TIMEOUT * 1000) {
				Thread.sleep(250);
			}
			if (serverAnswers.isEmpty()) {
				//server timed out
				throw new GameStorageException("The server timed out after " + SERVER_TIMEOUT + " seconds");
			}
			else if (!serverAnswers.get(0)) {
				throw new GameStorageException("The server send a negative answer to the game storage request");
			}
			//else: everything went fine
		}
		catch (InterruptedException ie) {
			throw new GameStorageException("Storing interrupted: no server answer received", ie);
		}
	}
	
	/**
	 * Add a server answer on a game load or store request.
	 * This method should only be called by a client interpreter.
	 * 
	 * @param successful
	 * 		Indicates whether the load or store request was successful or not.
	 */
	public void addServerAnswer(boolean successful) {
		serverAnswers.add(successful);
	}
	
	/**
	 * Add a game that was loaded by the server.
	 * 
	 * @param loadedGame
	 * 		The game sent by the server.
	 */
	public void setLoadedGame(Game loadedGame) {
		this.loadedGame = loadedGame;
	}
}