package net.jfabricationgames.bunkers_and_badasses.server;

import java.util.Map;

import net.jfabricationgames.jfgserver.server.JFGConnection;
import net.jfabricationgames.jfgserver.server.JFGLoginServer;

public class BunkersAndBadassesServer extends JFGLoginServer {
	
	private Map<Object, JFGConnection> userMap;
	
	public BunkersAndBadassesServer(int port) {
		super(port);
	}
	
	@Override
	public void acceptLogin(JFGConnection connection) {
		super.acceptLogin(connection);
		//TODO get the user name and map it to the connection
		//TODO when the user name is found send a message to all clients to update the user list
	}
}