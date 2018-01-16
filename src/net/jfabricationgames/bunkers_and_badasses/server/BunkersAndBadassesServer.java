package net.jfabricationgames.bunkers_and_badasses.server;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameVariableStorage;
import net.jfabricationgames.bunkers_and_badasses.game.SkillProfile;
import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;
import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_board.BoardKeeper;
import net.jfabricationgames.bunkers_and_badasses.game_board.BoardLoader;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.BuildingStorage;
import net.jfabricationgames.bunkers_and_badasses.game_character.troop.TroopStorage;
import net.jfabricationgames.bunkers_and_badasses.game_command.CommandStorage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardOverviewRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardTransfereMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.DynamicVariableRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameCreationMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameLoadRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameOverviewRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameStartMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameStatisticsRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameTransferMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.SkillProfileTransferMessage;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameOverview;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusStorage;
import net.jfabricationgames.bunkers_and_badasses.help.HelpContent;
import net.jfabricationgames.bunkers_and_badasses.main_menu.MainMenuMessage;
import net.jfabricationgames.bunkers_and_badasses.statistic.GameStatistic;
import net.jfabricationgames.bunkers_and_badasses.statistic.GameStatisticManager;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jdbc.JFGDatabaseConnection;
import net.jfabricationgames.jfgdatabaselogin.message.Cryptographer;
import net.jfabricationgames.jfgdatabaselogin.message.JFGDatabaseLoginMessage;
import net.jfabricationgames.jfgserver.secured_message.JFGSecureLoginServer;
import net.jfabricationgames.jfgserver.server.JFGConnection;
import net.jfabricationgames.logger.JFGLogger;
import net.jfabricationgames.logger.JFGLoggerManager;

public class BunkersAndBadassesServer extends JFGSecureLoginServer {
	
	public static final String loginTable = "users";
	
	/**
	 * Indicates whether this application is a client- or a server-application.
	 */
	public static boolean IS_SERVER_APPLICATION = false;
	
	private Map<User, JFGConnection> userMap;
	private Map<JFGConnection, User> connectionMap;
	
	private Map<Integer, BoardKeeper> loadedMaps;
	
	private List<User> allUsers;
	
	private MessageDigest md5;
	
	private ServerPingManager pingManager;
	
	private JFGLogger serverLogger;
	
	public BunkersAndBadassesServer(int port) {
		super(port);
		//create a logger for the server
		try {
			serverLogger = new JFGLogger("bunkers_and_badasses_server_log", 100);
			JFGLoggerManager.addLogger(serverLogger);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		userMap = new HashMap<User, JFGConnection>();
		connectionMap = new HashMap<JFGConnection, User>();
		loadedMaps = new HashMap<Integer, BoardKeeper>();
		try {
			md5 = MessageDigest.getInstance("md5");
		}
		catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		loadUsers();
		pingManager = new ServerPingManager(this);
		setGroupFactory(new BunkersAndBadassesConnectionGroup());
	}
	
	/**
	 * Accept the login of a connection (implemented in the super class JFGLoginServer) and ask the new user for his name.
	 * 
	 * @param connection
	 * 		The new connection that is accepted
	 */
	@Override
	public void acceptLogin(JFGConnection connection) {
		super.acceptLogin(connection);
		connection.sendMessage(new ServerNameRequest());
	}
	
	/**
	 * Map the connection of a user to his name to identify the user by his name and the JFGConnection.
	 * 
	 * @param username
	 * 		The players name
	 * 
	 * @param connection
	 * 		The players connection
	 */
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
		//inform the ping manager
		pingManager.addUser(user);
		//send updates to the users
		sendUserUpdate();
		//start the secure connection after the user update was sent
		//startSecureConnection(connection, user);
		serverLogger.addLog("accepted login and maped connection (user: " + username + ");");
	}
	
	/**
	 * Logout a user, close his connection and send an updated user list to all remaining users.
	 * 
	 * @param connection
	 * 		The closed connection.
	 */
	public void logout(JFGConnection connection) {
		User user = connectionMap.get(connection);
		user.setInGame(false);
		user.setOnline(false);
		//inform the ping manager
		pingManager.removeUser(connectionMap.get(connection));
		//remove the user from the connection maps
		connectionMap.remove(connection);
		userMap.remove(user);
		//close the connection
		closeConnection(connection);
		//send an update to the clients
		sendUserUpdate();
		serverLogger.addLog("logged out user (user: " + user + ");");
	}

	/**
	 * Start the secure connection protocol by changing the connection object to a SecureMessageConnection.
	 *  
	 * @param connection
	 * 		The current used connection.
	 * 
	 * @param user
	 * 		The user to map the new connection and user.
	 */
	/*private void startSecureConnection(JFGConnection connection, User user) {
		JFGSecureMessageConnection secureConnection = new JFGSecureMessageConnection(connection);
		userMap.remove(user);
		connectionMap.remove(connection);
		userMap.put(user, secureConnection);
		connectionMap.put(secureConnection, user);
	}*/
	
	/**
	 * Inform the players about a game creation request to which the player is invited.
	 * 
	 * @param message
	 * 		The inviting message.
	 */
	public void sendGameCreationRequest(MainMenuMessage message) {
		List<JFGConnection> invitedConnections = new ArrayList<JFGConnection>();
		StringBuilder invitedUsers = new StringBuilder(); 
		for (User user : message.getInvitedPlayers()) {
			invitedConnections.add(userMap.get(user));
			invitedUsers.append(user.getUsername());
			invitedUsers.append(", ");
		}
		invitedUsers.setLength(invitedUsers.length()-2);
		for (JFGConnection con : invitedConnections) {
			//con.resetOutput();
			con.sendMessage(message);
		}
		String log;
		if (message.getMessageType() == MainMenuMessage.MessageType.GAME_CREATION_REQUEST) {
			log = "sending game creation request ";
		}
		else {
			log = "sending game creation abort ";
		}
		log += "(from player: " + message.getPlayer() + "; invited players: [" + invitedUsers.toString() + "]);";
		serverLogger.addLog(log);
	}
	/**
	 * Send an answer to a game creating request to the player that invited this player.
	 * 
	 * @param message
	 * 		The answer on the game request.
	 */
	public void sendGameCreationAnswer(MainMenuMessage message) {
		JFGConnection toPlayer = userMap.get(message.getToPlayer());
		//toPlayer.resetOutput();
		toPlayer.sendMessage(message);
		serverLogger.addLog("sending game creation answer (from player: " + message.getPlayer() + "; to player: " + message.getToPlayer() + "; joining: " + message.isJoining() + ");");
	}
	/**
	 * Inform the players about the abortion of the game creation.
	 * 
	 * @param message
	 * 		The message sent.
	 */
	public void sendGameCreationAbort(MainMenuMessage message) {
		//same as send request; just the message sent is different...
		sendGameCreationRequest(message);
	}

