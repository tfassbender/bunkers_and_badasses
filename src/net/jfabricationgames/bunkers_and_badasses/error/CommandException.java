package net.jfabricationgames.bunkers_and_badasses.error;

public class CommandException extends BunkersAndBadassesException {

	private static final long serialVersionUID = -3947649698414550772L;
	
	public CommandException(String message, Throwable cause) {
		super(message, cause);
	}
	public CommandException(String message) {
		super(message);
	}
	public CommandException(String message, String errorText) {
		super(message, errorText);
	}
	public CommandException(String message, Throwable cause, String errorText) {
		super(message, cause, errorText);
	}
}