package commons;

import broker.PCADBrokerInterface;

public interface SubBrokerInterface {
	public void Subscribe(PCADBrokerInterface broker, Topic topic);
	public void StopNotification(PCADBrokerInterface broker);
	
}
