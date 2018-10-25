package mains.two_servers;

import static commons.Utils.SERVER_SENDING_NEWS;
import static commons.Utils.SERVER_SUBBED;
import static commons.Utils.port;
import static commons.Utils.ipMainComputer;
import static commons.Utils.ipDell;
import static commons.Utils.ipLenovo;
import static commons.Utils.topicA;
import static commons.Utils.LOCALHOST;
import static commons.Utils.createNews;
import static commons.Utils.listsAreEqual;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import commons.ClientReceiving;
import commons.ClientSending;
import commons.NewsInterface;
import commons.Topic;
import commons.TopicInterface;

public class TwoServerRemote_ClientSideTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FutureTask<List<NewsInterface>> receiveTask = null;
		Thread receiveThread;
		Thread sendThread;
		int noOfNews=20;
		List<NewsInterface> res;
		List<NewsInterface> initialNews=createNews(20, topicA, "News A numero: ");

		int nOfNews = 20;

		try {
			receiveTask = new FutureTask<List<NewsInterface>>(
					new ClientReceiving("Receiver", LOCALHOST, LOCALHOST, SERVER_SUBBED, port, topicA));
			receiveThread = new Thread(receiveTask);
			// Creati i thread che riceveranno le news
			receiveThread.start();

			TimeUnit.SECONDS.sleep(5);
			// Aspetto 5 secondi prima di far partire 
			sendThread = new ClientSending("Sender", LOCALHOST, LOCALHOST, SERVER_SENDING_NEWS, port,
					topicA, initialNews);
			sendThread.start();
			
			//res = 
			//Recupero il numero di news lette
			
			if (listsAreEqual(receiveTask.get(),initialNews))
				//Controllo che il numero di news lette e inviate sia lo stesso
				System.out.println("Tutto sembra essere andato okey.");
			else
				System.out.println("Qualcosa e' andato storto.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
