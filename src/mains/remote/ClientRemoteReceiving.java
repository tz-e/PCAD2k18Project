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
import exceptions.NotConnectedException;
import exceptions.SubscriberAlreadySubbedException;

public class ClientRemoteReceiving {

	public static void main(String[] args) {
		TopicInterface t=new Topic("C","A");
		ClientInterface client=new Client("192.168.1.127", "192.168.1.19", "S_REMOTE", 8000);
		
		try {

			client.Subscribe(t);
			Thread th=client.ReadNews();
			
			th.join();
			System.out.println("join done");
			client.Disconnect();
			System.out.println("disconnected");

		} catch (RemoteException e) {
			e.printStackTrace();
			return;
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}catch (SubscriberAlreadySubbedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			return;
		}catch (NonExistentSubException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		System.out.println("out of catch");

	}
}
