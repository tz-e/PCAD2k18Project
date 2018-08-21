package client;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import commons.NewsInterface;
import commons.SubInterface;
import commons.TopicInterface;

public interface ClientInterface extends SubInterface, Remote, Serializable {
	public boolean Subscribe(TopicInterface topic) throws RemoteException;

	public void StopNotification() throws RemoteException;

	public void Publish(NewsInterface news) throws Exception, RemoteException;

	public void Unsubscribe(TopicInterface topic) throws RemoteException;

	public boolean Connect() throws RemoteException;

	public boolean Disconnect() throws RemoteException;

	void notifyClient(NewsInterface news) throws RemoteException, Exception;

}
