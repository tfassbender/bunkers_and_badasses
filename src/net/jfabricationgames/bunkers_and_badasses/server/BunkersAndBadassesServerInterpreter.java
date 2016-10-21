package net.jfabricationgames.bunkers_and_badasses.server;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.chat.ChatMessage;
import net.jfabricationgames.bunkers_and_badasses.main_menu.MainMenuMessage;
import net.jfabricationgames.jfgdatabaselogin.message.JFGDatabaseLoginMessage;
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
		if (message instanceof JFGDatabaseLoginMessage) {
			loginInterpreter.interpreteServerMessage(message, connection);
		}
		else if (message instanceof ChatMessage) {
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
		switch (message.getMessageType()) {
			case DYNAMIC_CONTENT_ANSWER:
				//do nothing here because the server only sends the answers
				break;
			case DYNAMIC_CONTENT_REQUEST:
				//TODO load the content and send it back to the client
				break;
			case GAME_CREATION_ANSWER:
				//TODO send the answer to the player that created the game (MainMenuMessage.toPlayer)
				break;
			case GAME_CREATION_REQUEST:
				//TODO send the request to all players that are invited (MainMenuMessage.invitedPlayers)
				break;
			case PASSWORD_UPDATE:
				//TODO update the password of the player (MainMenuMessage.lastUsername)
				break;
			case PASSWORD_USERNAME_UPDATE:
				//TODO update the user name of the player (MainMenuMessage.lastUsername)
				break;
			case USERNAME_UPDATE:
				//TODO update the password and user name of the player (MainMenuMessage.lastUsername)
				break;
			case USERLIST_UPDATE:
				//do nothing here because the server only sends the updates
				break;
		}
	}
}