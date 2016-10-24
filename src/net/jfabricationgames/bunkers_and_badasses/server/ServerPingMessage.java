package net.jfabricationgames.bunkers_and_badasses.server;

import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

public class ServerPingMessage implements JFGClientMessage, JFGServerMessage {
	
	private static final long serialVersionUID = 356519488031061112L;
	
	public ServerPingMessage() {
		
	}
}