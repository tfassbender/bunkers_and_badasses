package net.jfabricationgames.bunkers_and_badasses.error;

public class BunkersAndBadassesException extends RuntimeException {
	
	private static final long serialVersionUID = 3875049482621001758L;
	
	protected String errorText;
	
	public BunkersAndBadassesException(String message, Throwable cause) {
		super(message, cause);
	}
	public BunkersAndBadassesException(String message) {
		super(message);
	}
	public BunkersAndBadassesException(String message, String errorText) {
		super(message);
		this.errorText = errorText;
	}
	public BunkersAndBadassesException(String message, Throwable cause, String errorText) {
		super(message, cause);
		this.errorText = errorText;
	}
	
	public String getErrorText() {
		return errorText;
	}
}