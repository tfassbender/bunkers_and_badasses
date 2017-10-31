package net.jfabricationgames.bunkers_and_badasses.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.jfabricationgames.jfgdatabaselogin.server.JFGDatabaseLoginServerInterpreter;
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
		//set the connection factory to use secured messaging
		server.setConnectionFactory(new BunkersAndBadassesConnection());
		//server.setConnectionFactory(new JFGSecureMessageConnection());
		
		//set the JFGConnections to reset the output before every message sent
		//JFGConnection.setResetBeforeSending(true);
		
		//start the server
		try {
			server.startServer();
			System.out.println("Bunkers and Badasses - Server started");
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		//set the server application value to true
		BunkersAndBadassesServer.IS_SERVER_APPLICATION = true;
		
		//print the build information
		try (BufferedReader buildInfoReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/build_info")))) {
			StringBuilder buildInfo = new StringBuilder();
			String line;
			buildInfo.append('\n');
			while ((line = buildInfoReader.readLine()) != null) {
				buildInfo.append(line);
				buildInfo.append('\n');
			}
			System.out.println(buildInfo.toString());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public JFGLoginServer getServer() {
		return server;
	}
}