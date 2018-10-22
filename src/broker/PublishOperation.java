package broker;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import client.ClientInterface;
import commons.MySet;
import commons.NewsInterface;
import commons.SubInterface;
import exceptions.NonExistentTopicException;

public class PublishOperation implements Runnable {
	private Object[] subList;
	private NewsInterface news;

	public PublishOperation(Object[] subList, NewsInterface news) {
		System.out.println("Thread creato");

		this.subList = subList;
		this.news = news;
	}

	@Override
	public void run() {

		System.out.println("Number of subs: " + subList.length);
		try {
			for (Object sub : subList) {
				System.out.println("Sending news to a " + sub.toString());
				if (sub instanceof ClientInterface)
					((ClientInterface) sub).notifyClient(news);
				else
					((PCADBrokerInterface) sub).notifyBroker(news);
			}
		} catch (RemoteException | NonExistentTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
