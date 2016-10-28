package net.jfabricationgames.bunkers_and_badasses.main_menu;

import net.jfabricationgames.bunkers_and_badasses.chat.ChatClient;
import net.jfabricationgames.bunkers_and_badasses.chat.ChatMessage;
import net.jfabricationgames.bunkers_and_badasses.server.UserUpdateMessage;
import net.jfabricationgames.bunkers_and_badasses.user.UserManager;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.interpreter.JFGClientInterpreter;

public class MainMenuClientInterpreter implements JFGClientInterpreter {
	
	private ChatClient chatClient;
	private MainMenuDynamicLoader dynamicLoader;
	private MainMenuFrame mainMenu;
	
	public MainMenuClientInterpreter(ChatClient chatClient, MainMenuDynamicLoader dynamicLoader, MainMenuFrame mainMenu) {
		this.chatClient = chatClient;
		this.dynamicLoader = dynamicLoader;
		this.mainMenu = mainMenu;
	}
	
	@Override
	public void interpreteClientMessage(JFGClientMessage message, JFGClient client) {
		if (message instanceof MainMenuMessage) {
			interpreteMainMenuMessage((MainMenuMessage) message, client);
		}
		else if (message instanceof UserUpdateMessage) {
			interpreteUserUpdateMessage((UserUpdateMessage) message, client);
		}
		else if (message instanceof ChatMessage) {
			interpreteChatMessage((ChatMessage) message, client);
		}
	}
	
	private void interpreteMainMenuMessage(MainMenuMessage message, JFGClient client) {
		switch (message.getMessageType()) {
			case DYNAMIC_CONTENT_ANSWER:
				dynamicLoader.receiveDynamicContent(message);
				break;
			case DYNAMIC_CONTENT_REQUEST:
				//do nothing here; only server side
				break;
			case GAME_CREATION_ANSWER:
				mainMenu.receiveGameCreationAnswer(message.getPlayer(), message.isJoining());
				break;
			case GAME_CREATION_REQUEST:
				mainMenu.showGameRequest(message.getPlayer(), message.getInvitedPlayers());
				break;
			case GAME_CREATEION_ABORT:
				//TODO inform the user about the abort
				break;
			case PASSWORD_UPDATE:
				//do nothing here; only server side
				break;
			case PASSWORD_USERNAME_UPDATE:
				//do nothing here; only server side
				break;
			case USERNAME_UPDATE:
				//do nothing here; only server side
				break;
			case PASSWORD_USERNAME_UPDATE_ANSWER:
				//TODO
				break;
		}
	}
	
	private void interpreteUserUpdateMessage(UserUpdateMessage message, JFGClient client) {
		UserManager.setUsers(message.getUsers());
		mainMenu.updateUserList();
	}
	
	private void interpreteChatMessage(ChatMessage message, JFGClient client) {
		chatClient.receiveMessage(message.getMessage());
	}
}