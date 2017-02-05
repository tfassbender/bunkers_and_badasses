package net.jfabricationgames.bunkers_and_badasses.game;

import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardTransfereMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameSaveMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameStartMessage;
import net.jfabricationgames.bunkers_and_badasses.game_frame.GameStartDialog;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameStore;
import net.jfabricationgames.bunkers_and_badasses.server.ServerLogoutMessage;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.interpreter.JFGClientInterpreter;

public class BunkersAndBadassesClientInterpreter implements JFGClientInterpreter {
	
	private GameStore gameStore;
	private GameStartDialog gameStartDialog;
	
	public BunkersAndBadassesClientInterpreter(GameStore gameStore, GameStartDialog gameStartDialog) {
		this.gameStore = gameStore;
		this.gameStartDialog = gameStartDialog;
	}

	@Override
	public void interpreteClientMessage(JFGClientMessage message, JFGClient client) {
		//board loading message
		if (message instanceof BoardTransfereMessage) {
			interpreteBoardTransfereMessage((BoardTransfereMessage) message, client);
		}
		//game save message
		else if (message instanceof GameSaveMessage) {
			interpreteGameSaveMessage((GameSaveMessage) message, client);
		}
		//game loading messages are implemented in main menu interpreter
		//game start message
		else if (message instanceof GameStartMessage) {
			interpreteGameStartMessage((GameStartMessage) message, client);
		}
		//server logout
		else if (message instanceof ServerLogoutMessage) {
			interpreteServerLogoutMessage((ServerLogoutMessage) message, client);
		}
	}
	
	private void interpreteBoardTransfereMessage(BoardTransfereMessage message, JFGClient client) {
		gameStartDialog.receiveBoard(message.getBoard());
	}
	
	private void interpreteGameSaveMessage(GameSaveMessage message, JFGClient client) {
		gameStore.addServerAnswer(message.isSaveSuccessful());
	}
	
	private void interpreteGameStartMessage(GameStartMessage message, JFGClient client) {
		gameStartDialog.receiveGameId(message.getGameId());
	}
	
	private void interpreteServerLogoutMessage(ServerLogoutMessage message, JFGClient client) {
		new ServerLogoutDialog(message).setVisible(true);
	}
}