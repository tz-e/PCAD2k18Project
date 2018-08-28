package mains;

import java.rmi.RemoteException;

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
			client.ReadNews();
			client.Publish(new News(t, "uhu"));
		} catch (RemoteException | SubscriberAlreadySubbedException | NonExistentSubException | NonExistentTopicException e) {
			e.printStackTrace();
		}
	}

}
