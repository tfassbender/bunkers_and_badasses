package net.jfabricationgames.bunkers_and_badasses.main_menu;

import net.jfabricationgames.bunkers_and_badasses.chat.ChatClient;
import net.jfabricationgames.bunkers_and_badasses.chat.ChatMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardOverviewRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameLoadRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameOverviewRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameStartMessage;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameStore;
import net.jfabricationgames.bunkers_and_badasses.server.UserUpdateMessage;
import net.jfabricationgames.bunkers_and_badasses.user.UserManager;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.interpreter.JFGClientInterpreter;

public class MainMenuClientInterpreter implements JFGClientInterpreter {
	
	private ChatClient chatClient;
	private MainMenuDynamicLoader dynamicLoader;
	private MainMenuFrame mainMenu;
	
	private GameStore gameStore;
	
	public MainMenuClientInterpreter(ChatClient chatClient, MainMenuDynamicLoader dynamicLoader, MainMenuFrame mainMenu, GameStore gameStore) {
		this.chatClient = chatClient;
		this.dynamicLoader = dynamicLoader;
		this.mainMenu = mainMenu;
		this.gameStore = gameStore;
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
		else if (message instanceof GameOverviewRequestMessage) {
			interpreteGameOverviewRequestMessage((GameOverviewRequestMessage) message, client);
		}
		else if (message instanceof BoardOverviewRequestMessage) {
			interpreteBoardOverviewRequestMessage((BoardOverviewRequestMessage) message, client);
		}
		else if (message instanceof GameLoadRequestMessage) {
			interpreteGameLoadRequestMessage((GameLoadRequestMessage) message, client);
		}
		else if (message instanceof GameStartMessage) {
			interpreteGameStartMessage((GameStartMessage) message, client);
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
				mainMenu.showGameRequest(message.getPlayer(), message.getInvitedPlayers(), message.getMap());
				break;
			case GAME_CREATEION_ABORT:
				mainMenu.receiveGameCreationAbort(message.getPlayer(), message.getAbortCause());
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
				mainMenu.receiveAccoutUpdateAnswer(message.isUpdateSuccessfull(), message.getUsername());
				break;
			case GAME_LOADING_ANSWER:
				mainMenu.receiveGameLoadingAnswer(message.getPlayer(), message.isJoining());
				break;
			case GAME_LOADING_REQUEST:
				mainMenu.showGameRequest(message.getPlayer(), message.getInvitedPlayers(), message.getMap(), message.getOverview());
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
	
	private void interpreteGameOverviewRequestMessage(GameOverviewRequestMessage message, JFGClient client) {
		mainMenu.receiveGameOverviews(message.getGameOverviews());
	}
	
	private void interpreteBoardOverviewRequestMessage(BoardOverviewRequestMessage message, JFGClient client) {
		mainMenu.receiveBoardOverviews(message.getBoards());
	}
	
	private void interpreteGameLoadRequestMessage(GameLoadRequestMessage message, JFGClient client) {
		gameStore.setLoadedGame(message.getLoadedGame());
		gameStore.addServerAnswer(message.isLoadedSuccessful());
	}
	
	private void interpreteGameStartMessage(GameStartMessage message, JFGClient client) {
		mainMenu.receiveGameStartMessage(message.getPlayers().get(0), message.getBoardId(), message.getPlayers().size());
	}
}