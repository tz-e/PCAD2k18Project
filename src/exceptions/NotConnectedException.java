package exceptions;

public class NotConnectedException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotConnectedException(String message) {
		super(message);
	}

	public NotConnectedException() {
	}

}
