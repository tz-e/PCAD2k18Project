package exceptions;

public class SubscriberAlreadyConnectedException extends Exception{
	private static final long serialVersionUID = 1L;

	public SubscriberAlreadyConnectedException(String message) {
		super(message);
	}

	public SubscriberAlreadyConnectedException() {
	}

}
