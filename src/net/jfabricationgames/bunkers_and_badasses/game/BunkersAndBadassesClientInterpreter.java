package net.jfabricationgames.bunkers_and_badasses.game;

import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardTransfereMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameSaveMessage;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameStore;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.interpreter.JFGClientInterpreter;

public class BunkersAndBadassesClientInterpreter implements JFGClientInterpreter {
	
	private GameStore gameStore;
	
	public BunkersAndBadassesClientInterpreter(GameStore gameStore) {
		this.gameStore = gameStore;
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
	}
	
	private void interpreteBoardTransfereMessage(BoardTransfereMessage message, JFGClient client) {
		//TODO do something with the board
	}
	
	private void interpreteGameSaveMessage(GameSaveMessage message, JFGClient client) {
		gameStore.addServerAnswer(message.isSaveSuccessful());
	}
}