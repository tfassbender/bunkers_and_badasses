package net.jfabricationgames.bunkers_and_badasses.game_storage;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class GameOverview implements Serializable {
	
	private static final long serialVersionUID = -1483474172284438490L;

	public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";
	
	private String dateStored;
	private List<User> players;
	private int turn;
	private int id;//is set when the game overview is loaded from the database to identify the game
	
	public GameOverview(Game game) {
		this.players = game.getPlayers();
		this.turn = game.getTurnManager().getTurn();
		setCurrentDateTime();
	}
	
	/**
	 * Set the store date to the current date and time.
	 */
	public void setCurrentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		dateStored = dateFormat.format(date);
	}
	
	public String getDateStored() {
		return dateStored;
	}
	public void setDateStored(String dateStored) {
		this.dateStored = dateStored;
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