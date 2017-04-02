package net.jfabricationgames.bunkers_and_badasses.server;

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
import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;
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
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameTransferMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.SkillProfileTransferMessage;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameOverview;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusStorage;
import net.jfabricationgames.bunkers_and_badasses.main_menu.MainMenuMessage;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jdbc.JFGDatabaseConnection;
import net.jfabricationgames.jfgdatabaselogin.message.Cryptographer;
import net.jfabricationgames.jfgdatabaselogin.message.JFGDatabaseLoginMessage;
import net.jfabricationgames.jfgserver.server.JFGConnection;
import net.jfabricationgames.jfgserver.server.JFGConnectionGroup;
import net.jfabricationgames.jfgserver.server.JFGLoginServer;

public class BunkersAndBadassesServer extends JFGLoginServer {
	
	public static final String loginTable = "users";
	
	private Map<User, JFGConnection> userMap;
	private Map<JFGConnection, User> connectionMap;
	
	private Map<Integer, BoardKeeper> loadedMaps;
	
	private List<User> allUsers;
	
	private MessageDigest md5;
	
	private ServerPingManager pingManager;
	
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
	}
	
	/**
	 * Inform the players about a game creation request to which the player is invited.
	 *  
	 * @param message
	 * 		The inviting message.
	 */
	public void sendGameCreationRequest(MainMenuMessage message) {
		List<JFGConnection> invitedConnections = new ArrayList<JFGConnection>();
		for (User user : message.getInvitedPlayers()) {
			invitedConnections.add(userMap.get(user));
		}
		for (JFGConnection con : invitedConnections) {
			//con.resetOutput();
			con.sendMessage(message);
		}
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
	}
	/**
	 * Send an updated user list to all users logged in.
	 */
	private void sendUserUpdate() {
		UserUpdateMessage update = new UserUpdateMessage(allUsers);
		for (JFGConnection con : getConnections()) {
			if (isLoggedIn(con)) {
				//con.resetOutput();
				con.sendMessage(update);
			}
		}
	}
	
	/**
	 * Load all users from the database after the server was started.
	 */
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
		BoardKeeper keeper = loadedMaps.get(id);
		synchronized (this) {//wait if the map is currently loaded
			if (keeper == null) {
				BoardLoader loader = new BoardLoader();
				Board board = loader.loadBoard(id);//get the path from the database and load the file
				board.setBoardId(id);
				keeper = new BoardKeeper(board, this, id, players);
				loadedMaps.put(id, keeper);//store the loaded map for the next players
			}
		}
		BoardTransfereMessage transfere = new BoardTransfereMessage(keeper.getBoard());
		connection.sendMessage(transfere);
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
			if (result.next()) {
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
			boards.add(b);
		}
		//send the board overviews to the client
		message.setBoards(boards);
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
		boolean saveSuccessful = false;
		GameOverview overview = new GameOverview(game);
		Connection connection = JFGDatabaseConnection.getJFGDefaultConnection();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("UPDATE bunkers_and_badasses.games SET active = ?, game_overview = ?, game_data = ? WHERE id = ?");
			statement.setBoolean(1, !gameEnded);
			statement.setObject(2, overview);//add the overview object to the prepared statement
			statement.setObject(3, game);//add the game object to the prepared statement
			statement.setInt(4, game.getId());
			saveSuccessful = statement.execute();
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		if (gameEnded) {
			//create the statistics if the game has ended
			saveSuccessful &= createGameStatistics(game);
		}
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
		boolean statisticsSuccessful = false;
		//TODO create the game statistics and store them in the database
		return statisticsSuccessful;
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
		String query = "SELECT id, game_overview FROM bunkers_and_badasses.games WHERE active = true";
		GameOverview overview;
		int id;
		try (Statement statement = con.createStatement()) {
			//get the overviews from the database and store them in a list
			result = statement.executeQuery(query);
			while (result.next()) {
				id = result.getInt(1);
				overview = result.getObject(2, GameOverview.class);
				overview.setId(id);//add the id to the overview to identify the game save
				gameOverviews.add(overview);
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
		//add the overviews to the message and send it back to the client 
		message.setGameOverviews(gameOverviews);
		connection.sendMessage(message);
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
	public void loadGame(GameLoadRequestMessage message, JFGConnection connection) {
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		ResultSet result = null;
		int id = message.getOverview().getId();
		String query = "SELECT game_data FROM bunkers_and_badasses.games WHERE id = " + id;
		Game loadedGame = null;
		try (Statement statement = con.createStatement()) {
			result = statement.executeQuery(query);//load the game from the database
			if (result.next()) {
				loadedGame = result.getObject(1, Game.class);
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
		//add the game to the message and send it back to the client
		//the board image is loaded separately by the GameStartDialog via a BoardRequestMessage
		message.setLoadedGame(loadedGame);
		message.setLoadedSuccessful(loadedGame != null);
		connection.sendMessage(message);
	}
	
	/**
	 * Send game start messages to all invited players.
	 * 
	 * @param message
	 * 		The message to be sent.
	 */
	public void sendGameStartMessage(GameStartMessage message) {
		List<User> players = message.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			userMap.get(players.get(i)).sendMessage(message);
		}
	}
	
	/**
	 * Create a group for the players of the game.
	 * 
	 * @param message
	 * 		The message that starts the game and contains all the players.
	 */
	public void createGameGroup(GameStartMessage message) {
		List<JFGConnection> connections = new ArrayList<JFGConnection>();
		for (User player : message.getPlayers()) {
			connections.add(userMap.get(player));
		}
		JFGConnectionGroup group = createGroup(connections);
		for (JFGConnection connection : connections) {
			connection.setGroup(group);
		}
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
		}
		//create the tables
		String[] sql = new String[message.getPlayers().size()+2];
		sql[0] = "INSERT INTO bunkers_and_badasses.games (id, active) VALUES (" + gameId + ", true)";//create the game
		sql[1] = "INSERT INTO bunkers_and_badasses.game_maps VALUES (" + gameId + ", " + message.getBoardId() + ")";//create a game - map
		for (int i = 0; i < message.getPlayers().size(); i++) {
			sql[2+i] = "INSERT INTO bunkers_and_badasses.statistics (user_id, game_id) VALUES (" + userIds[i] + ", " + gameId + ")";
		}
		try (Statement statement = con.createStatement()) {
			for (String s : sql) {
				if (!statement.execute(s)) {
					throw new SQLException();
				}
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
		//send the game id to the clients to start the game
		GameStartMessage startMessage = new GameStartMessage();
		startMessage.setGameId(gameId);
		for (int i = 0; i < message.getPlayers().size(); i++) {
			userMap.get(message.getPlayers().get(i)).sendMessage(startMessage);
		}
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
		for (User user : message.getGame().getPlayers()) {
			userMap.get(user).sendMessage(message);
		}
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
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		ResultSet result = null;
		String username = connectionMap.get(connection).getUsername();
		String query = "SELECT * FROM bunkers_and_badasses.skills WHERE name = " + username;
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
		//send the profiles back to the client
		message.setProfiles(skillProfiles);
		connection.sendMessage(message);
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
			if (!statement.execute(query)) {
				throw new SQLException();
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
	}
	/**
	 * Generate the default skill profiles for a new user.
	 * 
	 * @param message
	 * 		The message that was sent to sign up the user.
	 */
	public void generateUserSkills(JFGDatabaseLoginMessage message) {
		Connection con = JFGDatabaseConnection.getJFGDefaultConnection();
		SkillProfile defaultProfile = SkillProfileManager.getDefaultSkillProfile();
		String query = "INSERT INTO bunkers_and_badasses.skills (id, name, points, eridium, credits, ammo, eridium_building, credits_building, ammo_building, heroes) VALUES (0, " + 
				message.getUsername() + ", " + defaultProfile.getPoints() + ", " + defaultProfile.getEridium() + ", " + defaultProfile.getCredits() + ", " + defaultProfile.getAmmo() + 
				", " + defaultProfile.getEridiumBuilding() + ", " + defaultProfile.getCreditsBuilding() + ", " + defaultProfile.getAmmoBuilding() + ", " + defaultProfile.getHero() + ");";
		try (Statement statement = con.createStatement()) {
			for (int i = 0; i < 5; i++) {//insert 5 default skill profiles
				if (!statement.execute(query)) {
					throw new SQLException();
				}
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
		String gameVariablesQuery = "SELECT * FROM bunkers_and_badasses.game_variables WHERE used = true";
		String skillResourcesQuery = "SELECT * FROM bunkser_and_badasses.skill_resources";
		String startResourcesQuery = "SELECT * FROM bunkers_and_badasses.start_resources WHERE used = true";
		String buildingQuery = "SELECT * FROM bunkers_and_badasses.buliding_variables";
		String buildingCostQuery = "SELECT * FROM bunkers_and_badasses.costs_building";
		String troopCostQuery = "SELECT * FROM bunkers_and_badasses.costs_troop";
		String commandCostQuery = "SELECT * FROM bunkers_and_badasses.costs_command";
		String commandQuery = "SELECT * FROM bunkers_and_badasses.commands WHERE used = true";
		String bonusQuery = "SELECT * FROM bunkers_and_badasses.turn_bonus_resources";
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
			//the costs for buildings
			result = statement.executeQuery(buildingCostQuery);
			int[][] buildingPrices = buildingStorage.getBuildingPrices();
			int[][] extensionPrices = buildingStorage.getBuildingExtensionPrices();
			while (result.next()) {
				buildingPrices[result.getInt(1)][BuildingStorage.PRICE_CREDITS] = result.getInt(2);
				buildingPrices[result.getInt(1)][BuildingStorage.PRICE_AMMO] = result.getInt(3);
				buildingPrices[result.getInt(1)][BuildingStorage.PRICE_ERIDIUM] = result.getInt(4);
				extensionPrices[result.getInt(1)][BuildingStorage.PRICE_CREDITS] = result.getInt(5);
				extensionPrices[result.getInt(1)][BuildingStorage.PRICE_AMMO] = result.getInt(6);
				extensionPrices[result.getInt(1)][BuildingStorage.PRICE_ERIDIUM] = result.getInt(7);
			}
			try {
				result.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
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
			//the bonus resources
			result = statement.executeQuery(bonusQuery);
			int[][] resources = turnBonusStorage.getResources();
			if (result.next()) {
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
		//add the loaded variables and send back the message
		message.setGameStorage(gameStorage);
		message.setBuildingStorage(buildingStorage);
		message.setTroopStorage(troopStorage);
		message.setCommandStorage(commandStorage);
		message.setTurnBonusStorage(turnBonusStorage);
		connection.sendMessage(message);
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