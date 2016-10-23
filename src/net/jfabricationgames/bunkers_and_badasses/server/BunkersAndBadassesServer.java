package net.jfabricationgames.bunkers_and_badasses.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jdbc.JFGDatabaseConnection;
import net.jfabricationgames.jfgserver.server.JFGConnection;
import net.jfabricationgames.jfgserver.server.JFGLoginServer;

public class BunkersAndBadassesServer extends JFGLoginServer {
	
	private Map<User, JFGConnection> userMap;
	
	private List<User> allUsers;
	
	public BunkersAndBadassesServer(int port) {
		super(port);
		loadUsers();
	}
	
	@Override
	public void acceptLogin(JFGConnection connection) {
		super.acceptLogin(connection);
		//TODO get the user name and map it to the connection
		//TODO when the user name is found send a message to all clients to update the user list
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
}