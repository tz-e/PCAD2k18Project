package exceptions;

public class SubscriberAlreadySubbedException extends Exception {

	private static final long serialVersionUID = 1L;

	public SubscriberAlreadySubbedException(String message) {
		super(message);
	}

	public SubscriberAlreadySubbedException() {
	}

}
