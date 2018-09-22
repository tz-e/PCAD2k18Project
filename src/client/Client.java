package client;

import broker.PCADBrokerInterface;
import commons.NewsInterface;
import commons.Topic;
import commons.TopicInterface;
import exceptions.AlreadyConnectedException;
import exceptions.NonExistentSubException;
import exceptions.NonExistentTopicException;
import exceptions.NotConnectedException;
import exceptions.SubscriberAlreadyConnectedException;
import exceptions.SubscriberAlreadySubbedException;
import commons.Reader;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Client implements ClientInterface {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Thread thr;
	private ClientInterface stub;
	private PCADBrokerInterface server;
	private LinkedBlockingQueue<NewsInterface> NewsToRead;
	private volatile boolean connected;
	public Client(PCADBrokerInterface broker) {
		thr=null;
		server=broker;
		stub=this;
		NewsToRead = new LinkedBlockingQueue<NewsInterface>();
		connected=false;
	}
	public Client(String serverToConnect) {
		try {
			/*System.setProperty("java.security.policy", "file:./sec.policy");
			// System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Client/");
			if (System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());
			System.setProperty("java.rmi.server.hostname", "localhost");
						//System.setProperty("java.rmi.server.hostname", "localhost");
			// Registry r = LocateRegistry.getRegistry("localhost",8000);
			Registry r = LocateRegistry.getRegistry("192.168.1.127",8000);
			server = (PCADBrokerInterface) r.lookup(serverToConnect);
			stub = (ClientInterface) UnicastRemoteObject.exportObject(this, 0);
			server.Connect(stub);*/
			initializeClient("localhost", "localhost", serverToConnect,8000);

		} catch (RemoteException | NotBoundException | SubscriberAlreadyConnectedException | UnknownHostException e) {
			e.printStackTrace();
		}
	}
	public Client(String myIp, String serverIp, String serverName, int port) {
		try {
			initializeClient(myIp, serverIp, serverName, port);
		} catch (UnknownHostException | RemoteException | NotBoundException | SubscriberAlreadyConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Client() {
		try {
			initializeClient("localhost", "localhost", "nameServer", 8000);

		} catch (RemoteException | NotBoundException | SubscriberAlreadyConnectedException | UnknownHostException e) {
			e.printStackTrace();
		}
	}
	private void initializeClient(String myIp, String serverIp, String serverName, int port) throws UnknownHostException, RemoteException, NotBoundException, AccessException,
			SubscriberAlreadyConnectedException {
		NewsToRead = new LinkedBlockingQueue<NewsInterface>();

		System.out.println(InetAddress.getLocalHost());
		System.setProperty("java.security.policy", "file:./sec.policy");
		// System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Client/");
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		//System.setProperty("java.rmi.server.hostname", "DESKTOP-R1IAP30/192.168.1.127");
					System.setProperty("java.rmi.server.hostname", myIp);
		// Registry r = LocateRegistry.getRegistry("localhost",8000);
		//Registry r = LocateRegistry.getRegistry("DESKTOP-R1IAP30/192.168.1.127",8000);
					Registry r = LocateRegistry.getRegistry(serverIp,port);
		server = (PCADBrokerInterface) r.lookup(serverName);
		stub = (ClientInterface) UnicastRemoteObject.exportObject(this, 0);
		//server.Connect(stub);
	}

	@Override
	public void Connect() throws RemoteException, SubscriberAlreadyConnectedException, AlreadyConnectedException {
		actualConnect(server);		

	}
	@Override
	public void Connect(PCADBrokerInterface server) throws RemoteException, SubscriberAlreadyConnectedException, AlreadyConnectedException {
		actualConnect(server);		
	}
	private void actualConnect(PCADBrokerInterface server) throws RemoteException, SubscriberAlreadyConnectedException, AlreadyConnectedException  {
		isConnected();
		try{
			server.Connect(stub);
		}
		catch(RemoteException | SubscriberAlreadyConnectedException e) {
			throw e;
		}
		connected=true;
	}
	private void isConnected() throws AlreadyConnectedException {
		if(connected) throw new AlreadyConnectedException();
	}

	@Override
	public void Disconnect() throws RemoteException, NonExistentSubException, NotConnectedException {
		notConnected();
		server.Disconnect(stub);
		if(thr.isAlive()) thr.interrupt();
	}

	@Override
	public void StopNotification() throws RemoteException, NonExistentSubException, NotConnectedException {
		notConnected();
		server.StopNotification(stub);
	}

	@Override
	public void Publish(NewsInterface news) throws NonExistentTopicException, RemoteException, NotConnectedException  {
		notConnected();
		server.PublishNews(news, news.GetTopic());
	}

	@Override
	public void Unsubscribe(TopicInterface topic) throws RemoteException, NonExistentTopicException, NonExistentSubException, NotConnectedException {
		notConnected();
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
	public void Subscribe(TopicInterface topic) throws RemoteException, SubscriberAlreadySubbedException, NonExistentSubException, NotConnectedException {
		notConnected();
		server.Subscribe(stub, topic);
	}
	@Override
	public Thread ReadNews() throws RemoteException, NotConnectedException{
		notConnected();
		if(thr!=null) thr.interrupt();
		
		thr = new Thread(new Reader(NewsToRead));
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
	
	private void notConnected() throws NotConnectedException {
		if(connected) throw new NotConnectedException();
	}
}
