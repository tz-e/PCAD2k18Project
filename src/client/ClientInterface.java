package client;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import broker.PCADBrokerInterface;
import commons.NewsInterface;
import commons.SubInterface;
import commons.TopicInterface;
import exceptions.AlreadyConnectedException;
import exceptions.NonExistentSubException;
import exceptions.NonExistentTopicException;
import exceptions.NotConnectedException;
import exceptions.SubscriberAlreadyConnectedException;
import exceptions.SubscriberAlreadySubbedException;

public interface ClientInterface extends SubInterface, Remote, Serializable {
	public void Subscribe(TopicInterface topic) throws RemoteException, SubscriberAlreadySubbedException, NonExistentSubException, NotConnectedException;

	public void StopNotification() throws RemoteException, NonExistentSubException, NotConnectedException;

	public void Publish(NewsInterface news) throws NonExistentTopicException, RemoteException, NotConnectedException;

	public void Unsubscribe(TopicInterface topic) throws RemoteException, NonExistentTopicException, NonExistentSubException, NotConnectedException;

	public void Connect() throws RemoteException, SubscriberAlreadyConnectedException, AlreadyConnectedException;

	public void Disconnect() throws RemoteException, NonExistentSubException, NotConnectedException;

	public void notifyClient(NewsInterface news) throws RemoteException;

	public Thread ReadNews() throws RemoteException, NotConnectedException;


	public void Connect(PCADBrokerInterface server) throws RemoteException, SubscriberAlreadyConnectedException, AlreadyConnectedException;

}
