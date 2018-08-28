package client;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import broker.PCADBrokerInterface;
import commons.NewsInterface;
import commons.SubInterface;
import commons.TopicInterface;
import exceptions.NonExistentSubException;
import exceptions.NonExistentTopicException;
import exceptions.SubscriberAlreadyConnectedException;
import exceptions.SubscriberAlreadySubbedException;

public interface ClientInterface extends SubInterface, Remote, Serializable {
	public void Subscribe(TopicInterface topic) throws RemoteException, SubscriberAlreadySubbedException, NonExistentSubException;

	public void StopNotification() throws RemoteException, NonExistentSubException;

	public void Publish(NewsInterface news) throws NonExistentTopicException, RemoteException;

	public void Unsubscribe(TopicInterface topic) throws RemoteException, NonExistentTopicException, NonExistentSubException;

	public void Connect() throws RemoteException, SubscriberAlreadyConnectedException;

	public void Disconnect() throws RemoteException, NonExistentSubException;

	void notifyClient(NewsInterface news) throws RemoteException;

	void ReadNews();

	public void Connect(PCADBrokerInterface server) throws RemoteException, SubscriberAlreadyConnectedException;

}
