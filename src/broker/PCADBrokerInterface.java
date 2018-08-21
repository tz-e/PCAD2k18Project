package broker;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import client.ClientInterface;
import commons.NewsInterface;
import commons.SubInterface;
import commons.Topic;
import commons.TopicInterface;

public interface PCADBrokerInterface extends SubInterface, Remote, Serializable {

	public boolean Connect(ClientInterface sub) throws RemoteException;

	public boolean Connect(PCADBrokerInterface broker) throws RemoteException;

	public boolean Disconnect(ClientInterface sub) throws RemoteException;

	public boolean Disconnect(PCADBrokerInterface broker) throws RemoteException;

	public boolean Subscribe(ClientInterface sub, TopicInterface topic) throws RemoteException;

	public boolean Subscribe(PCADBrokerInterface broker, TopicInterface topic) throws RemoteException;

	public boolean Unsubscribe(ClientInterface sub, TopicInterface topic) throws RemoteException;

	public boolean Unsubscribe(PCADBrokerInterface broker, TopicInterface topic) throws RemoteException;

	public int PublishNews(NewsInterface news, TopicInterface topic) throws Exception;

	public boolean StopNotification(ClientInterface client) throws RemoteException;

	public boolean StopNotification(PCADBrokerInterface broker) throws RemoteException;

	public void notifyBroker(NewsInterface news) throws RemoteException, Exception;

}