	/**
	 * Update a user's password and send an answer to the client that sent the request.
	 * Before the user is updated first the password is checked to verify the user.
	 *   
	 * @param message
	 * 		The message including the new user data
	 * 
	 * @param conn
	 * 		The connection from which the request came and to which the answer is sent
	 */
	public void updatePassword(MainMenuMessage message, JFGConnection conn) {
		Connection connection = JFGDatabaseConnection.getJFGDefaultConnection();
		boolean result = false;
		String password = getPasswordHash(message.getPassword());
		String sql = "UPDATE bunkers_and_badasses." + loginTable + " SET passwd = '"  + password + "' WHERE username = '" + message.getLastUsername() + "'";
		try (Statement statement = connection.createStatement()) {
			result = statement.execute(sql);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				connection.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		MainMenuMessage updateAnswer = new MainMenuMessage();
		updateAnswer.setMessageType(MainMenuMessage.MessageType.PASSWORD_USERNAME_UPDATE_ANSWER);
		updateAnswer.setUpdateSuccessfull(result);
		conn.sendMessage(updateAnswer);
		serverLogger.addLog("updated password (player: " + message.getLastUsername() + ");");
	}
	/**
	 * Update a user's name and send an answer to the client that sent the request.
	 * Before the user is updated first the password is checked to verify the user.
	 * If the changes were successful an update of the user list is sent to all players.
	 *   
	 * @param message
	 * 		The message including the new user data
	 * 
	 * @param conn
	 * 		The connection from which the request came and to which the answer is sent
	 */
	public void updateUsername(MainMenuMessage message, JFGConnection conn) {
		Connection connection = JFGDatabaseConnection.getJFGDefaultConnection();
		boolean result = false;
		String sql = "UPDATE bunkers_and_badasses." + loginTable + " SET username = '"  + message.getUsername() + "' WHERE username = '" + message.getLastUsername() + "'";
		try (Statement statement = connection.createStatement()) {
			result = statement.execute(sql);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				connection.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
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
		serverLogger.addLog("updated username (name: " + message.getLastUsername() + "; to name: " + message.getUsername() + ");");
	}
	/**
	 * Update a user's name or password and send an answer to the client that sent the request.
	 * Before the user is updated first the password is checked to verify the user.
	 * If the changes were successful an update of the user list is sent to all players.
	 *   
	 * @param message
	 * 		The message including the new user data
	 * 
	 * @param conn
	 * 		The connection from which the request came and to which the answer is sent
	 */
	public void updateUser(MainMenuMessage message, JFGConnection conn) {
		Connection connection = JFGDatabaseConnection.getJFGDefaultConnection();
		boolean result = false;
		String password = getPasswordHash(message.getPassword());
		String sql = "UPDATE bunkers_and_badasses." + loginTable + " SET passwd = '"  + password + "' WHERE username = '" + message.getLastUsername() + "'";
		String sql2 = "UPDATE bunkers_and_badasses." + loginTable + " SET username = '"  + message.getUsername() + "' WHERE username = '" + message.getLastUsername() + "'";
		try (Statement statement = connection.createStatement()) {
			result = !statement.execute(sql) && !statement.execute(sql2);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				connection.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
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
		serverLogger.addLog("updated password and username (name: " + message.getLastUsername() + "; to name: " + message.getUsername() + ");");
	}
	/**
	 * Get a password hash from an encrypted password.
	 * The password is first decrypted and then the hash is created.
	 * 
	 * @param password
	 * 		The encrypted password String
	 * 
	 * @return
	 * 		The password hash (md5)
	 */
	private String getPasswordHash(String password) {
		String pass = Cryptographer.decryptText(password, "cG4d2DP1MQlyonezuv71Z03");
		return getHash(pass);
	}
	/**
	 * Generate an MD5 hash for a password.
	 * 
	 * @param password
	 * 		The password String
	 * 
	 * @return
	 * 		The password hash (md5)
	 */
	private String getHash(String password) {
		byte[] inBuff = password.getBytes();
		byte[] outBuff = md5.digest(inBuff);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < outBuff.length; i++) {
			sb.append(Integer.toString((outBuff[i]&0xff)+0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	/**
	 * Add a new user and his JFGConnection to the maps and send an update to all users logged in.
	 * 
	 * @param connection
	 * 		The new JFGConnection
	 * 
	 * @param username
	 * 		The name of the new user
	 */
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
		serverLogger.addLog("updated user list (player added: " + username + ");");
	}
	/**
	 * Send an updated user list to all users logged in.
	 */
	private void sendUserUpdate() {
		UserUpdateMessage update = new UserUpdateMessage(allUsers);
		for (JFGConnection con : getConnections()) {
			if (isLoggedIn(con)) {
				con.resetOutput();
				con.sendMessage(update);
			}
		}
		serverLogger.addLog("sending user list update to all users;");
	}
	
	/**
	 * Load all users from the database after the server was started.
	 */
	private void loadUsers() {
		allUsers = new ArrayList<User>();
		Connection connection = JFGDatabaseConnection.getJFGDefaultConnection();
		Statement statement = null;
		ResultSet result = null;
		StringBuilder userList = new StringBuilder();
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT username FROM bunkers_and_badasses." + loginTable + "");
			while (result.next()) {
				String username = result.getString(1);
				allUsers.add(new User(username));
				userList.append(username);
				userList.append(", ");
			}
			userList.setLength(userList.length()-2);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			if (result != null) {
				try {
					result.close();
				}
				catch (SQLException e1) {
					;
				}				
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
		serverLogger.addLog("users loaded from database (users: [" + userList.toString() + "]);");
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
	 * 		The client connection that sent the request.
	 */
	public void loadMap(int id, int players, JFGConnection connection) {
		/*BoardKeeper keeper = loadedMaps.get(id);
		synchronized (this) {//wait if the map is currently loaded
			if (keeper == null) {
				BoardLoader loader = new BoardLoader();
				Board board = loader.loadBoard(id);//get the path from the database and load the file
				board.setBoardId(id);
				keeper = new BoardKeeper(board, this, id, players);
				loadedMaps.put(id, keeper);//store the loaded map for the next players
			}
		}
		//keeper.getBoard().setStoreImage(true);
		BoardTransfereMessage transfere = new BoardTransfereMessage(keeper.getBoard());*/
		BoardLoader loader = new BoardLoader();
		Board board = loader.loadBoard(id);
		board.setBoardId(id);
		//don't send the image; client has already got one he can use
		//board.setStoreImage(true);
		//send the board to the client
		BoardTransfereMessage message = new BoardTransfereMessage(board);
		connection.sendMessage(message);
		serverLogger.addLog("map loaded (map id: " + id + "; player: " + connectionMap.get(connection).getUsername() + "; players in game: " + players + ");");
	}
	/**
	 * Load an overview of all maps (including the name, the image and the player number) and send it to the client.
	 * 
	 * @param message
	 * 		The message that is sent back to the client
	 * 
	 * @param connection
	 * 		The client connection that sent the request.
	 */
	public void sendMapOverviews(BoardOverviewRequestMessage message, JFGConnection connection) {
		List<Integer> availableBoardIds = new ArrayList<Integer>();
		//get the available ids from the database 
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		ResultSet result = null;
		String query = "SELECT id FROM bunkers_and_badasses.maps";
		try (Statement statement = con.createStatement()) {
			result = statement.executeQuery(query);//get the ids
			while (result.next()) {
				availableBoardIds.add(result.getInt(1));//store all ids in a list
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			//close resources
			if (result != null) {
				try {
					result.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				con.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		//load all boards from files
		BoardLoader loader = new BoardLoader();
		List<Board> boards = new ArrayList<Board>();
		for (int id : availableBoardIds) {
			Board b = loader.loadBoard(id);
			//delete the fields and regions form the board
			b.setFields(null);
			b.setRegions(null);
			b.setBoardId(id);
			b.setStoreImage(true);
			boards.add(b);
		}
		//send the board overviews to the client
		message.setBoards(boards);
		connection.sendMessage(message);
		serverLogger.addLog("sending map overviews (player: " + connectionMap.get(connection).getUsername() + ");");
	}
	
	public void completeBoards(BoardOverviewRequestMessage message, JFGConnection connection) {
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		ResultSet result = null;
		String query = "SELECT * FROM bunkers_and_badasses.maps";
		try (Statement statement = con.createStatement()) {
			for (Board board : message.getBoards()) {
				result = statement.executeQuery(query + " WHERE name = '" + board.getName() + "'");
				if (result.next()) {
					board.setBoardId(result.getInt(1));
					board.setPlayersMin(result.getInt(3));
					board.setPlayersMax(result.getInt(4));
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			//close resources
			if (result != null) {
				try {
					result.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				con.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		connection.sendMessage(message);
	}
	
	/**
	 * Save a game in the database.
	 * First an overview of the game is created. Then the game is updated in the database.
	 * An existing game with the same id will be overwritten.
	 * If the game has ended the statistics of this game are also stored in the database.
	 * 
	 * @param game
	 * 		The game that is to be saved
	 * 
	 * @param gameEnded
	 * 		Indicates whether the game has ended to know if the statistics need to be created
	 */
	public boolean saveGame(Game game, boolean gameEnded) {
		boolean saveSuccessful = true;
		GameOverview overview = new GameOverview(game);
		Connection connection = JFGDatabaseConnection.getJFGDefaultConnection();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("UPDATE bunkers_and_badasses.games SET active = ?, game_overview = ?, game_data = ? WHERE id = ?");
			statement.setBoolean(1, !gameEnded);
			statement.setObject(2, overview);//add the overview object to the prepared statement
			statement.setObject(3, game);//add the game object to the prepared statement
			statement.setInt(4, game.getId());
			statement.execute();
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
			saveSuccessful = false;
		}
		if (gameEnded) {
			//create the statistics if the game has ended
			saveSuccessful &= createGameStatistics(game);
		}
		serverLogger.addLog("saved game (game id: " + game.getId() + "; ended: " + gameEnded + "; save successful: " + saveSuccessful + ");");
		return saveSuccessful;
	}
	/**
	 * Create the statistics of a game and store them in the database.
	 * 
	 * @param game
	 * 		The game that has ended and is added to the statistics
	 * 
	 * @return
	 * 		true if the statistics can be created and send to the database. false otherwise. 
	 */
	private boolean createGameStatistics(Game game) {
		boolean statisticsSuccessful = true;
		String cause = null;//the cause when the statistics are not created successfully
		//add the game id to the statistics
		GameStatisticManager statistics = game.getStatisticManager();
		statistics.getStatistics().forEach((user, stats) -> stats.setGame_id(game.getId()));
		//to add the user id's a database connection is needed
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		ResultSet result = null;
		try (Statement statement = con.createStatement()) {
			for (User user : statistics.getStatistics().keySet()) {
				//load the user ids from the database
				result = statement.executeQuery("SELECT id from bunkers_and_badasses.users WHERE username = '" + user + "'");
				if (result.next()) {
					statistics.getStatistics(user).setUser_id(result.getInt(1));
				}
				else {
					System.err.println("ERROR: user id of user [" + user + "] not found");
					statisticsSuccessful = false;
					cause = "user id not found";
				}
			}
		} 
		catch (SQLException sqle) {
			sqle.printStackTrace();
			statisticsSuccessful = false;
			cause = "exception in statement 1";
		}
		finally {
			if (result != null) {
				try {
					result.close();
				}
				catch (SQLException sqle) {
					sqle.printStackTrace();
					statisticsSuccessful = false;
					cause = "result can't be closed";
				}
			}
		}
		//add the statistics to the database
		String query = "INSERT INTO bunkers_and_badasses.statistics(`user_id`, `game_id`, `position`, `players`, `points`, `points_fight`, "
				+ "`points_fields`, `points_regions`, `points_goals`, `points_bonuses`, `points_skills`, `troops_killed_normal`, "
				+ "`troops_killed_badass`, `troops_killed_neutral`, `troops_controlled_end`, `troops_controlled_max`, `fields_end`, "
				+ "`fields_max`, `regions_end`, `region_value_end`, `regions_max`, `region_value_max`, `battles_won`, `battles_lost`, "
				+ "`heros_used_battle`, `heros_used_effect`, `used_credits`, `used_ammo`, `used_eridium`, `buildings_created`, "
				+ "`buildings_upgraded`, `buildings_destroyed`, `support_given_self`, `support_received_self`, `support_given_other`, "
				+ "`support_received_other`, `support_rejected`, `commands_raid`, `commands_retreat`, `commands_march`, `commands_build`, "
				+ "`commands_recruit`, `commands_resources`, `commands_support`, `commands_defense`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = con.prepareStatement(query)) {
			for (User user : statistics.getStatistics().keySet()) {
				statistics.getStatistics(user).prepareStatement(statement);
				statement.execute();
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
			statisticsSuccessful = false;
			cause = "exception in statement 2";
		}
		finally {
			try {
				con.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
				sqle.printStackTrace();
				statisticsSuccessful = false;
				cause = "connection can't be closed";
			}
		}
		serverLogger.addLog("added statistics to the database (" + (statisticsSuccessful ? "successful" : "NOT successful: " + cause) + ")");
		return statisticsSuccessful;
	}
	
	/**
	 * Load all statistics from the database and send them back to the user that asked for them.
	 */
	public void loadStatistics(GameStatisticsRequestMessage message, JFGConnection connection) {
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		ResultSet result = null;
		String query = "SELECT s.*, u.username, m.map_id, map.name FROM bunkers_and_badasses.statistics s "
				+ "JOIN bunkers_and_badasses.users u ON s.user_id = u.id "
				+ "JOIN bunkers_and_badasses.game_maps m ON m.game_id = s.game_id "
				+ "JOIN bunkers_and_badasses.maps map ON m.map_id = map.id";
		List<GameStatistic> statistics = new ArrayList<GameStatistic>();
		try (Statement statement = con.createStatement()) {
			result = statement.executeQuery(query);
			while (result.next()) {
				GameStatistic stats = new GameStatistic(result);
				statistics.add(stats);
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			if (result != null) {
				try {
					result.close();
				}
				catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
			try {
				con.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		message.setStatistics(statistics);
		connection.resetOutput();
		connection.sendMessage(message);
		serverLogger.addLog("sending game statistics (player: " + connectionMap.get(connection).getUsername() + ");");
	}
	
	/**
	 * Send all game overview objects that can be found in the database to the client that is identified by his connection.
	 * All Overviews are sent to the client. The selection of the games in which the player took part is implemented on the client side.
	 * 
	 * @param connection
	 * 		The connection that sent the request for the game overviews.
	 */
	public void sendGameOverviews(GameOverviewRequestMessage message, JFGConnection connection) {
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		List<GameOverview> gameOverviews = new ArrayList<GameOverview>();
		ResultSet result = null;
		String query = "SELECT id, game_overview FROM bunkers_and_badasses.games WHERE active = true && game_overview IS NOT NULL";
		GameOverview overview;
		int id;
		
		try (Statement statement = con.createStatement()) {
			//get the overviews from the database and store them in a list
			result = statement.executeQuery(query);
			while (result.next()) {
				id = result.getInt(1);
				byte[] in = (byte[]) result.getObject(2);
				ByteArrayInputStream baip = new ByteArrayInputStream(in);
				ObjectInputStream ois = new ObjectInputStream(baip);
				overview = (GameOverview) ois.readObject();
				overview.setId(id);//add the id to the overview to identify the game save
				gameOverviews.add(overview);
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		finally {
			if (result != null) {
				try {
					result.close();
				}
				catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
			try {
				con.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		//add the overviews to the message and send it back to the client 
		message.setGameOverviews(gameOverviews);
		connection.sendMessage(message);
		serverLogger.addLog("sending game overviews (player: " + connectionMap.get(connection).getUsername() + ");");
	}
	
	/**
	 * Load a game from the database and send it to a client.
	 * 
	 * @param id
	 * 		The id in the database that identifies the game that is to be loaded.
	 * 
	 * @param connection
	 * 		The connection that sent the request.
	 */
	public Game loadGame(GameLoadRequestMessage message, JFGConnection connection) {
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		ResultSet result = null;
		int id = message.getOverview().getId();
		String query = "SELECT game_data FROM bunkers_and_badasses.games WHERE id = " + id;
		Game loadedGame = null;
		try (Statement statement = con.createStatement()) {
			result = statement.executeQuery(query);//load the game from the database
			if (result.next()) {
				byte[] in = (byte[]) result.getObject(1);
				ByteArrayInputStream baip = new ByteArrayInputStream(in);
				ObjectInputStream ois = new ObjectInputStream(baip);
				loadedGame = (Game) ois.readObject();
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		finally {
			if (result != null) {
				try {
					result.close();
				}
				catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
			try {
				con.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		//add the game to the message and send it back to the client
		//the board image is loaded separately by the GameStartDialog via a BoardRequestMessage
		message.setLoadedGame(loadedGame);
		message.setLoadedSuccessful(loadedGame != null);
		connection.sendMessage(message);
		serverLogger.addLog("loaded game from database (player: " + connectionMap.get(connection).getUsername() + "; game id: " + message.getOverview().getId() + ");");
		return loadedGame;
	}
	
	/**
	 * Send game start messages to all invited players.
	 * 
	 * @param message
	 * 		The message to be sent.
	 */
	public void sendGameStartMessage(GameStartMessage message) {
		List<User> players = message.getPlayers();
		StringBuilder playerList = new StringBuilder();
		for (int i = 0; i < players.size(); i++) {
			JFGConnection connection = userMap.get(players.get(i));
			connection.resetOutput();
			connection.sendMessage(message);
			playerList.append(players.get(i).getUsername());
			playerList.append(", ");
		}
		playerList.setLength(playerList.length()-2);
		serverLogger.addLog("sending game start message (to players: [" + playerList.toString() + "]);");
	}
	
	/**
	 * Create a group for the players of the game.
	 * 
	 * @param message
	 * 		The message that starts the game and contains all the players.
	 */
	public void createGameGroup(GameStartMessage message) {
		List<JFGConnection> connections = new ArrayList<JFGConnection>();
		StringBuilder playerList = new StringBuilder();
		for (User player : message.getPlayers()) {
			connections.add(userMap.get(player));
			playerList.append(player.getUsername());
			playerList.append(", ");
		}
		playerList.setLength(playerList.length()-2);
		BunkersAndBadassesConnectionGroup group = (BunkersAndBadassesConnectionGroup) createGroup(connections);
		for (JFGConnection connection : connections) {
			connection.setGroup(group);
		}
		group.setStartingPlayer(message.getStartingPlayer());
		group.setStartingPlayerConnection(userMap.get(message.getStartingPlayer()));			
		serverLogger.addLog("created connection group (players: [" + playerList.toString() + "]; starting player: " + message.getStartingPlayer().getUsername() + ");");
	}
	
	/**
	 * Create a new game in the database and send the game's id to the players.
	 * 
	 * @param message
	 * 		The creation message.
	 */
	public void createGame(GameCreationMessage message) {
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		ResultSet result = null;
		String query = "SELECT MAX(id) FROM bunkers_and_badasses.games";//find the maximum id of a game
		int gameId = 0;
		try (Statement statement = con.createStatement()) {
			result = statement.executeQuery(query);
			if (result.next()) {
				gameId = result.getInt(1);
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
			System.err.println("Select maximum game id failed");
		}
		finally {
			if (result != null) {
				try {
					result.close();
				}
				catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
			//don't close the connection; needed later
		}
		gameId += 1;//use the next free id for the game
		query = "SELECT id FROM bunkers_and_badasses.users WHERE username = '";
		int[] userIds = new int[message.getPlayers().size()];
		try (Statement statement = con.createStatement()) {
			for (int i = 0; i < message.getPlayers().size(); i++) {
				//get the user's ids
				result = statement.executeQuery(query + message.getPlayers().get(i).getUsername() + "'");
				if (result.next()) {
					userIds[i] = result.getInt(1);
				}
				try {
					result.close();
				}
				catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
			System.err.println("Select user id's failed");
		}
		//create the tables
		//String[] sql = new String[message.getPlayers().size()+2];
		String[] sql = new String[2];
		sql[0] = "INSERT INTO bunkers_and_badasses.games (id, active) VALUES (" + gameId + ", true)";//create the game
		sql[1] = "INSERT INTO bunkers_and_badasses.game_maps (game_id, map_id) VALUES (" + gameId + ", " + message.getBoardId() + ")";//create a game - map
		//statistics are created when the game ends
		/*for (int i = 0; i < message.getPlayers().size(); i++) {
			sql[2+i] = "INSERT INTO bunkers_and_badasses.statistics (user_id, game_id) VALUES (" + userIds[i] + ", " + gameId + ")";
		}*/
		try (Statement statement = con.createStatement()) {
			for (String s : sql) {
				statement.execute(s);
				/*if (!statement.execute(s)) {
					throw new SQLException();
				}*/
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
			System.err.println("Insert into game, game_maps or statistics failed");
		}
		finally {
			try {
				con.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		//send the game id to the clients to start the game
		GameStartMessage startMessage = new GameStartMessage();
		startMessage.setGameId(gameId);
		StringBuilder players = new StringBuilder();
		for (int i = 0; i < message.getPlayers().size(); i++) {
			JFGConnection connection = userMap.get(message.getPlayers().get(i));
			//System.out.println("sending game start to player: " + message.getPlayers().get(i).getUsername());
			//System.out.println(System.currentTimeMillis());
			connection.sendMessage(startMessage);
			players.append(message.getPlayers().get(i));
			players.append(", ");
		}
		players.setLength(players.length()-2);
		serverLogger.addLog("started game (game id: " + gameId + "; players: [" + players.toString() + "]; board id: " + startMessage.getBoardId() + ");");
	}
	
	/**
	 * Receive a ping from a user.
	 * 
	 * @param connection
	 * 		The users connection to identify the user.
	 */
	public void receivePing(JFGConnection connection) {
		pingManager.ping(connectionMap.get(connection));
	}
	
	/**
	 * Add the users resources depending on the users skill profiles.
	 * 
	 * @param game
	 * 		The game containing the users.
	 */
	/*public void addResources(Game game) {
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		ResultSet result = null;
		Map<User, UserResource> userResources = new HashMap<User, UserResource>();
		for (User user : game.getPlayers()) {
			String username = "__global_user_skill__";//In the first version there is only one global user skill profile
			String query = "SELECT * FROM bunkers_and_badasses.skills WHERE name = " + username;
			try (Statement statement = con.createStatement()) {
				result = statement.executeQuery(query);//load the skill profile
				if (result.next()) {
					UserResource resource = new UserResource();
					resource.setEridium(SkillProfileManager.ERIDIUM_SKILL_LEVEL[result.getInt(4)]);
					resource.setCredits(SkillProfileManager.CREDITS_SKILL_LEVEL[result.getInt(5)]);
					resource.setAmmo(SkillProfileManager.AMMO_SKILL_LEVEL[result.getInt(6)]);
					resource.setEridiumBuilding(SkillProfileManager.ERIDIUM_BUILDING_SKILL_LEVEL[result.getInt(7)]);
					resource.setCreditsBuilding(SkillProfileManager.CREDITS_BUILDING_SKILL_LEVEL[result.getInt(8)]);
					resource.setAmmoBuilding(SkillProfileManager.AMMO_BUILDING_SKILL_LEVEL[result.getInt(9)]);
					userResources.put(user, resource);
					game.getPointManager().addPoints(user, SkillProfileManager.POINTS_SKILL_LEVEL[result.getInt(3)]);
					game.getHeroCardManager().takeCards(user, SkillProfileManager.HEROS_SKILL_LEVEL[result.getInt(10)]);
				}
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			finally {
				if (result != null) {
					try {
						result.close();
					}
					catch (SQLException sqle) {
						sqle.printStackTrace();
					}
				}
			}			
		}
		try {
			con.close();
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		//the message is sent back from the interpreter
	}*/
	/**
	 * Send the game transfer message to all players joining the game.
	 * 
	 * @param message
	 * 		The message containing the players.
	 */
	public void sendGameTransferMessage(GameTransferMessage message) {
		StringBuilder players = new StringBuilder();
		for (User user : message.getGame().getPlayers()) {
			userMap.get(user).sendMessage(message);
			players.append(user.getUsername());
			players.append(", ");
		}
		players.setLength(players.length()-2);
		serverLogger.addLog("sending game transfere message (transfere type: " + message.getType().toString() + "; game id: " + message.getGame().getId() + "; players: [" + players.toString() + "]);");
	}
	
	/**
	 * Load the skill profiles for a user and send them to the client.
	 * 
	 * @param message
	 * 		The transfer message from the client (also send back to the client).
	 * 
	 * @param connection
	 * 		The connection that sent the request.
	 */
	public void loadSkillProfiles(SkillProfileTransferMessage message, JFGConnection connection) {
		String username = connectionMap.get(connection).getUsername();
		SkillProfile[] skillProfiles = loadSkillProfiles(username);
		//send the profiles back to the client
		message.setProfiles(skillProfiles);
		connection.sendMessage(message);
		serverLogger.addLog("loaded skill profiles (player: " + connectionMap.get(connection).getUsername() + ");");
	}
	private SkillProfile[] loadSkillProfiles(String username) {
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		ResultSet result = null;
		String query = "SELECT * FROM bunkers_and_badasses.skills WHERE name = '" + username + "'";
		SkillProfile[] skillProfiles = new SkillProfile[5];//every user has 5 skill profiles
		try (Statement statement = con.createStatement()) {
			result = statement.executeQuery(query);//load the skill profile
			int index = 0;
			while (result.next() && index < skillProfiles.length) {
				SkillProfile profile = new SkillProfile();
				profile.setEridium(result.getInt(4));
				profile.setCredits(result.getInt(5));
				profile.setAmmo(result.getInt(6));
				profile.setEridiumBuilding(result.getInt(7));
				profile.setCreditsBuilding(result.getInt(8));
				profile.setAmmoBuilding(result.getInt(9));
				profile.setHero(result.getInt(10));
				profile.setPoints(result.getInt(3));
				profile.setId(result.getInt(1));
				skillProfiles[index] = profile;
				index++;
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			if (result != null) {
				try {
					result.close();
				}
				catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
			try {
				con.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return skillProfiles;
	}
	/**
	 * Update a user's skill profile in the database.
	 *   
	 * @param message
	 * 		The update message containing the new skill profile.
	 */
	public void updateSkillProfile(SkillProfileTransferMessage message) {
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		SkillProfile update = message.getUpdate();
		String query = "UPDATE bunkers_and_badasses.skills SET points = " + update.getPoints() + ",  eridium = " + update.getEridium() + 
				", credits = " + update.getCredits() + ", ammo = " + update.getAmmo() + ", eridium_building = " + update.getEridiumBuilding() + 
				", credits_building = " + update.getCreditsBuilding() + ", ammo_building = " + update.getAmmoBuilding() + ", heroes = " + update.getHero() + 
				" WHERE id = " + update.getId();
		try (Statement statement = con.createStatement()) {
			/*if (!statement.execute(query)) {
				throw new SQLException();
			}*/
			//execute returns false but the update is successful...
			statement.execute(query);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				con.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		serverLogger.addLog("updated skill profiles (skill id: " + message.getUpdate().getId() + ");");
	}
	/**
	 * Generate the default skill profiles for a new user.
	 * 
	 * @param message
	 * 		The message that was sent to sign up the user.
	 */
	public void generateUserSkills(JFGDatabaseLoginMessage message) {
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		SkillProfile defaultProfile = loadSkillProfiles("__global_user_skill__")[0];
		String query = "INSERT INTO bunkers_and_badasses.skills (id, name, points, eridium, credits, ammo, eridium_building, credits_building, ammo_building, heroes) VALUES (0, '" + 
				message.getUsername() + "', " + defaultProfile.getPoints() + ", " + defaultProfile.getEridium() + ", " + defaultProfile.getCredits() + ", " + defaultProfile.getAmmo() + 
				", " + defaultProfile.getEridiumBuilding() + ", " + defaultProfile.getCreditsBuilding() + ", " + defaultProfile.getAmmoBuilding() + ", " + defaultProfile.getHero() + ");";
		try (Statement statement = con.createStatement()) {
			for (int i = 0; i < 5; i++) {//insert 5 default skill profiles
				//System.out.println("Executing query: " + query);
				statement.execute(query);
				/*if (!statement.execute(query)) {
					throw new SQLException();
				}*/
			}
			//con.commit();
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				con.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		serverLogger.addLog("generated user skills (player: " + message.getUsername() + ");");
	}
	
	/**
	 * Load the dynamic variables from the database and send them back to the client.
	 * 
	 * @param message
	 * 		The request message that is sent back to the client.
	 * 
	 * @param connection
	 * 		The connection that sent the request.
	 */
	public void loadDynamicVariables(DynamicVariableRequestMessage message, JFGConnection connection) {
		GameVariableStorage gameStorage = new GameVariableStorage();
		BuildingStorage buildingStorage = new BuildingStorage();
		TroopStorage troopStorage = new TroopStorage();
		CommandStorage commandStorage = new CommandStorage();
		TurnBonusStorage turnBonusStorage = new TurnBonusStorage();
		List<HelpContent> helpContents = new ArrayList<HelpContent>();
		String gameVariablesQuery = "SELECT * FROM bunkers_and_badasses.game_variables WHERE used = true";
		String skillResourcesQuery = "SELECT * FROM bunkers_and_badasses.skill_resources";
		String startResourcesQuery = "SELECT * FROM bunkers_and_badasses.start_resources WHERE used = true";
		String buildingQuery = "SELECT * FROM bunkers_and_badasses.building_variables";
		String buildingCostQuery = "SELECT * FROM bunkers_and_badasses.costs_building";
		String troopCostQuery = "SELECT * FROM bunkers_and_badasses.costs_troop";
		String commandCostQuery = "SELECT * FROM bunkers_and_badasses.costs_command";
		String commandQuery = "SELECT * FROM bunkers_and_badasses.commands WHERE used = true";
		String bonusQuery = "SELECT * FROM bunkers_and_badasses.turn_bonus_resources";
		String gamePointQuery = "SELECT * FROM bunkers_and_badasses.game_points WHERE used = true";
		String helpContentQuery = "SELECT * FROM bunkers_and_badasses.help_content ORDER BY id";
		ResultSet result;
		//load the variables from the database
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		try (Statement statement = con.createStatement()) {
			//game variables (skill points, turns, hero cards, start troops)
			result = statement.executeQuery(gameVariablesQuery);
			if (result.next()) {
				gameStorage.setSkillPoints(result.getInt(3));
				gameStorage.setGameTurns(result.getInt(4));
				gameStorage.setMaxHerosCards(result.getInt(5));
				gameStorage.setStartTroops(result.getInt(6));
				gameStorage.setHeroCardCosts(result.getInt(7));
				gameStorage.setAdditionalCommandCosts(result.getInt(8));
				gameStorage.setFieldCosts(result.getInt(9));
			}
			try {
				result.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			//System.out.println("game variables ready");
			//the additional resources you get from skills
			result = statement.executeQuery(skillResourcesQuery);
			while (result.next()) {
				gameStorage.getCreditsSkillLevel()[result.getInt(1)] = result.getInt(2);
				gameStorage.getCreditsBuildingSkillLevel()[result.getInt(1)] = result.getInt(3);
				gameStorage.getAmmoSkillLevel()[result.getInt(1)] = result.getInt(4);
				gameStorage.getAmmoBuildingSkillLevel()[result.getInt(1)] = result.getInt(5);
				gameStorage.getEridiumSkillLevel()[result.getInt(1)] = result.getInt(6);
				gameStorage.getEridiumBuildingSkillLevel()[result.getInt(1)] = result.getInt(7);
				gameStorage.getHerosSkillLevel()[result.getInt(1)] = result.getInt(8);
				gameStorage.getPointsSkillLevel()[result.getInt(1)] = result.getInt(9);
			}
			try {
				result.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			//System.out.println("skill resources ready");
			//the start resources of every player
			result = statement.executeQuery(startResourcesQuery);
			if (result.next()) {
				gameStorage.setStartCredits(result.getInt(3));
				gameStorage.setStartAmmo(result.getInt(4));
				gameStorage.setStartEridium(result.getInt(5));
				gameStorage.setTurnStartCredits(result.getInt(6));
				gameStorage.setTurnStartAmmo(result.getInt(7));
				gameStorage.setTurnStartEridium(result.getInt(8));
			}
			try {
				result.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			//System.out.println("start resources ready");
			//dynamic variables for every building (mining, land mines, defense, move distance, points)
			result = statement.executeQuery(buildingQuery);
			int[][] buildingVariables = buildingStorage.getBuildingVariables();
			while (result.next()) {
				buildingVariables[result.getInt(1)][BuildingStorage.RECRUITABLE_TROOPS] = result.getInt(2);
				buildingVariables[result.getInt(1)][BuildingStorage.MINING_CREDITS] = result.getInt(3);
				buildingVariables[result.getInt(1)][BuildingStorage.MINING_AMMO] = result.getInt(4);
				buildingVariables[result.getInt(1)][BuildingStorage.MINING_ERIDIUM] = result.getInt(5);
				buildingVariables[result.getInt(1)][BuildingStorage.LAND_MINE_VICTIMS] = result.getInt(6);
				buildingVariables[result.getInt(1)][BuildingStorage.ADDITIONAL_DEFENCE] = result.getInt(7);
				buildingVariables[result.getInt(1)][BuildingStorage.MOVE_DISTANCE] = result.getInt(8);
				buildingVariables[result.getInt(1)][BuildingStorage.POINTS] = result.getInt(9);
			}
			try {
				result.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			//System.out.println("buildings ready");
			//the costs for buildings
			result = statement.executeQuery(buildingCostQuery);
			int[][] buildingPrices = buildingStorage.getBuildingPrices();
			int[][] extensionPrices = buildingStorage.getBuildingExtensionPrices();
			int[] buildingBreakOffPrices = buildingStorage.getBuildingBreakOffPrices();
			while (result.next()) {
				if (result.getInt(1) == -1) {
					//-1 is the index for break off prices;
					buildingBreakOffPrices[0] = result.getInt(2);
					buildingBreakOffPrices[0] = result.getInt(3);
					buildingBreakOffPrices[0] = result.getInt(4);
				}
				else {
					buildingPrices[result.getInt(1)][BuildingStorage.PRICE_CREDITS] = result.getInt(2);
					buildingPrices[result.getInt(1)][BuildingStorage.PRICE_AMMO] = result.getInt(3);
					buildingPrices[result.getInt(1)][BuildingStorage.PRICE_ERIDIUM] = result.getInt(4);
					extensionPrices[result.getInt(1)][BuildingStorage.PRICE_CREDITS] = result.getInt(5);
					extensionPrices[result.getInt(1)][BuildingStorage.PRICE_AMMO] = result.getInt(6);
					extensionPrices[result.getInt(1)][BuildingStorage.PRICE_ERIDIUM] = result.getInt(7);					
				}
			}
			try {
				result.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			//System.out.println("building costs ready");
			//the costs for recruiting and moving troops
			result = statement.executeQuery(troopCostQuery);
			int[][] troopPrices = troopStorage.getTroopCosts();
			while (result.next()) {
				troopPrices[result.getInt(1)][TroopStorage.BASE_COSTS_CREDITS] = result.getInt(2);
				troopPrices[result.getInt(1)][TroopStorage.BASE_COSTS_AMMO] = result.getInt(3);
				troopPrices[result.getInt(1)][TroopStorage.RECRUIT_COSTS_CREDITS] = result.getInt(4);
				troopPrices[result.getInt(1)][TroopStorage.RECRUIT_COSTS_AMMO] = result.getInt(5);
				troopPrices[result.getInt(1)][TroopStorage.RECRUIT_COSTS_ERIDIUM] = result.getInt(6);
			}
			try {
				result.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			//System.out.println("troop costs ready");
			//the costs for commands
			result = statement.executeQuery(commandCostQuery);
			int[][] commandPrices = commandStorage.getCosts();
			boolean[][] commandDependencies = commandStorage.getDependencies();
			while (result.next()) {
				if (result.getInt(1) >= 0) {
					commandPrices[result.getInt(1)][CommandStorage.CREDITS] = result.getInt(2);
					commandPrices[result.getInt(1)][CommandStorage.AMMO] = result.getInt(3);
					commandDependencies[result.getInt(1)][CommandStorage.CREDITS] = result.getBoolean(5);
					commandDependencies[result.getInt(1)][CommandStorage.AMMO] = result.getBoolean(6);
				}
				else {
					//the resources a player gets from a collect command
					int[] collectResources = commandStorage.getResourceReception();
					collectResources[CommandStorage.CREDITS] = result.getInt(2);
					collectResources[CommandStorage.AMMO] = result.getInt(3);
					collectResources[CommandStorage.ERIDIUM] = result.getInt(4);
				}
			}
			try {
				result.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			//System.out.println("command costs ready");
			//the number of commands every player gets
			result = statement.executeQuery(commandQuery);
			int[] commands = gameStorage.getUserCommands();
			if (result.next()) {
				commands[UserPlanManager.COMMAND_RAID] = result.getInt(3);
				commands[UserPlanManager.COMMAND_RETREAT] = result.getInt(4);
				commands[UserPlanManager.COMMAND_MARCH] = result.getInt(5);
				commands[UserPlanManager.COMMAND_BUILD] = result.getInt(6);
				commands[UserPlanManager.COMMAND_RECRUIT] = result.getInt(7);
				commands[UserPlanManager.COMMAND_COLLECT] = result.getInt(8);
				commands[UserPlanManager.COMMAND_SUPPORT] = result.getInt(9);
				commands[UserPlanManager.COMMAND_DEFEND] = result.getInt(10);
			}
			try {
				result.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			//System.out.println("commands ready");
			//the bonus resources
			result = statement.executeQuery(bonusQuery);
			int[][] resources = turnBonusStorage.getResources();
			while (result.next()) {
				resources[result.getInt(1)][TurnBonusStorage.CREDITS] = result.getInt(2);
				resources[result.getInt(1)][TurnBonusStorage.AMMO] = result.getInt(3);
				resources[result.getInt(1)][TurnBonusStorage.ERIDIUM] = result.getInt(4);
			}
			try {
				result.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			//System.out.println("bonus ready");
			//the points for fights and fields
			result = statement.executeQuery(gamePointQuery);
			if (result.next()) {
				gameStorage.setFieldPoints(result.getInt(3));
				gameStorage.setFieldPointCount(result.getInt(4));
				gameStorage.setFightAttackerPoints(result.getInt(5));
				gameStorage.setFightWinnerPoints(result.getInt(6));
				gameStorage.setFightSupporterPoints(result.getInt(7));
				gameStorage.setFieldConquerPoints(result.getInt(8));
			}
			try {
				result.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			//System.out.println("game points ready");
			result = statement.executeQuery(helpContentQuery);
			while (result.next()) {
				HelpContent help = new HelpContent(result.getString(3), 
						result.getString(5),
						result.getString(6),
						result.getString(4),
						new int[] {result.getInt(7), result.getInt(8)},
						new Dimension(result.getInt(9), result.getInt(10)),
						result.getInt(1),
						result.getInt(2));
				helpContents.add(help);
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				con.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		//System.out.println("help content ready");
		//add the loaded variables and send back the message
		message.setGameStorage(gameStorage);
		message.setBuildingStorage(buildingStorage);
		message.setTroopStorage(troopStorage);
		message.setCommandStorage(commandStorage);
		message.setTurnBonusStorage(turnBonusStorage);
		message.setHelpContents(helpContents);
		connection.sendMessage(message);
		serverLogger.addLog("dynamic variables loaded and sent to player (player: " + connectionMap.get(connection).getUsername() + ");");
	}
	
	public Map<User, JFGConnection> getUserMap() {
		return userMap;
	}
	public Map<JFGConnection, User> getConnectionMap() {
		return connectionMap;
	}
	
	public List<User> getUsers() {
		return allUsers;
	}
	
	public Map<Integer, BoardKeeper> getLoadedMaps() {
		return loadedMaps;
	}
}