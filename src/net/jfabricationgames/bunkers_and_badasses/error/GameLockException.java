package net.jfabricationgames.bunkers_and_badasses.error;

public class GameLockException extends BunkersAndBadassesException {
	
	private static final long serialVersionUID = 7150441647156055525L;
	
	public GameLockException(String message, String errorText) {
		super(message, errorText);
	}
	public GameLockException(String message, Throwable cause, String errorText) {
		super(message, cause, errorText);
	}
	public GameLockException(String message, Throwable cause) {
		super(message, cause);
	}
	public GameLockException(String message) {
		super(message);
	}
}