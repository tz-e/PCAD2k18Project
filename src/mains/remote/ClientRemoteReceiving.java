package mains.remote;

import java.rmi.RemoteException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

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

public class ClientRemoteReceiving<Integer> implements Callable {
	private String clientName;
	private String myIp;
	private String serverIp;
	private String serverName;
	private int port;
	TopicInterface[] t;
	public ClientRemoteReceiving(String clientName, String myIp, String serverIp, String serverName,
			int port, TopicInterface... topic ) {
		this.clientName = clientName;
		this.myIp = myIp;
		this.serverIp = serverIp;
		this.serverName = serverName;
		this.port = port;
		t=topic;
	}
	@Override
	public Integer call() {
		
		ClientInterface client=new Client(myIp, serverIp, serverName, port);
		FutureTask<Integer> th;
		try {
			client.Connect();
			for(TopicInterface topic:t) {
				client.Subscribe(topic);	
			}
			th=(FutureTask<Integer>) client.ReadNews();
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
			Integer res= th.get();
			//client.Disconnect();//ritorno il numero di news ricevute
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
