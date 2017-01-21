package net.jfabricationgames.bunkers_and_badasses.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.chat.ChatMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardOverviewRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameLoadRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameOverviewRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameSaveMessage;
import net.jfabricationgames.bunkers_and_badasses.main_menu.MainMenuMessage;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jdbc.JFGDatabaseConnection;
import net.jfabricationgames.jfgdatabaselogin.message.JFGDatabaseLoginMessage;
import net.jfabricationgames.jfgdatabaselogin.message.JFGDatabaseLoginMessageType;
import net.jfabricationgames.jfgdatabaselogin.server.JFGDatabaseLoginServerInterpreter;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;
import net.jfabricationgames.jfgserver.interpreter.JFGServerInterpreter;
import net.jfabricationgames.jfgserver.server.JFGConnection;

public class BunkersAndBadassesServerInterpreter implements JFGServerInterpreter {
	
	private JFGDatabaseLoginServerInterpreter loginInterpreter;
	
	private BunkersAndBadassesServer server;
	
	public BunkersAndBadassesServerInterpreter(JFGDatabaseLoginServerInterpreter loginInterpreter, BunkersAndBadassesServer server) {
		this.loginInterpreter = loginInterpreter;
		this.server = server;
	}
	
	@Override
	public JFGServerInterpreter getInstance() {
		BunkersAndBadassesServerInterpreter interpreter = new BunkersAndBadassesServerInterpreter(loginInterpreter, server);
		return interpreter;
	}
	
	@Override
	public void interpreteServerMessage(JFGServerMessage message, JFGConnection connection) {
		//login messages
		if (message instanceof JFGDatabaseLoginMessage) {
			interpreteDatabaseLoginMessage((JFGDatabaseLoginMessage) message, connection);
		}
		else if (message instanceof ServerNameRequest) {
			interpreteServerNameRequest((ServerNameRequest) message, connection);
		}
		else if (message instanceof MainMenuMessage) {
			interpreteMainMenuMessage((MainMenuMessage) message, connection);
		}
		else if (message instanceof ChatMessage) {
			interpreteChatMessage((ChatMessage) message, connection);
		}
		else if (message instanceof UserLogoutMessage) {
			interpreteUserLogoutMessage((UserLogoutMessage) message, connection);
		}
		else if (message instanceof ServerPingMessage) {
			//TODO react on ping (user still connected)
		}
		//board loading message
		else if (message instanceof BoardRequestMessage) {
			interpreteBoardRequestMessage((BoardRequestMessage) message, connection);
		}
		else if (message instanceof BoardOverviewRequestMessage) {
			interpreteBoardOverviewRequestMessage((BoardOverviewRequestMessage) message, connection);
		}
		//game storing message
		else if (message instanceof GameSaveMessage) {
			interpreteGameSaveMessage((GameSaveMessage) message, connection);
		}
		//game loading messages
		else if (message instanceof GameOverviewRequestMessage) {
			interpreteGameOverviewRequestMessage((GameOverviewRequestMessage) message, connection);
		}
		else if (message instanceof GameLoadRequestMessage) {
			interpreteGameLoadRequestMessage((GameLoadRequestMessage) message, connection);
		}
	}
	
	private void interpreteDatabaseLoginMessage(JFGDatabaseLoginMessage message, JFGConnection connection) {
		loginInterpreter.interpreteServerMessage(message, connection);
		//if the user signs up add him to the user list
		JFGDatabaseLoginMessage loginMessage = (JFGDatabaseLoginMessage) message;
		if (loginMessage.getMessageType().equals(JFGDatabaseLoginMessageType.SIGN_UP)) {
			User user = new User(loginMessage.getUsername());
			if (!server.getUsers().contains(user)) {
				server.getUsers().add(user);
			}
		}
	}
	
	private void interpreteServerNameRequest(ServerNameRequest message, JFGConnection connection) {
		((BunkersAndBadassesServer) connection.getServer()).mapConnection(message.getUsername(), connection);
	}
	
	private void interpreteMainMenuMessage(MainMenuMessage message, JFGConnection connection) {
		switch (message.getMessageType()) {
			case DYNAMIC_CONTENT_ANSWER:
				//do nothing here because the server only sends the answers
				break;
			case DYNAMIC_CONTENT_REQUEST:
				MainMenuMessage dynamicContent = createDynamicContentAnswer();
				connection.sendMessage(dynamicContent);
				break;
			case GAME_CREATION_ANSWER:
				server.sendGameCreationAnswer(message);
				break;
			case GAME_CREATION_REQUEST:
				server.sendGameCreationRequest(message);
				break;
			case GAME_CREATEION_ABORT:
				server.sendGameCreationAbort(message);
				break;
			case PASSWORD_UPDATE:
				server.updatePassword(message, connection);
				break;
			case PASSWORD_USERNAME_UPDATE:
				server.updateUser(message, connection);
				break;
			case USERNAME_UPDATE:
				server.updateUsername(message, connection);
				break;
			case PASSWORD_USERNAME_UPDATE_ANSWER:
				//do nothing here; only client side
				break;
		}
	}
	private MainMenuMessage createDynamicContentAnswer() {
		Connection connection = JFGDatabaseConnection.getJFGDefaultConnection();
		Statement statement = null;
		ResultSet result = null;
		MainMenuMessage message = new MainMenuMessage();
		message.setMessageType(MainMenuMessage.MessageType.DYNAMIC_CONTENT_ANSWER);
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT content, priority FROM bunkers_and_badasses.main_menu_dynamic_content WHERE display = true ORDER BY priority");
			StringBuilder sb = new StringBuilder();
			//sb.append("<html>");
			while (result.next()) {
				sb.append(result.getString(1));
				sb.append("\n");
				//sb.append("<br><br><br>");
			}
			//sb.append("</html>");
			message.setDynamicContentAnswer(sb.toString());
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				result.close();
			}
			catch (SQLException e) {
				;
			}
			try {
				statement.close();
			}
			catch (SQLException e) {
				;
			}
			try {
				connection.close();
			}
			catch (SQLException e) {
				;
			}
		}
		return message;
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
	
	private void interpreteUserLogoutMessage(UserLogoutMessage message, JFGConnection connection) {
		((BunkersAndBadassesServer) connection.getServer()).logout(connection);
	}
	
	private void interpreteBoardRequestMessage(BoardRequestMessage message, JFGConnection connection) {
		server.loadMap(message.getId(), message.getPlayers(), connection);
	}
	
	private void interpreteBoardOverviewRequestMessage(BoardOverviewRequestMessage message, JFGConnection connection) {
		server.loadMapOverviews(message, connection);
	}
	
	private void interpreteGameSaveMessage(GameSaveMessage message, JFGConnection connection) {
		boolean saveSuccesful = server.saveGame(message.getGame(), message.isGameEnded());
		GameSaveMessage answer = new GameSaveMessage(saveSuccesful);
		connection.sendMessage(answer);
	}
	
	private void interpreteGameOverviewRequestMessage(GameOverviewRequestMessage message, JFGConnection connection) {
		server.sendGameOverviews(message, connection);
	}
	
	private void interpreteGameLoadRequestMessage(GameLoadRequestMessage message, JFGConnection connection) {
		server.loadGame(message, connection);
	}
}