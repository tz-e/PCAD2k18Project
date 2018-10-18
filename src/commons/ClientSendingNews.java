package commons;

import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

import client.Client;
import exceptions.AlreadyConnectedException;

public class ClientSendingNews extends Thread {

	public void run(){
		Client client = new Client("SERVER_SENDING_NEWS");
		try {
			client.Connect();
		} catch (RemoteException | AlreadyConnectedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Let's start sending news!");
		
		TopicInterface topic = new Topic("A", "B");
		for (int i = 0; i < 10; ++i) {
			NewsInterface news = new News(topic, "LUL" + i);
			try {
				
				System.out.println("News n^: " + i);
				client.Publish(news);
				TimeUnit.SECONDS.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
