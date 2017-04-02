package net.jfabricationgames.bunkers_and_badasses.error;

public class ResourceException extends RuntimeException {
	
	private static final long serialVersionUID = 8520435140805630084L;

	public ResourceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ResourceException(String message) {
		super(message);
	}
}