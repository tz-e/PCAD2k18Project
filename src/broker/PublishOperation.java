package broker;

import client.ClientInterface;
import commons.NewsInterface;

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

		for (Object sub : subList) {
			try {
				System.out.println("Sending news to a " + sub.toString());
				if (sub instanceof ClientInterface)
					((ClientInterface) sub).notifyClient(news);
				else
					((PCADBrokerInterface) sub).notifyBroker(news);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
