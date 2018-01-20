package net.jfabricationgames.bunkers_and_badasses.server;

import java.io.IOException;
import java.net.Socket;

import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;
import net.jfabricationgames.jfgserver.secured_message.JFGAcknowledgeMessage;
import net.jfabricationgames.jfgserver.secured_message.JFGSecurableMessage;
import net.jfabricationgames.jfgserver.secured_message.JFGSecureMessageConnection;
import net.jfabricationgames.jfgserver.server.JFGConnection;
import net.jfabricationgames.jfgserver.server.JFGServer;
import net.jfabricationgames.logger.JFGLogger;
import net.jfabricationgames.logger.JFGLoggerManager;

public class BunkersAndBadassesConnection extends JFGSecureMessageConnection {
	
	private static JFGLogger communicationLogger;
	
	protected BunkersAndBadassesConnection() {
		if (communicationLogger == null) {
			try {
				communicationLogger = new JFGLogger("bunkers_and_badasses_server_communication_logs", 1000);
				JFGLoggerManager.addLogger(communicationLogger);
			}
			catch (IOException ioe) {
				communicationLogger = new JFGLogger();
				ioe.printStackTrace();
			}
		}
	}
	
	private BunkersAndBadassesConnection(JFGServer server, Socket socket) throws IOException {
		super(server, socket);
	}
	
	@Override
	public JFGConnection getInstance(JFGServer server, Socket socket) throws IOException {
		return new BunkersAndBadassesConnection(server, socket);
	}
	
	private void createSendLog(JFGClientMessage message, boolean unshared) {
		String log;
		if (message instanceof JFGSecurableMessage) {
			int messageId = ((JFGSecurableMessage) message).getMessageId();
			int messageCount = ((JFGSecurableMessage) message).getSendCount();
			log = "sending message (secured; ";
			if (unshared) {
				log += "unshared; ";
			}
			log += "id: " + messageId + "; ";
			log += "count: " + messageCount + "; ";
		}
		else {
			log = "sending message (unsecured; ";
			if (unshared) {
				log += "unshared; ";
			}
		}
		log += "type: " + message.getClass().getName();
		if (message instanceof JFGAcknowledgeMessage) {
			log += "; ackId: " + ((JFGAcknowledgeMessage) message).getAcknoledgingMessageId();
		}
		log += ");";
		communicationLogger.addLog(log);
	}
	private void createReceiveLog(JFGServerMessage message, boolean ack) {
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
				int messageCount = ((JFGSecurableMessage) message).getSendCount();
				log += "id: " + messageId + "; ";
				log += "count: " + messageCount + "; ";
			}
			log += "type: " + message.getClass().getName() + ");";
		}
		communicationLogger.addLog(log);
	}
	
	@Override
	public void sendMessage(JFGClientMessage message) {
		super.sendMessage(message);
		createSendLog(message, false);
	}
	
	@Override
	public void sendMessageUnshared(JFGClientMessage message) {
		super.sendMessageUnshared(message);
		createSendLog(message, true);
	}
	
	@Override
	public void receiveMessage(JFGServerMessage message) {
		super.receiveMessage(message);
		createReceiveLog(message, message instanceof JFGAcknowledgeMessage);
	}
}