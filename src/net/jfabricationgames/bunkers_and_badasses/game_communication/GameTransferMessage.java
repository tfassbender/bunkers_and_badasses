package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

/**
 * Send a Game object between client and server.
 */
public class GameTransferMessage implements JFGServerMessage, JFGClientMessage {
	
	private static final long serialVersionUID = 2115290204142785419L;
	
	private Game game;
	
	private TransferType type;
	
	//identify the reason for the game transfer
	public static enum TransferType {
		NEW_GAME,
		TURN_OVER,
		PLANING_COMMIT;
	}
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	public TransferType getType() {
		return type;
	}
	public void setType(TransferType type) {
		this.type = type;
	}
}