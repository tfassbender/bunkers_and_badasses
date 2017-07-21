package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;

/**
 * Send a board from the server to the client.
 * The board is only sent when the client starts a new game and needs a basic map that is kept in the server file system.
 */
public class BoardTransfereMessage extends BunkersAndBadassesMessage {

	private static final long serialVersionUID = -4364647097995249370L;
	
	private Board board;
	
	public BoardTransfereMessage(Board board) {
		this.board = board;
	}
	
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
}