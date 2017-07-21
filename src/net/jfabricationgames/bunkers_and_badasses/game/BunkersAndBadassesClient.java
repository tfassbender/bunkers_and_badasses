package net.jfabricationgames.bunkers_and_badasses.game;

import net.jfabricationgames.bunkers_and_badasses.game_communication.AcknowledgeMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.BunkersAndBadassesMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.CommunicationSecurity;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

public class BunkersAndBadassesClient extends JFGClient {
	
	private CommunicationSecurity communicationSecurity;
	
	public BunkersAndBadassesClient(JFGClient client) {
		super(client);//create a clone of the client
		communicationSecurity = new CommunicationSecurity(this);
	}
	
	/**
	 * Send a secured message to the server and check it's arrival.
	 * The CommunicationSecurity checks that the message arrives.
	 * 
	 * @param message
	 * 		The message send to the server.
	 */
	@Override
	public void sendMessage(JFGServerMessage message) {
		communicationSecurity.secureMessage((BunkersAndBadassesMessage) message);
		super.sendMessage(message);
	}
	/**
	 * Send a message to the server connected to this JFGClient using the writeUnshared method.
	 * The CommunicationSecurity checks that the message arrives.
	 * 
	 * @param message
	 * 		The message to send to the server.
	 */
	@Override
	public void sendMessageUnshared(JFGServerMessage message) {
		communicationSecurity.secureMessage((BunkersAndBadassesMessage) message);
		super.sendMessageUnshared(message);
	}
	
	/**
	 * Receive a message that was send to the client's socket.
	 * 
	 * If it's an ACK-Message it's passed on to the communication security.
	 * Otherwise it's passed on to the interpreter. 
	 * 
	 * @param message
	 * 		The message that was received.
	 */
	@Override
	public void receiveMessage(JFGClientMessage message) {
		if (message instanceof AcknowledgeMessage) {
			communicationSecurity.receiveAcknoledgeMessage((AcknowledgeMessage) message);
		}
		else {
			super.receiveMessage(message);
		}
	}
}