package net.jfabricationgames.bunkers_and_badasses.server;

import net.jfabricationgames.bunkers_and_badasses.game_communication.BunkersAndBadassesMessage;

public class ServerNameRequest extends BunkersAndBadassesMessage {
	
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