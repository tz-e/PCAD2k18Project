package broker;

import client.ClientInterface;
import commons.NewsInterface;
import commons.SubInterface;
import commons.Topic;
import commons.TopicInterface;

public interface PCADBrokerInterface  extends SubInterface{
	//public void request(Request r,SubInterface stub) throws RemoteException;

	public boolean Connect(ClientInterface sub);
	public boolean Disconnect(ClientInterface sub);
	public boolean Subscribe(ClientInterface sub, TopicInterface topic);
	public boolean Unsubscribe(ClientInterface sub, TopicInterface topic);
	public void PublishNews(NewsInterface news, TopicInterface topic) throws Exception;
	public boolean StopNotification(ClientInterface client);
	public boolean Subscribe(PCADBrokerInterface broker, Topic topic);
	public boolean StopNotification(PCADBrokerInterface broker);
	public boolean Unsubscribe(PCADBrokerInterface broker, TopicInterface topic);

	
	
}
