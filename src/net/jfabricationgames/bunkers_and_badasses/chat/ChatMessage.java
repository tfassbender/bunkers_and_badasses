package net.jfabricationgames.bunkers_and_badasses.chat;

import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

public class ChatMessage implements JFGServerMessage, JFGClientMessage {
	
	private static final long serialVersionUID = -2344862251118965570L;
	
	private String message;
	
	public ChatMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}