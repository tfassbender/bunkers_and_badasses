package net.jfabricationgames.bunkers_and_badasses.server;

import net.jfabricationgames.jfgserver.client.JFGClientMessage;

/**
 * A message that tells the user that he got kicked from the server (probably because of connection lost or not pinging). 
 */
public class ServerLogoutMessage implements JFGClientMessage {
	
	private static final long serialVersionUID = 3250679698188725306L;
	
	private String message;
	
	public ServerLogoutMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}