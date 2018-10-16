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

public class ClientRemoteSending1 {

	public static void main(String[] args) {
		TopicInterface t=new Topic("C","A");
		ClientInterface client=new Client("192.168.1.127", "192.168.1.19", "S_REMOTE", 8000);
		try {			
			client.Connect();
			//client.Subscribe(t);
			//Thread th=client.ReadNews();
			TimeUnit.SECONDS.sleep(10);

			for(int i=0;i<100;++i) {

				client.Publish(new News(t, "Client1 "+i));
				//TimeUnit.SECONDS.sleep(6);
			}
		/*	th.join();
			System.out.println("join done");
			client.Disconnect();
			System.out.println("disconnected");*/

		} catch (RemoteException e) {
			e.printStackTrace();
			return;
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		catch (NonExistentTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (SubscriberAlreadyConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("out of catch");

	}
}
