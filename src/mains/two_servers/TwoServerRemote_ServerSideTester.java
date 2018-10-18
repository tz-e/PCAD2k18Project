package mains.two_servers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static commons.Utils.LOCALHOST;
import static commons.Utils.SERVER_SUBBED;
import static commons.Utils.SERVER_SENDING_NEWS;
import static commons.Utils.topicA;


import commons.Utils;

public class TwoServerRemote_ServerSideTester {
	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(2);

		try {
			pool.submit(new ServerSendingNews(LOCALHOST, SERVER_SENDING_NEWS, 8000, topicA));

			TimeUnit.SECONDS.sleep(5);
			pool.submit(new ServerSubbed(LOCALHOST, LOCALHOST, SERVER_SENDING_NEWS, SERVER_SUBBED, 8000, topicA));

			TimeUnit.MINUTES.sleep(5); // dopo 5 minuti chiudo i due server
			Utils.shutdownAndAwaitTermination(pool);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
