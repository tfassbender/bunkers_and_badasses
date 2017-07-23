package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.jfgserver.client.JFGClient;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

public class JFGSecureMessageClient extends JFGClient {
	
	private JFGCommunicationSecurity communicationSecurity;
	
	public JFGSecureMessageClient(JFGClient client) {
		super(client);//create a clone of the client
		communicationSecurity = new JFGCommunicationSecurity(this);
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
		communicationSecurity.secureMessage(message);
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
		communicationSecurity.secureMessage(message);
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
		if (message instanceof JFGAcknowledgeMessage) {
			communicationSecurity.receiveAcknoledgeMessage((JFGAcknowledgeMessage) message);
		}
		else {
			if (!communicationSecurity.isResentMessage(message)) {
				super.receiveMessage(message);
			}
			communicationSecurity.sendAcknowledge(message);
		}
	}
}