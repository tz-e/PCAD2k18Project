package client;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import broker.PCADBrokerInterface;
import commons.NewsInterface;
import commons.SubInterface;
import commons.Topic;
import commons.TopicInterface;

public interface ClientInterface extends  SubInterface {
	public void Subscribe(Topic topic);
	public void StopNotification(ClientInterface sub);
	public void Publish(NewsInterface news) throws Exception;
	public void Unsubscribe(TopicInterface topic);
	public boolean Connect();
	public boolean Disconnect();
	
	
	
}	
