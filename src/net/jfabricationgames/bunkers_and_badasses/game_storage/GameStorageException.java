package net.jfabricationgames.bunkers_and_badasses.game_storage;

/**
 * This exception is thrown when the storing or loading of the game crashed.
 */
public class GameStorageException extends RuntimeException {
	
	private static final long serialVersionUID = 5148185844549835205L;

	public GameStorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public GameStorageException(String message) {
		super(message);
	}
}