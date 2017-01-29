package net.jfabricationgames.bunkers_and_badasses.server;

import java.io.IOException;

import net.jfabricationgames.jfgdatabaselogin.server.JFGDatabaseLoginServerInterpreter;
import net.jfabricationgames.jfgserver.server.JFGConnection;
import net.jfabricationgames.jfgserver.server.JFGLoginServer;

/**
 * This class is the server side main class of Bunkers and Badasses.
 * 
 * The game is started when the user is logged in.
 */
public class ServerMain {
	
	/**
	 * The servers port number and URL (also used in the client main)
	 */
	public static final int SERVER_PORT = 4715;
	public static final String SERVER_URL = "jfabricationgames.ddns.net";
	
	private BunkersAndBadassesServer server;
	
	public static void main(String [] args) {
		new ServerMain();
	}
	
	/**
	 * Create a new JFGLoginServer that is connected to the JFG database to login the users of Bunkers and Badasses 
	 */
	public ServerMain() {
		//Tell the server interpreter how to reach the login data
		JFGDatabaseLoginServerInterpreter.CHECK_NAME_SQL = "SELECT username FROM bunkers_and_badasses." + BunkersAndBadassesServer.loginTable + " WHERE username = '%user';";
		JFGDatabaseLoginServerInterpreter.LOGIN_SQL = "SELECT passwd FROM bunkers_and_badasses." + BunkersAndBadassesServer.loginTable + " WHERE username = '%user';";
		JFGDatabaseLoginServerInterpreter.SIGN_UP_SQL = "INSERT INTO bunkers_and_badasses." + BunkersAndBadassesServer.loginTable + " VALUES (0, '%user', '%pass');";
		//create a new interpreter
		server = new BunkersAndBadassesServer(SERVER_PORT);
		JFGDatabaseLoginServerInterpreter loginInterpreter = new JFGDatabaseLoginServerInterpreter(server);
		BunkersAndBadassesServerInterpreter interpreter = new BunkersAndBadassesServerInterpreter(loginInterpreter, server);
		server.setInterpreterFactory(interpreter);
		
		//set the JFGConnections to reset the output before every message sent
		JFGConnection.setResetBeforeSending(true);
		
		//start the server
		try {
			server.startServer();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public JFGLoginServer getServer() {
		return server;
	}
}