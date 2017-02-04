package net.jfabricationgames.bunkers_and_badasses.game_board;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.jfabricationgames.jdbc.JFGDatabaseConnection;

public class BoardLoader {
	
	/**
	 * Load a board from a file.
	 * 
	 * @param file
	 * 		The file from where the board is loaded.
	 * 
	 * @return
	 * 		The loaded board or null if anything went wrong while loading.
	 */
	public Board loadBoard(File file) {
		Board board = null;
		try (FileInputStream fileStream = new FileInputStream(file);
				BufferedInputStream bufferedStream = new BufferedInputStream(fileStream);
				ObjectInputStream objectStream = new ObjectInputStream(bufferedStream);) {
			board = (Board) objectStream.readObject();
		}
		catch (IOException | ClassNotFoundException ioe) {
			ioe.printStackTrace();
		}
		return board;
	}
	
	/**
	 * Store the board to a file.
	 * Should mostly be used by the Map Creator.
	 * 
	 * @param board
	 * 		The created board that is stored to a file.
	 * 
	 * @param file
	 * 		The path where the board is stored.
	 */
	public void storeBoard(Board board, File file) {
		try (FileOutputStream fileStream = new FileOutputStream(file);
				BufferedOutputStream bufferedStream = new BufferedOutputStream(fileStream);
				ObjectOutputStream objectStream = new ObjectOutputStream(bufferedStream);) {
			objectStream.writeObject(board);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Return a board that is identified by it's id in the database and loaded from the file system.
	 * WARNING: The database connection only works on the server side. Don't execute on client side.
	 * 
	 * @param id
	 * 		The id of the board in the database.
	 * 
	 * @return
	 * 		The board loaded from the file system.
	 */
	public Board loadBoard(int id) {
		Connection connection = JFGDatabaseConnection.getJFGDefaultConnection();
		ResultSet result = null;
		String query = "SELECT path, players_min, players_max FROM bunkers_and_badasses.maps WHERE id = " + id;
		Board board = null;
		try (Statement statement = connection.createStatement()) {
			result = statement.executeQuery(query);//get the path from the database
			if (result.next()) {
				String path = result.getString(1);//column count starts by one
				int playersMin = result.getInt(2);
				int playersMax = result.getInt(3);
				File file = new File(path);
				board = loadBoard(file);//load the map from a file
				board.setPlayersMin(playersMin);
				board.setPlayersMax(playersMax);
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			//close resources
			if (result != null) {
				try {
					result.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return board;
	}
}