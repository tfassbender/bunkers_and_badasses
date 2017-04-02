package net.jfabricationgames.bunkers_and_badasses.error;

public class CommandException extends RuntimeException {

	private static final long serialVersionUID = -3947649698414550772L;

	public CommandException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandException(String message) {
		super(message);
	}
}