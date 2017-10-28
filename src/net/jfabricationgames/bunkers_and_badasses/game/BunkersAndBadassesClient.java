package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.IOException;

import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;
import net.jfabricationgames.jfgserver.secured_message.JFGAcknowledgeMessage;
import net.jfabricationgames.jfgserver.secured_message.JFGSecurableMessage;
import net.jfabricationgames.jfgserver.secured_message.JFGSecureMessageClient;
import net.jfabricationgames.logger.JFGLogger;
import net.jfabricationgames.logger.JFGLoggerManager;

/**
 * A subclass of the JFGSecureMessageClient that logs all outgoing and incoming messages.
 * 
 * This client is not working because the construction of a JFGClient from another client seems not to work.
 * It was only used for message logging so...
 */
@Deprecated
public class BunkersAndBadassesClient extends JFGSecureMessageClient {
	
	private static JFGLogger communicationLogger;
	
	public BunkersAndBadassesClient(JFGSecureMessageClient secureMessageClient) {
		super(secureMessageClient);
		if (communicationLogger == null) {
			try {
				communicationLogger = new JFGLogger("bunkers_and_badasses_client_communication_logs", 100);
				JFGLoggerManager.addLogger(communicationLogger);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	private void createSendLog(JFGServerMessage message, boolean unshared) {
		String log;
		if (message instanceof JFGSecurableMessage) {
			int messageId = ((JFGSecurableMessage) message).getMessageId();
			log = "sending message (secured; ";
			if (unshared) {
				log += "unshared; ";
			}
			log += "id: " + messageId + "; ";
		}
		else {
			log = "sending message (unsecured; ";
			if (unshared) {
				log += "unshared; ";
			}
		}
		log += "type: " + message.getClass().getName() + ");";
		communicationLogger.addLog(log);
	}
	private void createReceiveLog(JFGClientMessage message, boolean ack) {
		String log;
		if (ack) {
			int ackId = ((JFGAcknowledgeMessage) message).getAcknoledgingMessageId();
			log = "received acknowledge message (ackId: ";
			log += ackId + ");";
		}
		else {
			log = "received message (";
			if (!(message instanceof JFGSecurableMessage)) {
				log += "unsecured; ";
			}
			else {
				int messageId = ((JFGSecurableMessage) message).getMessageId();
				log += "id: " + messageId + "; ";
			}
			log += "type: " + message.getClass().getName() + ");";
		}
		communicationLogger.addLog(log);
	}
	
	@Override
	public void sendMessage(JFGServerMessage message) {
		createSendLog(message, false);
		super.sendMessage(message);
	}
	
	@Override
	public void sendMessageUnshared(JFGServerMessage message) {
		createSendLog(message, true);
		super.sendMessageUnshared(message);
	}
	
	@Override
	public void receiveMessage(JFGClientMessage message) {
		createReceiveLog(message, message instanceof JFGAcknowledgeMessage);
		super.receiveMessage(message);
	}
	
	@Override
	public void resetOutput() {
		communicationLogger.addLog("reset output");
		super.resetOutput();
	}
	
	@Override
	public void closeConnection() {
		communicationLogger.addLog("connection closed");
		super.closeConnection();
	}
}