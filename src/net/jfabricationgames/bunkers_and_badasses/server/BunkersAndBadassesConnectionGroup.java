package net.jfabricationgames.bunkers_and_badasses.server;

import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.server.DefaultJFGConnectionGroup;
import net.jfabricationgames.jfgserver.server.JFGConnection;

public class BunkersAndBadassesConnectionGroup extends DefaultJFGConnectionGroup {
	
	private User startingPlayer;
	private JFGConnection startingPlayerConnection;
	
	@Override
	public void groupStarted() {
		//do nothing here
		//no need to send a message that confirms the group creation
	}
	
	/**
	 * Send a message to the starting player of this group.
	 */
	public void sendToStartingPlayer(JFGClientMessage message) {
		startingPlayerConnection.sendMessage(message);
	}
	
	public User getStartingPlayer() {
		return startingPlayer;
	}
	public void setStartingPlayer(User startingPlayer) {
		this.startingPlayer = startingPlayer;
	}
	
	public JFGConnection getStartingPlayerConnection() {
		return startingPlayerConnection;
	}
	public void setStartingPlayerConnection(JFGConnection startingPlayerConnection) {
		this.startingPlayerConnection = startingPlayerConnection;
	}
}