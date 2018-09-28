package exceptions;

public class SubscriberAlreadyConnectedException extends AlreadyConnectedException{
	private static final long serialVersionUID = 1L;

	public SubscriberAlreadyConnectedException(String message) {
		super(message);
	}

	public SubscriberAlreadyConnectedException() {
	}

}
