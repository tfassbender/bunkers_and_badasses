package net.jfabricationgames.bunkers_and_badasses.login;

import static org.junit.Assert.*;

import org.junit.Test;

import net.jfabricationgames.bunkers_and_badasses.server.ServerMain;
import net.jfabricationgames.jfgdatabaselogin.client.JFGDatabaseLoginClient;
import net.jfabricationgames.jfgdatabaselogin.client.JFGDatabaseLoginClientInterpreter;

public class ConnectionTest {
	
	@Test
	public void testLogin() {
		//create a client and the interpreters
		JFGDatabaseLoginClient client = new JFGDatabaseLoginClient(ServerMain.SERVER_URL, ServerMain.SERVER_PORT, true);
		JFGDatabaseLoginClientInterpreter loginInterpreter = (JFGDatabaseLoginClientInterpreter) client.getClient().getClientInterpreter();
		SimpleLoginClientInterpreter interpreter = new SimpleLoginClientInterpreter(loginInterpreter);
		client.getClient().setClientInterpreter(interpreter);
		
		//login
		Thread login = new Thread(new Runnable() {
			@Override
			public void run() {
				if (!client.login("TestUser", "testtest")) {//use the TestUser profile to login
					System.err.println("Couldn't login to server");
					fail("Couldn't login to server.");
				}
			}
		}, "login");
		login.start();
		
		try {
			login.join(5000);
		}
		catch (InterruptedException ie) {
			ie.printStackTrace();
			fail("Couldn't login to server.");
		}
	}
	
	@Test
	public void testLoadDynamicVariables() {
		SimpleLoginClientInterpreter.loadDynamicVariables();
	}
}