package mains.remote;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static commons.Utils.LOCALHOST;
import static commons.Utils.SERVER_SUBBED;
import static commons.Utils.SERVER_SENDING_NEWS;
import static commons.Utils.ipDell;
import static commons.Utils.ipMainComputer;

import commons.Topic;
import commons.TopicInterface;
import commons.Utils;
import mains.local.ServerSendingNews;
import mains.local.ServerSubbed;

public class TwoServerRemote_ServerSideTester {
	public static void main(String[] args) {
		TopicInterface topic = new Topic("A", "B");
		ExecutorService pool = Executors.newFixedThreadPool(2);

		try {
			pool.submit(new ServerSendingNews(ipDell, SERVER_SENDING_NEWS, 8000, topic));

			TimeUnit.SECONDS.sleep(5);
			pool.submit(new ServerSubbed(ipDell, ipDell, SERVER_SENDING_NEWS, SERVER_SUBBED, 8000, topic));

			TimeUnit.MINUTES.sleep(5); // dopo 5 minuti chiudo i due server
			Utils.shutdownAndAwaitTermination(pool);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
