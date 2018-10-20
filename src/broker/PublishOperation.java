package broker;

import java.util.List;

import client.ClientInterface;
import commons.NewsInterface;
import commons.SubInterface;

public class PublishOperation extends Thread {
	private List<SubInterface> subList;
	private NewsInterface news;
	public PublishOperation(List<SubInterface> subList, NewsInterface news) {
		this.subList=subList;
		this.news=news;
	}
	@Override
	public void run() {
		try {
			for (SubInterface sub : subList) {
				System.out.println("Sending news to a " + sub.toString());
				if (sub instanceof ClientInterface)
					((ClientInterface) sub).notifyClient(news);
				else
					((PCADBrokerInterface) sub).notifyBroker(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
