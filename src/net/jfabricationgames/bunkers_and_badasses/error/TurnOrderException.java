package net.jfabricationgames.bunkers_and_badasses.error;

public class TurnOrderException extends BunkersAndBadassesException {
	
	private static final long serialVersionUID = -385372012048286629L;
	
	public TurnOrderException(String message, Throwable cause) {
		super(message, cause);
	}
	public TurnOrderException(String message) {
		super(message);
	}
	public TurnOrderException(String message, String errorText) {
		super(message, errorText);
	}
	public TurnOrderException(String message, Throwable cause, String errorText) {
		super(message, cause, errorText);
	}
}