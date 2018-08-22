package exceptions;

public class NonExistentSubException extends Exception {
	private static final long serialVersionUID = 1L;

	public NonExistentSubException(String message) {
		super(message);
	}

	public NonExistentSubException() {
	}

}
