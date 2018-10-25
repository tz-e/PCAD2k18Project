package commons;

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import client.Client;
import client.ClientInterface;
import exceptions.AlreadyConnectedException;
import exceptions.NonExistentSubException;
import exceptions.NonExistentTopicException;
import exceptions.NotConnectedException;
import exceptions.SubscriberAlreadyConnectedException;
import exceptions.SubscriberAlreadySubbedException;

public class ClientReceiving implements Callable<List<NewsInterface>> {
	private String clientName;
	private String myIp;
	private String serverIp;
	private String serverName;
	private int port;
	TopicInterface[] t;
	public ClientReceiving(String clientName, String myIp, String serverIp, String serverName,
			int port, TopicInterface... topic ) {
		this.clientName = clientName;
		this.myIp = myIp;
		this.serverIp = serverIp;
		this.serverName = serverName;
		this.port = port;
		t=topic;
	}
	@Override
	public List<NewsInterface> call() {
		
		ClientInterface client=new Client(myIp, serverIp, serverName, port);
		FutureTask<List<NewsInterface>> th;
		try {
			client.Connect();
			for(TopicInterface topic:t) {
				client.Subscribe(topic);	
			}
			th=(FutureTask<List<NewsInterface>>) client.ReadNews();
			//System.out.println("disconnected");
			


		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		} catch (NotConnectedException e) {
			e.printStackTrace();
			return null;
		}catch (SubscriberAlreadySubbedException e) {
			e.printStackTrace();			
			return null;
		}catch (NonExistentSubException e) {
			e.printStackTrace();
			return null;
		}catch (SubscriberAlreadyConnectedException e) {
			e.printStackTrace();
			return null;
		} catch (AlreadyConnectedException e) {
			e.printStackTrace();
			return null;
		} 
		try {
			List<NewsInterface> res= th.get();
			System.out.println("Letture terminate da "+ clientName);
			return res;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		} /*catch (RemoteException e) {
			e.printStackTrace();
			return null;
		} catch (NonExistentSubException e) {
			e.printStackTrace();
			return null;
		} catch (NotConnectedException e) {
			e.printStackTrace();
			return null;
		}*/
	}
}
