package net.jfabricationgames.bunkers_and_badasses.main_menu;

import net.jfabricationgames.bunkers_and_badasses.chat.ChatClient;
import net.jfabricationgames.bunkers_and_badasses.chat.ChatMessage;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.interpreter.JFGClientInterpreter;

public class MainMenuClientInterpreter implements JFGClientInterpreter {
	
	private ChatClient chatClient;
	private MainMenuDynamicLoader dynamicLoader;
	
	public MainMenuClientInterpreter(ChatClient chatClient, MainMenuDynamicLoader dynamicLoader) {
		this.chatClient = chatClient;
		this.dynamicLoader = dynamicLoader;
	}
	
	@Override
	public void interpreteClientMessage(JFGClientMessage message, JFGClient client) {
		if (message instanceof ChatMessage) {
			interpreteChatMessage((ChatMessage) message, client);
		}
		else if (message instanceof MainMenuMessage) {
			interpreteMainMenuMessage((MainMenuMessage) message, client);
		}
	}
	
	private void interpreteChatMessage(ChatMessage message, JFGClient client) {
		chatClient.receiveMessage(message.getMessage());
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
				//TODO send the answer to the player that created the game (MainMenuMessage.toPlayer)
				break;
			case GAME_CREATION_REQUEST:
				//do nothing here; only server side
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
		}
	}
}