package mains.two_servers;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import commons.ClientReceiving;
import commons.ClientSending;
import commons.NewsInterface;
import commons.Topic;
import commons.TopicInterface;
import commons.Utils;
import static commons.Utils.LOCALHOST;
import static commons.Utils.port;
import static commons.Utils.SERVER_SUBBED;
import static commons.Utils.createNews;
import static commons.Utils.listsAreEqual;
import static commons.Utils.SERVER_SENDING_NEWS;
import static commons.Utils.topicA;

public class TwoServerTesterLocal {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(3);
		List<NewsInterface> res = null;
		int nOfNews = 20;
		FutureTask<List<NewsInterface>> receiveTask = null;
		Thread receiveThread;
		List<NewsInterface> initialNews=createNews(20, topicA,"News A numero: ");

		try {
			pool.submit(new ServerSendingNews(LOCALHOST, SERVER_SENDING_NEWS, port, topicA));

			TimeUnit.SECONDS.sleep(5);

			pool.submit(new ServerSubbed(LOCALHOST, LOCALHOST, SERVER_SENDING_NEWS, SERVER_SUBBED, port, topicA));

			TimeUnit.SECONDS.sleep(5);

			receiveTask = new FutureTask<List<NewsInterface>>(
					new ClientReceiving("Receiver", LOCALHOST, LOCALHOST, SERVER_SUBBED, port, topicA));
			receiveThread = new Thread(receiveTask);
			// Creati i thread che riceveranno le news
			receiveThread.start();
			// Lanciati

			TimeUnit.SECONDS.sleep(5);
			pool.submit(
					new ClientSending("Sender", LOCALHOST, LOCALHOST, SERVER_SENDING_NEWS, port, topicA, initialNews));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			res = receiveTask.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (listsAreEqual(res,initialNews))
			// controllo che entrambi i risultati siano uguali, cosi' da vedere se
			// effettivamente tutte le news previste sono state inviate e ricevute
			// correttamente
			System.out.println("Tutto sembra essere andato okey.");
		else
			System.out.println("Qualcosa e' andato storto.");
		Utils.shutdownAndAwaitTermination(pool);

	}

}
