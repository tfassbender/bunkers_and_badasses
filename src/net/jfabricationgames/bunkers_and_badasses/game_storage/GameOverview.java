package net.jfabricationgames.bunkers_and_badasses.game_storage;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class GameOverview implements Serializable, Comparable<GameOverview> {
	
	private static final long serialVersionUID = -1483474172284438490L;

	public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";
	
	private String dateStored;
	private long timeStamp;
	private String boardName;
	private List<User> players;
	private int turn;
	private int id;//is set when the game overview is loaded from the database to identify the game
	
	public GameOverview(Game game) {
		this.players = game.getPlayers();
		this.turn = game.getTurnManager().getTurn();
		this.boardName = game.getBoard().getName();
		setCurrentDateTime();
	}
	
	/**
	 * Compare two GameOverviews to sort them and display them in the right order.
	 * 
	 * @param overview
	 * 		The compared overview
	 */
	@Override
	public int compareTo(GameOverview overview) {
		if (dateStored == null || dateStored.isEmpty() || timeStamp == 0) {
			return 0;
		}
		else {
			return (timeStamp - overview.getDateStamp() > 0 ? 1 : timeStamp - overview.getDateStamp() < 0 ? -1 : 0);
		}
	}
	
	@Override
	public String toString() {
		return dateStored + " " + boardName + " " + players + "";
	}
	
	/**
	 * A time stamp to compare the creation date of the game overviews.
	 */
	protected long getDateStamp() {
		return timeStamp;
	}
	
	/**
	 * Set the store date to the current date and time.
	 * Also sets a time stamp to compare the overviews creation date. 
	 */
	public void setCurrentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		dateStored = dateFormat.format(date);
		timeStamp = System.currentTimeMillis();
	}
	
	public String getDateStored() {
		return dateStored;
	}
	public void setDateStored(String dateStored) {
		this.dateStored = dateStored;
	}
	
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	
	public List<User> getPlayers() {
		return players;
	}
	public void setPlayers(List<User> players) {
		this.players = players;
	}
	
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}