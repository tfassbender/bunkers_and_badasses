package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.jfgserver.client.JFGServerMessage;

/**
 * Send a request to the server to load a basic map from the file system.
 * The id is the primary key in the maps table in the database that identifies the map that is to be loaded.
 */
public class BoardRequestMessage implements JFGServerMessage {
	
	private static final long serialVersionUID = -1598848885636908438L;
	
	private int id;//the id in the maps table that identifies the map
	private int players;//the number of players that will send a request for this map (for this game start)
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPlayers() {
		return players;
	}
	public void setPlayers(int players) {
		this.players = players;
	}
}