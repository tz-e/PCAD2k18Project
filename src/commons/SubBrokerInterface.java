package commons;

import broker.PCADBrokerInterface;
import client.ClientInterface;
/**
 * Questa interfaccia e' specifica per il broker 
 * e i messaggi che puo' ricevere da suoi pari
 * @author Daniele Atzeni
 */
public interface SubBrokerInterface {
	public boolean Subscribe(PCADBrokerInterface broker, Topic topic);
	public void StopNotification(PCADBrokerInterface broker);
	public boolean Unsubscribe(PCADBrokerInterface broker, TopicInterface topic);

	
}
