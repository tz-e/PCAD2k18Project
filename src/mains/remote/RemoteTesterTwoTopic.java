package mains.remote;

import static commons.Utils.ipMainComputer;
import static commons.Utils.ipLenovo;
import static commons.Utils.SERVER_REMOTE;
import static commons.Utils.createNews;
import static commons.Utils.topicA;
import static commons.Utils.topicB;
import static commons.Utils.ipDell;

import static commons.Utils.port;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import commons.ClientReceiving;
import commons.ClientSending;
import commons.NewsInterface;
import commons.Utils;

public class RemoteTesterTwoTopic {
	public static void main(String[] args) {
		int nOfNews = 20;
		List<NewsInterface> initialNewsA = createNews(20, topicA, "News A numero: ");
		List<NewsInterface> initialNewsB = createNews(20, topicB, "News A numero: ");

		ExecutorService pool = Executors.newFixedThreadPool(3);
		;
		FutureTask<List<NewsInterface>> receiveTask1 = new FutureTask<List<NewsInterface>>(
				new ClientReceiving("Receiver 1", ipDell, ipLenovo, SERVER_REMOTE, port, topicA, topicB));
		Thread receiveThread1 = new Thread(receiveTask1);
		// Creati i thread che riceveranno le news

		receiveThread1.start();
		// lanciati i thread riceventi

		try {
			TimeUnit.SECONDS.sleep(10); // aspetto 10 secondi prima di lanciare i thread che invieranno le news
			pool.submit(new ClientSending("Client1", ipDell, ipLenovo, SERVER_REMOTE, port, topicA, initialNewsA));
			pool.submit(new ClientSending("Client2", ipDell, ipLenovo, SERVER_REMOTE, port, topicB, initialNewsB));

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<NewsInterface> res = null;
		try {
			res = receiveTask1.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;

		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;

		}
		if (res.size() != initialNewsA.size() + initialNewsB.size())
			System.out.println("Qualcosa e' andato storto");
		for (NewsInterface news : res) {
			if (!(initialNewsA.contains(news) || initialNewsB.contains(news)))
				System.out.println("Qualcosa e' andato storto");
		}
		System.out.println("Tutto sembra essere andato ok");
		Utils.shutdownAndAwaitTermination(pool);
	}
}
