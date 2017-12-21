package net.jfabricationgames.bunkers_and_badasses.login;

import static org.junit.Assert.fail;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;
import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;
import net.jfabricationgames.bunkers_and_badasses.game_character.troop.Troop;
import net.jfabricationgames.bunkers_and_badasses.game_command.Command;
import net.jfabricationgames.bunkers_and_badasses.game_communication.DynamicVariableRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.server.ServerMain;
import net.jfabricationgames.bunkers_and_badasses.server.ServerNameRequest;
import net.jfabricationgames.bunkers_and_badasses.server.UserUpdateMessage;
import net.jfabricationgames.jfgdatabaselogin.client.JFGDatabaseLoginClient;
import net.jfabricationgames.jfgdatabaselogin.client.JFGDatabaseLoginClientInterpreter;
import net.jfabricationgames.jfgdatabaselogin.message.JFGDatabaseLoginMessage;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.interpreter.JFGClientInterpreter;

/**
 * A very simple login client to login a user and receive the dynamic variables from the server database (that are needed for the tests).
 */
public class SimpleLoginClientInterpreter implements JFGClientInterpreter {

	private JFGDatabaseLoginClientInterpreter loginInterpreter;
	
	private static boolean receivedDynamicVariables = false;
	
	public SimpleLoginClientInterpreter(JFGDatabaseLoginClientInterpreter loginInterpreter) {
		this.loginInterpreter = loginInterpreter;
	}
	
	public static void loadDynamicVariables() {
		//load all needed variables from the server
		
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
		
		//load the dynamic variables (interpreted in the SimpleLoginClientInterpreter)
		Thread variables = new Thread(new Runnable() {
			@Override
			public void run() {
				DynamicVariableRequestMessage request = new DynamicVariableRequestMessage();
				client.getClient().sendMessage(request);
				while (!receivedDynamicVariables) {
					try {
						Thread.sleep(100);
					}
					catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				}
			}
		}, "variables");
		variables.start();
		
		try {
			//wait for the answer to be received
			variables.join(5000);
		}
		catch (InterruptedException ie) {
			ie.printStackTrace();
			fail("Couldn't login to server.");
		}
	}
	
	@Override
	public void interpreteClientMessage(JFGClientMessage message, JFGClient client) {
		if (message instanceof JFGDatabaseLoginMessage) {
			loginInterpreter.interpreteClientMessage(message, client);
		}
		else if (message instanceof ServerNameRequest) {
			ServerNameRequest answer = new ServerNameRequest("TestUser");
			client.sendMessage(answer);
		}
		else if (message instanceof UserUpdateMessage) {
			//ignore this message
		}
		else if (message instanceof DynamicVariableRequestMessage) {
			receiveDynamicVariables((DynamicVariableRequestMessage) message);
			receivedDynamicVariables = true;
		}
	}
	
	/**
	 * From net.jfabricationgames.bunkers_and_badasses.main_menu.MainMenuFrame
	 */
	private void receiveDynamicVariables(DynamicVariableRequestMessage message) {
		Building.setStorage(message.getBuildingStorage());
		Troop.setStorage(message.getTroopStorage());
		Command.setStorage(message.getCommandStorage());
		TurnBonus.setStorage(message.getTurnBonusStorage());
		Game.setGameVariableStorage(message.getGameStorage());
		UserPlanManager.START_COMMANDS = message.getGameStorage().getUserCommands();
		SkillProfileManager skillProfileManager = new SkillProfileManager();
		skillProfileManager.loadSkillLevels();
		/*skillProfileManager.loadSkillLevels();
		//generate the help menu
		helpMenu = new HelpMenuFrame();
		helpMenu.buildHelpMenu(message.getHelpContents());
		dynamicVariablesLoaded = true;
		//enable the game start buttons
		btnSpielErstellen.setEnabled(true);
		mntmSpielErstellen.setEnabled(true);
		mntmSpielLaden.setEnabled(true);
		mntmHilfeMenu.setEnabled(true);
		//enable the buttons in the request dialogs
		for (GameRequestDialog request : requestDialogs.values()) {
			request.enableGameStart();
		}*/
	}
}