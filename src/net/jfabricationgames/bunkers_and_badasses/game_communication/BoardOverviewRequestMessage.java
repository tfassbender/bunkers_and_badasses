package net.jfabricationgames.bunkers_and_badasses.game_communication;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;

/**
 * Get an overview over all available boards (require the boards without fields or regions for less data).
 */
public class BoardOverviewRequestMessage extends BunkersAndBadassesMessage {
	
	private static final long serialVersionUID = 3408207952133660374L;
	
	private List<Board> boards;//the available board info (names, images, player numbers)
	
	public BoardOverviewRequestMessage() {
		//the server just needs the type of this message
	}
	
	public List<Board> getBoards() {
		return boards;
	}
	public void setBoards(List<Board> boards) {
		this.boards = boards;
	}
}