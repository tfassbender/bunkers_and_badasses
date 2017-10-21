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
				communicationLogger = new JFGLogger("bunkers_and_badasses_server_communication_logs", 100);
				JFGLoggerManager.addLogger(communicationLogger);
			}
			catch (IOException ioe) {
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
				log += "id: " + messageId + "; ";
			}
			log += "type: " + message.getClass().getName() + ");";
		}
		communicationLogger.addLog(log);
	}
	
	@Override
	public void sendMessage(JFGClientMessage message) {
		createSendLog(message, false);
		super.sendMessage(message);
	}
	
	@Override
	public void sendMessageUnshared(JFGClientMessage message) {
		createSendLog(message, true);
		super.sendMessageUnshared(message);
	}
	
	@Override
	public void receiveMessage(JFGServerMessage message) {
		createReceiveLog(message, message instanceof JFGAcknowledgeMessage);
		super.receiveMessage(message);
	}
}