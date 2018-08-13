package broker;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import client.ClientInterface;
import commons.NewsInterface;
import commons.TopicInterface;

public interface PCADBrokerInterface  extends Remote,Serializable{
	//public void request(Request r,SubInterface stub) throws RemoteException;

	public boolean Connect(ClientInterface sub);
	public boolean Disconnect(ClientInterface sub);
	public boolean Subscribe(ClientInterface sub, TopicInterface topic);
	public boolean Unsubscribe(ClientInterface sub, TopicInterface topic);
	public void PublishNews(NewsInterface news, TopicInterface topic);
	public void StopNotification(ClientInterface client);

	
}
