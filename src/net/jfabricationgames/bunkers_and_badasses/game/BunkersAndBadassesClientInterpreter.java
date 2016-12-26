package net.jfabricationgames.bunkers_and_badasses.game;

import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardTransfereMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameOverviewRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameSaveMessage;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.interpreter.JFGClientInterpreter;

public class BunkersAndBadassesClientInterpreter implements JFGClientInterpreter {

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
		//game loading messages
		else if (message instanceof GameOverviewRequestMessage) {
			interpreteGameOverviewRequestMessage((GameOverviewRequestMessage) message, client);
		}
	}
	
	private void interpreteBoardTransfereMessage(BoardTransfereMessage message, JFGClient client) {
		//TODO do something with the board
	}
	
	private void interpreteGameSaveMessage(GameSaveMessage message, JFGClient client) {
		if (!message.isSaveSuccessful()) {
			//TODO the game was not saved successful so do something
			System.out.println("ERROR: The game could not be saved");
		}
	}
	
	private void  interpreteGameOverviewRequestMessage(GameOverviewRequestMessage message, JFGClient client) {
		//TODO do something with the game overviews
	}
}