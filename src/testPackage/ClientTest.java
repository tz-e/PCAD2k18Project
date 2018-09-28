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
import exceptions.AlreadyConnectedException;
import exceptions.NonExistentSubException;
import exceptions.NonExistentTopicException;
import exceptions.NotConnectedException;
import exceptions.SubscriberAlreadyConnectedException;
import exceptions.SubscriberAlreadySubbedException;

public class ClientTest {
	private final PCADBrokerInterface server = new PCADBroker();
	private final ClientInterface client = new Client(server);
	private final TopicInterface topicP = new Topic("Politica", "Italiana");

	@Test
	public void ConnectReturnsOk() {
		try {
			client.Connect();
		} catch (RemoteException  | AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void ConnectTwiceThrowsException() {
		try {
			client.Connect();
			assertThrows(SubscriberAlreadyConnectedException.class, () -> client.Connect());
		} catch (RemoteException  | AlreadyConnectedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void DisconnectReturnsOk() {
		try {
			client.Connect();
			client.Disconnect();
		} catch (RemoteException  | NonExistentSubException | AlreadyConnectedException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void DisconnectTwiceThrowsException() {
		try {
			client.Connect();
			client.Disconnect();
			assertThrows(NonExistentSubException.class, () -> client.Disconnect());

		} catch (RemoteException | NonExistentSubException | AlreadyConnectedException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void StopNotificationReturnsOk() {
		try {
			client.Connect();
			client.StopNotification();

		} catch (RemoteException | NonExistentSubException | AlreadyConnectedException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void StopNotificationNoClientThrowsException() {
		try {
			client.Connect();
			client.Disconnect();
			assertThrows(NonExistentSubException.class, () -> client.StopNotification());
		} catch (RemoteException | NonExistentSubException | AlreadyConnectedException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void UnsubscribeReturnsOk() {
		try {

			client.Connect();
			client.Subscribe(topicP);
			client.Unsubscribe(topicP);
		} catch (RemoteException | SubscriberAlreadySubbedException
				| NonExistentSubException | NonExistentTopicException | AlreadyConnectedException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void UnsubscribeNoTopicThrowsException() {
		try {
			client.Connect();
			assertThrows(NonExistentTopicException.class, () -> client.Unsubscribe(topicP));
		} catch (RemoteException | AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void UnsubscribeNoSubThrowsException() {
		try {
			ClientInterface client2 = new Client(server);
			client2.Connect();
			client2.Subscribe(topicP);
			client.Connect();
			assertThrows(NonExistentSubException.class, () -> client.Unsubscribe(topicP));
		} catch (RemoteException | SubscriberAlreadySubbedException
				| NonExistentSubException | AlreadyConnectedException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void UnsubscribeNoTopicAfterOneUnsubsReturnsOk() {
		try {

			client.Connect();
			client.Subscribe(topicP);
			client.Unsubscribe(topicP);
			assertThrows(NonExistentTopicException.class, () -> client.Unsubscribe(topicP));
		} catch (RemoteException | SubscriberAlreadySubbedException
				| NonExistentSubException | NonExistentTopicException | AlreadyConnectedException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void SubscribeAlreadyExistingTopicReturnsOk() {
		try {
			ClientInterface client2 = new Client(server);
			client2.Connect();
			client2.Subscribe(topicP);
			client.Connect();
			client.Subscribe(topicP);
		} catch (RemoteException | SubscriberAlreadySubbedException
				| NonExistentSubException | AlreadyConnectedException | NotConnectedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void SubscribeNoTopicReturnsOk() {
		try {
			client.Connect();
			client.Subscribe(topicP);
		} catch (RemoteException | SubscriberAlreadySubbedException
				| NonExistentSubException | AlreadyConnectedException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void SubscribeAlreadySubbedThrowsException() throws SubscriberAlreadyConnectedException {
		try {
			client.Connect();
			client.Subscribe(topicP);
			assertThrows(SubscriberAlreadySubbedException.class, () -> client.Subscribe(topicP));
		} catch (RemoteException | SubscriberAlreadySubbedException
				| NonExistentSubException | AlreadyConnectedException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void SubscribeNonExistingSubThrowsException() {
		try {
			client.Connect();
			client.Disconnect();
			assertThrows(NonExistentSubException.class, () -> client.Subscribe(topicP));
		} catch (RemoteException | NonExistentSubException | AlreadyConnectedException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void PublishNoTopicThrowsException() {
		try {
			client.Connect();
			assertThrows(NonExistentTopicException.class, () -> client.Publish(new News(topicP, "ohoh")));
		} catch (RemoteException | AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void PublishReturnsOk() {
		try {
			client.Connect();
			client.Subscribe(topicP);
			client.Publish(new News(topicP, "ohoh"));
		} catch (RemoteException | NonExistentTopicException
				| SubscriberAlreadySubbedException | NonExistentSubException | AlreadyConnectedException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
