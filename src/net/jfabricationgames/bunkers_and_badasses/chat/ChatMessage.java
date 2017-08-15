package net.jfabricationgames.bunkers_and_badasses.chat;

import net.jfabricationgames.bunkers_and_badasses.game_communication.BunkersAndBadassesMessage;

public class ChatMessage extends BunkersAndBadassesMessage {
	
	private static final long serialVersionUID = -2344862251118965570L;
	
	private String message;
	
	public ChatMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}