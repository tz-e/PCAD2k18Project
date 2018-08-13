package client;

import broker.PCADBrokerInterface;
import commons.NewsInterface;
import commons.SubInterface;
import commons.Topic;
import commons.TopicInterface;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client  implements ClientInterface, SubInterface {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClientInterface stub;
    private PCADBrokerInterface server;
    public Client() {
	try {
		//System.setProperty("java.security.policy","file:./sec.policy");
		//System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Client/");
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname","localhost");
		//Registry r = LocateRegistry.getRegistry("localhost",8000);
		Registry r = LocateRegistry.getRegistry(8000);
		server = (PCADBrokerInterface) r.lookup("REG");
		stub = (ClientInterface) UnicastRemoteObject.exportObject(this,0);
		//server.request(x,stub);
		
		} catch (RemoteException | NotBoundException e) {
		e.printStackTrace();
		}
}
	@Override
	public boolean Connect() {
		server.Connect(stub); 
		/*TODO questo metodo, come anche Disconnect ha bisogno di un try/catch per vedere se tutto va a buon fine*/
		return true;
	}

	@Override
	public boolean Disconnect() {
		server.Disconnect(stub);
		return true;
	}

	@Override
	public void StopNotification(ClientInterface client) {
		server.StopNotification(stub);
	}

	@Override
	public void Publish(NewsInterface news) {
		server.PublishNews(news, news.GetTopic());
	}

	@Override
	public void Unsubscribe(TopicInterface topic) {
		server.Unsubscribe(stub, topic);
	}
	@Override
	public void notifyClient() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void Subscribe(Topic topic) {
		server.Subscribe(stub, topic);
		
	}
	
}
