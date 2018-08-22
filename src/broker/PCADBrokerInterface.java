package broker;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import client.ClientInterface;
import commons.NewsInterface;
import commons.SubInterface;
import commons.TopicInterface;
import exceptions.NonExistentSubException;
import exceptions.NonExistentTopicException;
import exceptions.SameBrokerException;
import exceptions.SubscriberAlreadyConnectedException;
import exceptions.SubscriberAlreadySubbedException;

/**
 * Interfaccia per rappresentare un generico Broker Contiene Metodi per
 * comunicare sia con dei suoi pari che con dei client
 * 
 * @author Daniele Atzeni
 */
public interface PCADBrokerInterface extends SubInterface, Remote, Serializable {

	public void Connect(ClientInterface sub) throws RemoteException, SubscriberAlreadyConnectedException;

	public void Connect(PCADBrokerInterface broker) throws RemoteException, SubscriberAlreadyConnectedException, SameBrokerException;

	public void Disconnect(ClientInterface sub) throws RemoteException, NonExistentSubException;

	public void Disconnect(PCADBrokerInterface broker) throws RemoteException, NonExistentSubException;

	public void Subscribe(ClientInterface sub, TopicInterface topic) throws RemoteException, SubscriberAlreadySubbedException, NonExistentSubException;

	public void Subscribe(PCADBrokerInterface broker, TopicInterface topic) throws RemoteException, SubscriberAlreadySubbedException, NonExistentSubException;

	public void Unsubscribe(ClientInterface sub, TopicInterface topic) throws RemoteException, NonExistentTopicException, NonExistentSubException;

	public void Unsubscribe(PCADBrokerInterface broker, TopicInterface topic) throws RemoteException, NonExistentTopicException, NonExistentSubException;

	public void PublishNews(NewsInterface news, TopicInterface topic) throws NonExistentTopicException, RemoteException;

	public void StopNotification(ClientInterface client) throws RemoteException, NonExistentSubException;

	public void StopNotification(PCADBrokerInterface broker) throws RemoteException, NonExistentSubException;

	public void notifyBroker(NewsInterface news) throws RemoteException, NonExistentTopicException;

}
