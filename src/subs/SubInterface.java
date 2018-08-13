package subs;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import broker.PCADBrokerInterface;
import commons.NewsInterface;
import commons.TopicInterface;

public interface SubInterface extends  Remote,Serializable {
	
	public void notifyClient() throws RemoteException;
	public boolean Connect(PCADBrokerInterface broker);
	public boolean Disconnect(PCADBrokerInterface broker);
	public void StopNotification();
	public void Publish(NewsInterface news);
	public void Unsubscribe(TopicInterface topic);
	
	
}	
