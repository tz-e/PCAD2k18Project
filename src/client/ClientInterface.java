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
	public void Subscribe(Topic topic) throws RemoteException;
	public void StopNotification(ClientInterface sub) throws RemoteException;
	public void Publish(NewsInterface news) throws Exception, RemoteException;
	public void Unsubscribe(TopicInterface topic) throws RemoteException;
	public boolean Connect() throws RemoteException;
	public boolean Disconnect() throws RemoteException;
	
	
	
}	
