package exceptions;

public class NonExistentTopicException extends Exception {

	public NonExistentTopicException(String message) {
		super(message);
	}
	public NonExistentTopicException() {}
	private static final long serialVersionUID = 1L;
	
}
