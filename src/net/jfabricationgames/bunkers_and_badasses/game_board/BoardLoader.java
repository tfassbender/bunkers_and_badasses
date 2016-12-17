package net.jfabricationgames.bunkers_and_badasses.game_board;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BoardLoader {
	
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
}