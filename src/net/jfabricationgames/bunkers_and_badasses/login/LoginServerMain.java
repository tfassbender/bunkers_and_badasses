package net.jfabricationgames.bunkers_and_badasses.login;

import java.io.IOException;

import net.jfabricationgames.jfgdatabaselogin.server.JFGDatabaseLoginServerInterpreter;
import net.jfabricationgames.jfgserver.interpreter.JFGServerInterpreter;
import net.jfabricationgames.jfgserver.server.JFGLoginServer;

/**
 * This class is the server side main class of Bunkers and Badasses.
 * 
 * The game is started when the user is logged in.
 */
public class LoginServerMain {
	
	/**
	 * The servers port number and URL (also used in the client main)
	 */
	public static final int SERVER_PORT = 4715;
	public static final String SERVER_URL = "jfabricationgames.ddns.net";
	
	private JFGLoginServer server;
	
	public static void main(String [] args) {
		new LoginServerMain();
	}
	
	/**
	 * Create a new JFGLoginServer that is connected to the JFG database to login the users of Bunkers and Badasses 
	 */
	public LoginServerMain() {
		//Tell the server interpreter how to reach the login data
		JFGDatabaseLoginServerInterpreter.CHECK_NAME_SQL = "SELECT username FROM bunkers_and_badasses.login WHERE username = '%user';";
		JFGDatabaseLoginServerInterpreter.LOGIN_SQL = "SELECT passwd FROM bunkers_and_badasses.login WHERE username = '%user';";
		JFGDatabaseLoginServerInterpreter.SIGN_UP_SQL = "INSERT INTO bunkers_and_badasses.login VALUES (0, '%user', '%pass');";
		//create a new interpreter
		server = new JFGLoginServer(SERVER_PORT);
		JFGServerInterpreter interpreter = new JFGDatabaseLoginServerInterpreter(server);
		server.setInterpreterFactory(interpreter);
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