package mains.remote;

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

public class ClientRemoteReceiving {

	public static void main(String[] args) {
		TopicInterface t=new Topic("C","A");
		ClientInterface client=new Client("192.168.1.19", "192.168.1.127", "S_REMOTE", 8000);
		
		try {

			client.Subscribe(t);
			Thread th=client.ReadNews();
			
			th.join();
			System.out.println("join done");
			client.Disconnect();
			System.out.println("disconnected");

		} catch (RemoteException | SubscriberAlreadySubbedException | NonExistentSubException  | InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("out of catch");

	}
}
