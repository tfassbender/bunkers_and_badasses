package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;
import net.jfabricationgames.jfgserver.secured_message.JFGSecurableMessage;

public class BunkersAndBadassesMessage implements JFGClientMessage, JFGServerMessage, JFGSecurableMessage {
	
	private static final long serialVersionUID = 5360301314766023754L;
	
	protected int id;//a randomly generated id to identify the message
	
	private int sendCount;
	
	public BunkersAndBadassesMessage() {
		id = (int) (Math.random() * Integer.MAX_VALUE);
	}
	
	public int getMessageId() {
		return id;
	}
	
	@Override
	public int getSendCount() {
		return sendCount;
	}
	@Override
	public void setSendCount(int count) {
		this.sendCount = count;
	}
}