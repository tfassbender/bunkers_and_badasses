package net.jfabricationgames.bunkers_and_badasses.server;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.server.DefaultJFGConnectionGroup;
import net.jfabricationgames.jfgserver.server.JFGConnection;
import net.jfabricationgames.jfgserver.server.JFGConnectionGroup;

public class BunkersAndBadassesConnectionGroup extends DefaultJFGConnectionGroup {
	
	private User startingPlayer;
	private JFGConnection startingPlayerConnection;
	
	protected BunkersAndBadassesConnectionGroup() {
		
	}
	public BunkersAndBadassesConnectionGroup(List<JFGConnection> connections) {
		super(connections);
	}
	
	@Override
	public void groupStarted() {
		//do nothing here
		//no need to send a message that confirms the group creation
	}
	
	@Override
	public JFGConnectionGroup getInstance(List<JFGConnection> connections) {
		return new BunkersAndBadassesConnectionGroup(connections);
	}
	
	public void resetAll() {
		for (JFGConnection connection : getConnections()) {
			connection.resetOutput();
		}
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