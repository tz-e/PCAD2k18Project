package mains.two_servers;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import commons.ClientReceiving;
import commons.ClientSending;
import commons.Topic;
import commons.TopicInterface;
import commons.Utils;
import static commons.Utils.LOCALHOST;
import static commons.Utils.port;
import static commons.Utils.SERVER_SUBBED;
import static commons.Utils.SERVER_SENDING_NEWS;
import static commons.Utils.topicA;

public class TwoServerTesterLocal {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(3);
		int res = 0;
		int nOfNews = 20;
		FutureTask<Integer> receiveTask = null;
		Thread receiveThread;
		try {
			pool.submit(new ServerSendingNews(LOCALHOST, SERVER_SENDING_NEWS, port, topicA));

			TimeUnit.SECONDS.sleep(5);

			pool.submit(new ServerSubbed(LOCALHOST, LOCALHOST, SERVER_SENDING_NEWS, SERVER_SUBBED, port, topicA));

			TimeUnit.SECONDS.sleep(5);

			receiveTask = new FutureTask<Integer>(
					new ClientReceiving<>("Receiver", LOCALHOST, LOCALHOST, SERVER_SUBBED, port, topicA));
			receiveThread = new Thread(receiveTask);
			// Creati i thread che riceveranno le news
			receiveThread.start();
			// Lanciati

			TimeUnit.SECONDS.sleep(5);
			pool.submit(
					new ClientSending("Sender", nOfNews = 20, LOCALHOST, LOCALHOST, SERVER_SENDING_NEWS, port, topicA));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			res = receiveTask.get().intValue();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (res == nOfNews)
			// controllo che entrambi i risultati siano uguali, cosi' da vedere se
			// effettivamente tutte le news previste sono state inviate e ricevute
			// correttamente
			System.out.println("Tutto sembra essere andato okey.");
		else
			System.out.println("Qualcosa e' andato storto.");
		Utils.shutdownAndAwaitTermination(pool);

	}

}
