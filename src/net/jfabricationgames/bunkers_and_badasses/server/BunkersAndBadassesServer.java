package net.jfabricationgames.bunkers_and_badasses.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jdbc.JFGDatabaseConnection;
import net.jfabricationgames.jfgserver.server.JFGConnection;
import net.jfabricationgames.jfgserver.server.JFGLoginServer;

public class BunkersAndBadassesServer extends JFGLoginServer {
	
	private Map<User, JFGConnection> userMap;
	private Map<JFGConnection, User> connectionMap;
	
	private List<User> allUsers;
	
	public BunkersAndBadassesServer(int port) {
		super(port);
		userMap = new HashMap<User, JFGConnection>();
		connectionMap = new HashMap<JFGConnection, User>();
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
	
	private void sendUserUpdate() {
		/*List<User> users = new ArrayList<User>();
		for (User u : allUsers) {
			User tmp = new User(u.getUsername());
			tmp.setOnline(u.isOnline());
			users.add(tmp);
		}*/
		UserUpdateMessage update = new UserUpdateMessage(allUsers);
		for (JFGConnection con : getConnections()) {
			if (isLoggedIn(con)) {
				System.out.println("sending to: " + connectionMap.get(con).getUsername());
				for (User u : update.getUsers()) {
					System.out.println(u.getUsername() + " " + u.isOnline());
				}
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
			result = statement.executeQuery("SELECT username FROM bunkers_and_badasses.login");
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
	
	public List<User> getUsers() {
		return allUsers;
	}
}