package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

public class BunkersAndBadassesMessage implements JFGClientMessage, JFGServerMessage, JFGSecurableMessage {
	
	private static final long serialVersionUID = 5360301314766023754L;
	
	protected int id;//a randomly generated id to identify the message
	
	public BunkersAndBadassesMessage() {
		id = Integer.MIN_VALUE + (int) (2 * Math.random() * Integer.MAX_VALUE);
	}
	
	public int getMessageId() {
		return id;
	}
}