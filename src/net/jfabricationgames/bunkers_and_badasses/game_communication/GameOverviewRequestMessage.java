package net.jfabricationgames.bunkers_and_badasses.game_communication;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_storage.GameOverview;

/**
 * Send a request to the server to load all game overview objects from the database to load stored games.
 * The message can be sen't back to the client when the overview objects are received from the database.
 */
public class GameOverviewRequestMessage extends BunkersAndBadassesMessage {
	
	private static final long serialVersionUID = 193274703567848906L;
	
	private List<GameOverview> gameOverviews;
	
	public GameOverviewRequestMessage() {
		//initialize empty because the server just needs the message type
	}
	
	public List<GameOverview> getGameOverviews() {
		return gameOverviews;
	}
	public void setGameOverviews(List<GameOverview> gameOverviews) {
		this.gameOverviews = gameOverviews;
	}
}