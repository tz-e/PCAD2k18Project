package mains;

import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

import client.Client;
import client.ClientInterface;
import commons.News;
import commons.Topic;
import commons.TopicInterface;
import exceptions.NonExistentSubException;
import exceptions.NonExistentTopicException;
import exceptions.SubscriberAlreadySubbedException;

public class ClientRemote {

	public static void main(String[] args) {
		TopicInterface t=new Topic("C","A");
		ClientInterface client=new Client();
		try {
			client.Subscribe(t);
			Thread th=client.ReadNews();
			for(int i=0;i<10;++i) {
				
				client.Publish(new News(t, "uhu"));
				TimeUnit.SECONDS.sleep(2);

			}
			th.join();
			System.out.println("join done");
			client.Disconnect();
			System.out.println("disconnected");

		} catch (RemoteException | SubscriberAlreadySubbedException | NonExistentSubException | NonExistentTopicException | InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("out of catch");

	}
}
