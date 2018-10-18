package mains.remote;

import java.rmi.RemoteException;
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

public class ClientRemoteSending extends Thread {
	private int nOfNews;
	private String clientName;
	private String myIp;
	private String serverIp;
	private String serverName;
	private int port;
	private TopicInterface t;

	public ClientRemoteSending(String clientName, int nOfNews, String myIp, String serverIp, String serverName,
			int port, TopicInterface t) {
		this.nOfNews = nOfNews;
		this.clientName = clientName;
		this.myIp = myIp;
		this.serverIp = serverIp;
		this.serverName = serverName;
		this.port = port;
		this.t=t;
	}

	public void run() {
		ClientInterface client = new Client(myIp, serverIp, serverName, port);
		try {
			client.Connect();
			TimeUnit.SECONDS.sleep(10);

			for (int i = 0; i < nOfNews; ++i) {
				client.Publish(new News(t, clientName + i));
				TimeUnit.SECONDS.sleep(1);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return;
		} catch (NotConnectedException e) {
			e.printStackTrace();
			return;
		} catch (NonExistentTopicException e) {
			e.printStackTrace();
			return;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		} catch (SubscriberAlreadyConnectedException e) {
			e.printStackTrace();
			return;
		} catch (AlreadyConnectedException e) {
			e.printStackTrace();
			return;
		}
		System.out.println("Sending news finished by " + clientName);
		return;
	}

}
