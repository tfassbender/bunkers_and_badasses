package net.jfabricationgames.bunkers_and_badasses.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_board.BoardKeeper;
import net.jfabricationgames.bunkers_and_badasses.game_board.BoardLoader;
import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardTransfereMessage;
import net.jfabricationgames.bunkers_and_badasses.main_menu.MainMenuMessage;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jdbc.JFGDatabaseConnection;
import net.jfabricationgames.jfgdatabaselogin.message.Cryptographer;
import net.jfabricationgames.jfgserver.server.JFGConnection;
import net.jfabricationgames.jfgserver.server.JFGLoginServer;

public class BunkersAndBadassesServer extends JFGLoginServer {
	
	public static final String loginTable = "users";
	
	private Map<User, JFGConnection> userMap;
	private Map<JFGConnection, User> connectionMap;
	
	private Map<Integer, BoardKeeper> loadedMaps;
	
	private List<User> allUsers;
	
	private MessageDigest md5;
	
	public BunkersAndBadassesServer(int port) {
		super(port);
		userMap = new HashMap<User, JFGConnection>();
		connectionMap = new HashMap<JFGConnection, User>();
		try {
			md5 = MessageDigest.getInstance("md5");
		}
		catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		loadUsers();
	}
	
	@Override
	public void acceptLogin(JFGConnection connection) {
		super.acceptLogin(connection);
		connection.sendMessage(new ServerNameRequest());
	}
	
	public void mapConnection(String username, JFGConnection connection) {
		User user = null;
		for (User u : allUsers) {
			if (u.getUsername().equals(username)) {
				user = u;
			}
		}
		if (user == null) {
			throw new IllegalArgumentException("The user (" + username + ") doesn't exist or is unknown.");
		}
		user.setOnline(true);
		userMap.put(user, connection);
		connectionMap.put(connection, user);
		
		sendUserUpdate();
	}
	
	public void logout(JFGConnection connection) {
		User user = connectionMap.get(connection);
		user.setInGame(false);
		user.setOnline(false);
		
		connectionMap.remove(connection);
		userMap.remove(user);
		
		closeConnection(connection);
		
		sendUserUpdate();
	}
	
	public void sendGameCreationRequest(MainMenuMessage message) {
		List<JFGConnection> invitedConnections = new ArrayList<JFGConnection>();
		for (User user : message.getInvitedPlayers()) {
			invitedConnections.add(userMap.get(user));
		}
		for (JFGConnection con : invitedConnections) {
			con.resetOutput();
			con.sendMessage(message);
		}
	}
	public void sendGameCreationAnswer(MainMenuMessage message) {
		JFGConnection toPlayer = userMap.get(message.getToPlayer());
		toPlayer.resetOutput();
		toPlayer.sendMessage(message);
	}
	public void sendGameCreationAbort(MainMenuMessage message) {
		//same as send request; just the message sent is different...
		sendGameCreationRequest(message);
	}
	
