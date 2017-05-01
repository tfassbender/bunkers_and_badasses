package net.jfabricationgames.bunkers_and_badasses.error;

public class MovementException extends RuntimeException {

	private static final long serialVersionUID = 9069660719634795031L;

	public MovementException(String message, Throwable cause) {
		super(message, cause);
	}

	public MovementException(String message) {
		super(message);
	}
}