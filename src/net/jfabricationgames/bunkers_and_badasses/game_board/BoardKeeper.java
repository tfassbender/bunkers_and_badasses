package net.jfabricationgames.bunkers_and_badasses.game_board;

import net.jfabricationgames.bunkers_and_badasses.server.BunkersAndBadassesServer;

/**
 * Store information about a map that is loaded by the server.
 * The maps are sent to all players taking part in a game and don't need to be loaded every time.
 */
public class BoardKeeper {
	
	public static final int KEEP_TIME = 10;//time till the loaded board reference is deleted (in seconds)
	
	private int id;//the map id in the database
	private int players;//the players taking part in the game
	private int requests;//the requests that reached the server
	
	private Board board;
	
	private BunkersAndBadassesServer server;
	
	public BoardKeeper(Board board, BunkersAndBadassesServer server, int id, int players) {
		this.board = board;
		this.server = server;
		this.id = id;
		this.players = players;
		requests = 0;
		startTimer();
	}

	public Board getBoard() {
		requests++;
		if (requests >= players) {
			//the board was sent to all players
			server.getLoadedMaps().remove(id);
		}
		return board;
	}
	
	/**
	 * Start the timer to delete the reference to the board after the KEEP_TIME is over.
	 */
	private void startTimer() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(KEEP_TIME * 1000);//sleep for KEEP_TIME seconds
				}
				catch (InterruptedException ie) {
					//ie.printStackTrace();
				}
				//delete the reference to this object after the KEEP_TIME is over
				server.getLoadedMaps().remove(id);
			}
		}).start();
	}
}