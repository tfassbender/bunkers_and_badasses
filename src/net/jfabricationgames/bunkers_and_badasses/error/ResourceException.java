package net.jfabricationgames.bunkers_and_badasses.error;

public class ResourceException extends BunkersAndBadassesException {
	
	private static final long serialVersionUID = 8520435140805630084L;

	public ResourceException(String message, Throwable cause) {
		super(message, cause);
	}
	public ResourceException(String message) {
		super(message);
	}
	public ResourceException(String message, String errorText) {
		super(message, errorText);
	}
	public ResourceException(String message, Throwable cause, String errorText) {
		super(message, cause, errorText);
	}
}