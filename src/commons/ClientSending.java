package commons;//  
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import client.Client;
import client.ClientInterface;
import exceptions.AlreadyConnectedException;
import exceptions.NonExistentSubException;
import exceptions.NonExistentTopicException;
import exceptions.NotConnectedException;
import exceptions.SubscriberAlreadyConnectedException;
import exceptions.SubscriberAlreadySubbedException;

public class ClientSending extends Thread {
	private String clientName;
	private String myIp;
	private String serverIp;
	private String serverName;
	private int port;
	private TopicInterface t;
	List<NewsInterface> newsList;
	public ClientSending(String clientName,  String myIp, String serverIp, String serverName, int port,
			TopicInterface t, List<NewsInterface> news) {
		this.clientName = clientName;
		this.myIp = myIp;
		this.serverIp = serverIp;
		this.serverName = serverName;
		this.port = port;
		this.t = t;
		this.newsList=news;
	}

	public void run() {
		ClientInterface client = new Client(myIp, serverIp, serverName, port);
		try {
			client.Connect();
			TimeUnit.SECONDS.sleep(10);

			for (NewsInterface news:newsList) {
				client.Publish(news);
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
		System.out.println(clientName + " ha finito di inviare news");
		return;
	}
}
