package net.jfabricationgames.bunkers_and_badasses.server;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.chat.ChatMessage;
import net.jfabricationgames.bunkers_and_badasses.main_menu.MainMenuMessage;
import net.jfabricationgames.jfgdatabaselogin.server.JFGDatabaseLoginServerInterpreter;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;
import net.jfabricationgames.jfgserver.interpreter.JFGServerInterpreter;
import net.jfabricationgames.jfgserver.server.JFGConnection;

public class BunkersAndBadassesServerInterpreter implements JFGServerInterpreter {
	
	private JFGDatabaseLoginServerInterpreter loginInterpreter;
	
	public BunkersAndBadassesServerInterpreter(JFGDatabaseLoginServerInterpreter loginInterpreter) {
		this.loginInterpreter = loginInterpreter;
	}
	
	@Override
	public JFGServerInterpreter getInstance() {
		BunkersAndBadassesServerInterpreter interpreter = new BunkersAndBadassesServerInterpreter(loginInterpreter);
		return interpreter;
	}
	
	@Override
	public void interpreteServerMessage(JFGServerMessage message, JFGConnection connection) {
		if (message instanceof ChatMessage) {
			interpreteChatMessage((ChatMessage) message, connection);
		}
		else if (message instanceof MainMenuMessage) {
			interpreteMainMenuMessage((MainMenuMessage) message, connection);
		}
	}
	
	private void interpreteChatMessage(ChatMessage message, JFGConnection connection) {
		if (connection.getGroup() != null) {
			//broadcast to all users of the group
			connection.getGroup().sendMessage(message, connection);
		}
		else {
			//broadcast to all users that are not in a group
			List<JFGConnection> con = connection.getServer().getConnections();
			for (JFGConnection c : con) {
				if (c.getGroup() == null && !c.equals(connection)) {
					c.sendMessage(message);
				}
			}
		}
	}
	
	private void interpreteMainMenuMessage(MainMenuMessage message, JFGConnection connection) {
		//TODO
		switch (message.getMessageType()) {
			case DYNAMIC_CONTENT_ANSWER:
				break;
			case DYNAMIC_CONTENT_REQUEST:
				break;
			case GAME_CREATION_ANSWER:
				break;
			case GAME_CREATION_REQUEST:
				break;
			case PASSWORD_UPDATE:
				break;
			case PASSWORD_USERNAME_UPDATE:
				break;
			case USERNAME_UPDATE:
				break;
			default:
				break;
		}
	}
}