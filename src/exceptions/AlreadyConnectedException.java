package exceptions;

public class AlreadyConnectedException extends Exception {
	private static final long serialVersionUID = 1L;

	public AlreadyConnectedException(String message) {
		super(message);
	}

	public AlreadyConnectedException() {
	}

}
