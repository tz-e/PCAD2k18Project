package client;

import broker.PCADBrokerInterface;
import commons.NewsInterface;
import commons.Topic;
import commons.TopicInterface;
import exceptions.NonExistentSubException;
import exceptions.NonExistentTopicException;
import exceptions.SubscriberAlreadyConnectedException;
import exceptions.SubscriberAlreadySubbedException;
import commons.Reader;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client implements ClientInterface {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Thread thr;
	private ClientInterface stub;
	private PCADBrokerInterface server;
	private ConcurrentLinkedQueue<NewsInterface> NewsToRead;

	public Client(PCADBrokerInterface broker) {
		server=broker;
		stub=this;
		NewsToRead = new ConcurrentLinkedQueue<NewsInterface>();
	}
	public Client(String serverToConnect) {
		NewsToRead = new ConcurrentLinkedQueue<NewsInterface>();
		try {
			System.setProperty("java.security.policy", "file:./sec.policy");
			// System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Client/");
			if (System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());
			System.setProperty("java.rmi.server.hostname", "localhost");
						//System.setProperty("java.rmi.server.hostname", "localhost");
			// Registry r = LocateRegistry.getRegistry("localhost",8000);
			Registry r = LocateRegistry.getRegistry("192.168.1.127",8000);
			server = (PCADBrokerInterface) r.lookup(serverToConnect);
			stub = (ClientInterface) UnicastRemoteObject.exportObject(this, 0);
			server.Connect(stub);

		} catch (RemoteException | NotBoundException | SubscriberAlreadyConnectedException e) {
			e.printStackTrace();
		}
	}
	
	public Client() {
		NewsToRead = new ConcurrentLinkedQueue<NewsInterface>();
		try {
			System.out.println(InetAddress.getLocalHost());
			System.setProperty("java.security.policy", "file:./sec.policy");
			// System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Client/");
			if (System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());
			//System.setProperty("java.rmi.server.hostname", "DESKTOP-R1IAP30/192.168.1.127");
						System.setProperty("java.rmi.server.hostname", "192.168.1.13");
			// Registry r = LocateRegistry.getRegistry("localhost",8000);
			//Registry r = LocateRegistry.getRegistry("DESKTOP-R1IAP30/192.168.1.127",8000);
						Registry r = LocateRegistry.getRegistry("192.168.1.127",8000);
			server = (PCADBrokerInterface) r.lookup("S_REMOTE");
			stub = (ClientInterface) UnicastRemoteObject.exportObject(this, 0);
			server.Connect(stub);

		} catch (RemoteException | NotBoundException | SubscriberAlreadyConnectedException | UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Connect() throws RemoteException, SubscriberAlreadyConnectedException {
		server.Connect(stub);
	}
	@Override
	public void Connect(PCADBrokerInterface server) throws RemoteException, SubscriberAlreadyConnectedException {
		server.Connect(stub);
		
	}
	@Override
	public void Disconnect() throws RemoteException, NonExistentSubException {
		server.Disconnect(stub);
		if(thr.isAlive()) thr.interrupt();
	}

	@Override
	public void StopNotification() throws RemoteException, NonExistentSubException {
		server.StopNotification(stub);
	}

	@Override
	public void Publish(NewsInterface news) throws NonExistentTopicException, RemoteException  {
		server.PublishNews(news, news.GetTopic());
	}

	@Override
	public void Unsubscribe(TopicInterface topic) throws RemoteException, NonExistentTopicException, NonExistentSubException {
		server.Unsubscribe(stub, topic);
	}

	@Override
	public void notifyClient(NewsInterface news) throws RemoteException {
		if (news == null)
			System.out.println("Handshake ok!");
		else {
			System.out.println("News Received by Server! - Client");
			NewsToRead.add(news);
		}
		// System.out.println("Topic: " + news.GetTopic() + "\\n Testo: " +
		// news.GetText());
	}

	@Override
	public void Subscribe(TopicInterface topic) throws RemoteException, SubscriberAlreadySubbedException, NonExistentSubException {
		server.Subscribe(stub, topic);
	}
	@Override
	public Thread ReadNews() throws RemoteException{
		thr= new Thread(new Reader(NewsToRead));
		thr.start();
		return thr;
	}

	@Override
	public int hashCode() {
		return 11 * NewsToRead.hashCode() ;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Client))
			return false;
		if (obj == this)
			return true;

		Client cl = (Client) obj;
		return cl.NewsToRead.equals(NewsToRead) && cl.server.equals(server) && cl.server.equals(server) && cl.stub.equals(stub);
	}

	@Override
	public String toString() {
		return "Client";
	}
	
}
