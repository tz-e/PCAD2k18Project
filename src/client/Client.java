package client;

import broker.PCADBrokerInterface;
import commons.NewsInterface;
import commons.Topic;
import commons.TopicInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client implements ClientInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClientInterface stub;
	private PCADBrokerInterface server;
	private ConcurrentLinkedQueue<NewsInterface> NewsToRead;
	public Client() {
		NewsToRead=new ConcurrentLinkedQueue<NewsInterface>();
		try {
			System.setProperty("java.security.policy","file:./sec.policy");
			//System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Client/");
			if (System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());
			System.setProperty("java.rmi.server.hostname", "localhost");
			// Registry r = LocateRegistry.getRegistry("localhost",8000);
			Registry r = LocateRegistry.getRegistry(8000);
			server = (PCADBrokerInterface) r.lookup("REG");
			stub = (ClientInterface) UnicastRemoteObject.exportObject(this,0);
			// server.request(x,stub);

		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean Connect() {
		return server.Connect(stub);
	}

	@Override
	public boolean Disconnect() {
		return server.Disconnect(stub);
	}

	@Override
	public void StopNotification(ClientInterface client) {
		server.StopNotification(stub);
	}

	@Override
	public void Publish(NewsInterface news) throws Exception {
		server.PublishNews(news, news.GetTopic());
	}

	@Override
	public void Unsubscribe(TopicInterface topic) {
		server.Unsubscribe(stub, topic);
	}

	@Override
	public void notifyClient(NewsInterface news) throws RemoteException {
		synchronized(NewsToRead){
			NewsToRead.add(news);
		}
	}

	@Override
	public void Subscribe(Topic topic) {
		server.Subscribe(stub, topic);

	}



}
