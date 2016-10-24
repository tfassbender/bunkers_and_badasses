package net.jfabricationgames.bunkers_and_badasses.server;

import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

public class ServerNameRequest implements JFGClientMessage, JFGServerMessage {
	
	private static final long serialVersionUID = 8022484186838485479L;
	
	private String username;
	
	public ServerNameRequest() {
		//the server sends an empty request
	}
	public ServerNameRequest(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
}