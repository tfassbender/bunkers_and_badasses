package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

public class AcknowledgeMessage implements JFGClientMessage, JFGServerMessage {
	
	private static final long serialVersionUID = 8900952077551743235L;
	
	private int acknoledgingMessageId;
	
	public AcknowledgeMessage(int acknoledgingMessageId) {
		this.acknoledgingMessageId = acknoledgingMessageId;
	}
	
	public int getAcknoledgingMessageId() {
		return acknoledgingMessageId;
	}
	public void setAcknoledgingMessageId(int acknoledgingMessageId) {
		this.acknoledgingMessageId = acknoledgingMessageId;
	}
}