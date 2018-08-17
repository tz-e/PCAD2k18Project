package client;

import broker.PCADBrokerInterface;
import commons.NewsInterface;
import commons.Topic;
import commons.TopicInterface;
import commons.Reader;

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
	private ClientInterface stub;
	private PCADBrokerInterface server;
	private ConcurrentLinkedQueue<NewsInterface> NewsToRead;

	public Client() {
		NewsToRead = new ConcurrentLinkedQueue<NewsInterface>();
		try {
			System.setProperty("java.security.policy", "file:./sec.policy");
			// System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Client/");
			if (System.getSecurityManager() == null)
				System.setSecurityManager(new SecurityManager());
			System.setProperty("java.rmi.server.hostname", "localhost");
			// Registry r = LocateRegistry.getRegistry("localhost",8000);
			Registry r = LocateRegistry.getRegistry(8000);
			server = (PCADBrokerInterface) r.lookup("REG");
			stub = (ClientInterface) UnicastRemoteObject.exportObject(this, 0);
			server.Connect(stub);

		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean Connect() throws RemoteException {
		return server.Connect(stub);
	}

	@Override
	public boolean Disconnect() throws RemoteException {
		return server.Disconnect(stub);
	}

	@Override
	public void StopNotification() throws RemoteException {
		server.StopNotification(stub);
	}

	@Override
	public void Publish(NewsInterface news) throws Exception {
		server.PublishNews(news, news.GetTopic());
	}

	@Override
	public void Unsubscribe(TopicInterface topic) throws RemoteException {
		server.Unsubscribe(stub, topic);
	}

	@Override
	public void notifyClient(NewsInterface news) throws RemoteException {
		if (news == null)
			System.out.println("Handshake ok!");
		else 
			NewsToRead.add(news);
			//System.out.println("Topic: " + news.GetTopic() + "\\n Testo: " + news.GetText());
		

	}

	@Override
	public void Subscribe(TopicInterface topic) throws RemoteException {
		server.Subscribe(stub, topic);
	}

	public void ReadNews() {
		Thread thr1 = new Thread(new Reader(NewsToRead));
		thr1.start();
	}
	

    @Override	
    public int hashCode() {
        return  11*NewsToRead.hashCode()*server.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Client))
            return false;
        if (obj == this)
            return true;

        Client cl = (Client) obj;
        return cl.NewsToRead.equals(NewsToRead) && cl.server.equals(server);
    }

}
