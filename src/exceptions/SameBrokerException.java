package exceptions;

public class SameBrokerException extends Exception{
	public SameBrokerException(String message) {
		super(message);
	}
	public SameBrokerException() {}
	private static final long serialVersionUID = 1L;
	
}
