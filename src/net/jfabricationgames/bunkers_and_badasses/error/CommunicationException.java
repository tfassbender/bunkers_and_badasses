package net.jfabricationgames.bunkers_and_badasses.error;

public class CommunicationException extends BunkersAndBadassesException {
	
	private static final long serialVersionUID = -4033514626948701450L;
	
	public CommunicationException(String message, String errorText) {
		super(message, errorText);
	}
	
	public CommunicationException(String message, Throwable cause, String errorText) {
		super(message, cause, errorText);
	}
	
	public CommunicationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CommunicationException(String message) {
		super(message);
	}
}