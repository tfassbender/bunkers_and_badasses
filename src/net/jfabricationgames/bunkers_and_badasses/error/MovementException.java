package net.jfabricationgames.bunkers_and_badasses.error;

public class MovementException extends BunkersAndBadassesException {

	private static final long serialVersionUID = 9069660719634795031L;

	public MovementException(String message, Throwable cause) {
		super(message, cause);
	}
	public MovementException(String message) {
		super(message);
	}
	public MovementException(String message, String errorText) {
		super(message, errorText);
	}
	public MovementException(String message, Throwable cause, String errorText) {
		super(message, cause, errorText);
	}
}