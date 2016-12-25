package net.jfabricationgames.bunkers_and_badasses.game;

import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardTransfereMessage;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.interpreter.JFGClientInterpreter;

public class BunkersAndBadassesClientInterpreter implements JFGClientInterpreter {

	@Override
	public void interpreteClientMessage(JFGClientMessage message, JFGClient client) {
		//board loading message
		if (message instanceof BoardTransfereMessage) {
			//TODO do something with the board
		}
	}
}