	public void updatePassword(MainMenuMessage message, JFGConnection conn) {
		Connection connection = JFGDatabaseConnection.getJFGDefaultConnection();
		Statement statement = null;
		boolean result = false;
		String password = getPasswordHash(message.getPassword());
		String sql = "UPDATE bunkers_and_badasses." + loginTable + " SET passwd = '"  + password + "' WHERE username = '" + message.getLastUsername() + "'";
		try {
			statement = connection.createStatement();
			statement.execute(sql);
			result = true;
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				statement.close();
			}
			catch (SQLException sqle) {
				//sqle.printStackTrace();
			}
			try {
				connection.close();
			}
			catch (SQLException sqle) {
				//sqle.printStackTrace();
			}
		}
		MainMenuMessage updateAnswer = new MainMenuMessage();
		updateAnswer.setMessageType(MainMenuMessage.MessageType.PASSWORD_USERNAME_UPDATE_ANSWER);
		updateAnswer.setUpdateSuccessfull(result);
		conn.sendMessage(updateAnswer);
	}
	public void updateUsername(MainMenuMessage message, JFGConnection conn) {
		Connection connection = JFGDatabaseConnection.getJFGDefaultConnection();
		Statement statement = null;
		boolean result = false;
		String sql = "UPDATE bunkers_and_badasses." + loginTable + " SET username = '"  + message.getUsername() + "' WHERE username = '" + message.getLastUsername() + "'";
		try {
			statement = connection.createStatement();
			statement.execute(sql);
			result = true;
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				statement.close();
			}
			catch (SQLException sqle) {
				//sqle.printStackTrace();
			}
			try {
				connection.close();
			}
			catch (SQLException sqle) {
				//sqle.printStackTrace();
			}
		}
		MainMenuMessage updateAnswer = new MainMenuMessage();
		updateAnswer.setMessageType(MainMenuMessage.MessageType.PASSWORD_USERNAME_UPDATE_ANSWER);
		updateAnswer.setUpdateSuccessfull(result);
		updateAnswer.setUsername(message.getUsername());
		conn.sendMessage(updateAnswer);
		if (result) {
			updateUserList(conn, message.getUsername());			
		}
	}
	public void updateUser(MainMenuMessage message, JFGConnection conn) {
		Connection connection = JFGDatabaseConnection.getJFGDefaultConnection();
		Statement statement = null;
		boolean result = false;
		String password = getPasswordHash(message.getPassword());
		String sql = "UPDATE bunkers_and_badasses." + loginTable + " SET passwd = '"  + password + "' WHERE username = '" + message.getLastUsername() + "'";
		String sql2 = "UPDATE bunkers_and_badasses." + loginTable + " SET username = '"  + message.getUsername() + "' WHERE username = '" + message.getLastUsername() + "'";
		try {
			statement = connection.createStatement();
			statement.execute(sql);
			statement.execute(sql2);
			result = true;
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				statement.close();
			}
			catch (SQLException sqle) {
				//sqle.printStackTrace();
			}
			try {
				connection.close();
			}
			catch (SQLException sqle) {
				//sqle.printStackTrace();
			}
		}
		MainMenuMessage updateAnswer = new MainMenuMessage();
		updateAnswer.setMessageType(MainMenuMessage.MessageType.PASSWORD_USERNAME_UPDATE_ANSWER);
		updateAnswer.setUpdateSuccessfull(result);
		updateAnswer.setUsername(message.getUsername());
		conn.sendMessage(updateAnswer);
		if (result) {
			updateUserList(conn, message.getUsername());			
		}
	}
	private String getPasswordHash(String password) {
		String pass = Cryptographer.decryptText(password, "cG4d2DP1MQlyonezuv71Z03");
		return getHash(pass);
	}
	private String getHash(String password) {
		byte[] inBuff = password.getBytes();
		byte[] outBuff = md5.digest(inBuff);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < outBuff.length; i++) {
			sb.append(Integer.toString((outBuff[i]&0xff)+0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	private void updateUserList(JFGConnection connection, String username) {
		User newUser = new User(username);
		newUser.setOnline(true);
		User user = connectionMap.get(connection);
		allUsers.remove(user);
		allUsers.add(newUser);
		userMap.remove(user);
		userMap.put(newUser, connection);
		connectionMap.put(connection, newUser);
		sendUserUpdate();
	}
	private void sendUserUpdate() {
		UserUpdateMessage update = new UserUpdateMessage(allUsers);
		for (JFGConnection con : getConnections()) {
			if (isLoggedIn(con)) {
				con.resetOutput();
				con.sendMessage(update);
			}
		}
	}
	
	private void loadUsers() {
		allUsers = new ArrayList<User>();
		Connection connection = JFGDatabaseConnection.getJFGDefaultConnection();
		Statement statement = null;
		ResultSet result = null;
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT username FROM bunkers_and_badasses." + loginTable + "");
			while (result.next()) {
				allUsers.add(new User(result.getString(1)));
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				result.close();
			}
			catch (SQLException e1) {
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
	}
	
	/**
	 * Load a map and send it back to the client.
	 * If the map is already loaded use the loaded one. Otherwise load the map and store it for the next player.
	 * The maps are stored until all players that play in the game have sent the request or up to 30 seconds.
	 * 
	 * @param id
	 * 		The map id in the database.
	 * 
	 * @param players
	 * 		The number of players playing in the game the map is used for.
	 * 
	 * @param connection
	 * 		The client connection that send the request.
	 */
	public void loadMap(int id, int players, JFGConnection connection) {
		BoardKeeper keeper = loadedMaps.get(id);
		synchronized (this) {//wait if the map is currently loaded
			if (keeper == null) {
				BoardLoader loader = new BoardLoader();
				Board board = loader.loadBoard(id);//get the path from the database and load the file
				keeper = new BoardKeeper(board, this, id, players);
				loadedMaps.put(id, keeper);//store the loaded map for the next players
			}			
		}
		BoardTransfereMessage transfere = new BoardTransfereMessage(keeper.getBoard());
		connection.sendMessage(transfere);
	}
	
	public List<User> getUsers() {
		return allUsers;
	}
	
	public Map<Integer, BoardKeeper> getLoadedMaps() {
		return loadedMaps;
	}
}