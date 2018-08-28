package testPackage;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;

import broker.PCADBroker;
import broker.PCADBrokerInterface;
import client.Client;
import client.ClientInterface;
import commons.News;
import commons.Topic;
import commons.TopicInterface;
import exceptions.NonExistentSubException;
import exceptions.NonExistentTopicException;
import exceptions.SameBrokerException;
import exceptions.SubscriberAlreadyConnectedException;
import exceptions.SubscriberAlreadySubbedException;

class PCADBrokerTest {

	private final PCADBrokerInterface server = new PCADBroker();
	private final PCADBrokerInterface serverToConnect = new PCADBroker();
	private final TopicInterface topicP = new Topic("Politica", "Italiana");
	private final ClientInterface client = new Client(server);


	@Test
	public void ConnectReturnsOk() {
		try {
			server.Connect(serverToConnect);
		} catch (RemoteException | SubscriberAlreadyConnectedException | SameBrokerException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void ConnectWithSameBrokerThowsException() {
		assertThrows(SameBrokerException.class, () -> server.Connect(server));
	}
	
	@Test
	public void DisconnectReturnsOk() {
		try {
			server.Connect(serverToConnect);
			server.Disconnect(serverToConnect);
		} catch (RemoteException | SubscriberAlreadyConnectedException | NonExistentSubException | SameBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void StopNotificationReturnsOk() {
		try {
			server.Connect(serverToConnect);
			server.StopNotification(serverToConnect);

		} catch (RemoteException | SubscriberAlreadyConnectedException | NonExistentSubException | SameBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void UnsubscribeReturnsOk() {
		try {
			server.Connect(serverToConnect);
			server.Subscribe(serverToConnect, topicP);
			server.Unsubscribe(serverToConnect, topicP);
		} catch (RemoteException | SubscriberAlreadyConnectedException | SubscriberAlreadySubbedException
				| NonExistentSubException | NonExistentTopicException | SameBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void SubscribeReturnsOk() {
		try {
			server.Connect(serverToConnect);
			server.Subscribe(serverToConnect, topicP);
		} catch (RemoteException | SubscriberAlreadyConnectedException | SubscriberAlreadySubbedException
				| NonExistentSubException | SameBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Questo test e' da rivedere
	 * BUG?
	 * |
	 * V**/
	@Test 
	public void PublishReturnsOk() {
		try {
			serverToConnect.Connect(server);
			serverToConnect.Subscribe(server, topicP);
			client.Connect(server);
			client.Subscribe(topicP);
			serverToConnect.PublishNews(new News(topicP, "ohoh"), topicP);
		} catch (RemoteException | SubscriberAlreadyConnectedException | NonExistentTopicException
				| SameBrokerException | SubscriberAlreadySubbedException | NonExistentSubException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}