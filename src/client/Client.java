package client;

import broker.PCADBrokerInterface;
import commons.NewsInterface;
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
import java.util.List;
import java.util.concurrent.FutureTask;
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
		initializeFields(null, broker, this, new LinkedBlockingQueue<NewsInterface>(), false);
	}

	private void initializeFields(Reader thr, PCADBrokerInterface broker, ClientInterface stub,
			LinkedBlockingQueue<NewsInterface> NewsToRead, boolean connected) {
		this.thr = null;
		this.server = broker;
		this.stub = stub;
		this.NewsToRead = NewsToRead;
		this.connected = connected;
	}

	public Client(String serverToConnect) {
		try {
			initializeClient("localhost", "localhost", serverToConnect, 8000);

		} catch (RemoteException | NotBoundException | SubscriberAlreadyConnectedException | UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public Client(String myIp, String serverIp, String serverName, int port) {
		try {
			initializeClient(myIp, serverIp, serverName, port);
		} catch (UnknownHostException | RemoteException | NotBoundException | SubscriberAlreadyConnectedException e) {
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

	private void initializeClient(String myIp, String serverIp, String serverName, int port)
			throws UnknownHostException, RemoteException, NotBoundException, AccessException,
			SubscriberAlreadyConnectedException {
		NewsToRead = new LinkedBlockingQueue<NewsInterface>();

		System.out.println(InetAddress.getLocalHost());
		System.setProperty("java.security.policy", "file:./sec.policy");
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname", myIp);
		Registry r = LocateRegistry.getRegistry(serverIp, port);

		initializeFields(null, (PCADBrokerInterface) r.lookup(serverName),
				(ClientInterface) UnicastRemoteObject.exportObject(this, 0), new LinkedBlockingQueue<NewsInterface>(),
				false);
		// server.Connect(stub);
	}

	@Override
	public void Connect() throws RemoteException, SubscriberAlreadyConnectedException, AlreadyConnectedException {
		actualConnect(server);
	}

	@Override
	public void Connect(PCADBrokerInterface server)
			throws RemoteException, SubscriberAlreadyConnectedException, AlreadyConnectedException {
		actualConnect(server);
	}

	private void actualConnect(PCADBrokerInterface server)
			throws RemoteException, SubscriberAlreadyConnectedException, AlreadyConnectedException {
		isConnected();
		try {
			server.Connect(stub);
		} catch (RemoteException | SubscriberAlreadyConnectedException e) {
			throw e;
		}
		connected = true;
	}

	private void isConnected() throws AlreadyConnectedException {
		if (connected)
			throw new AlreadyConnectedException();
	}

	@Override
	public void Disconnect() throws RemoteException, NonExistentSubException, NotConnectedException {
		notConnected();
		server.Disconnect(stub);
		if (thr!=null && thr.isAlive())
			thr.interrupt();
		connected=false;
	}

	@Override
	public void StopNotification() throws RemoteException, NonExistentSubException, NotConnectedException {
		notConnected();
		//server.StopNotification(stub);
		thr.interrupt();
		thr=null;
	}

	@Override
	public void Publish(NewsInterface news) throws NonExistentTopicException, RemoteException, NotConnectedException {
		notConnected();
		server.PublishNews(news, news.GetTopic());
	}

	@Override
	public void Unsubscribe(TopicInterface topic)
			throws RemoteException, NonExistentTopicException, NonExistentSubException, NotConnectedException {
		notConnected();
		server.Unsubscribe(stub, topic);
	}

	@Override
	public void notifyClient(NewsInterface news) throws RemoteException {

		if (news == null)
			System.out.println("Handshake ok!");
		else {
			//System.err.println("News Received by Server! - Client");
			NewsToRead.add(news);
		}
		// System.out.println("Topic: " + news.GetTopic() + "\\n Testo: " +
		// news.GetText());
	}

	@Override
	public void Subscribe(TopicInterface topic)
			throws RemoteException, SubscriberAlreadySubbedException, NonExistentSubException, NotConnectedException {
		notConnected();
		server.Subscribe(stub, topic);
	}

	@Override
	public FutureTask<List<NewsInterface>> ReadNews() throws RemoteException, NotConnectedException {
		notConnected();
		if (thr != null)
			thr.interrupt();
		FutureTask<List<NewsInterface>> task=new FutureTask<List<NewsInterface>>(new Reader(NewsToRead));
		thr = new Thread(task);
		thr.start();
		return task;
	}
	
	@Override
	public void stopReadNews() throws RemoteException, NotConnectedException {
		notConnected();

		if(thr!=null) {
			thr.interrupt();
			thr=null;
		}
	}
	@Override
	public int hashCode() {
		return 11 * NewsToRead.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Client))
			return false;
		if (obj == this)
			return true;

		Client cl = (Client) obj;
		return cl.NewsToRead.equals(NewsToRead) && cl.server.equals(server) && cl.server.equals(server)
				&& cl.stub.equals(stub);
	}

	@Override
	public String toString() {
		return "Client";
	}

	private void notConnected() throws NotConnectedException {
		if (!connected)
			throw new NotConnectedException();
	}

	
}
