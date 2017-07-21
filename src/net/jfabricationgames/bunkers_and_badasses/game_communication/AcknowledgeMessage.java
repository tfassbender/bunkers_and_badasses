package net.jfabricationgames.bunkers_and_badasses.game_communication;

public class AcknowledgeMessage extends BunkersAndBadassesMessage {
	
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