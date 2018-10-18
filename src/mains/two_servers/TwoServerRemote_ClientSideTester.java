package mains.two_servers;

import static commons.Utils.SERVER_SENDING_NEWS;
import static commons.Utils.SERVER_SUBBED;
import static commons.Utils.port;
import static commons.Utils.ipMainComputer;
import static commons.Utils.ipDell;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import commons.ClientReceiving;
import commons.ClientSending;
import commons.Topic;
import commons.TopicInterface;

public class TwoServerRemote_ClientSideTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TopicInterface topic = new Topic("A", "B");
		FutureTask<Integer> receiveTask = null;
		Thread receiveThread;
		Thread sendThread;
		int res = 0;
		int nOfNews = 20;

		try {
			receiveTask = new FutureTask<Integer>(
					new ClientReceiving("Receiver", ipMainComputer, ipDell, SERVER_SUBBED, port, topic));
			receiveThread = new Thread(receiveTask);
			// Creati i thread che riceveranno le news
			receiveThread.start();

			TimeUnit.SECONDS.sleep(5);
			// Aspetto 5 secondi prima di far partire 
			sendThread = new ClientSending("Sender", nOfNews, ipMainComputer, ipDell, SERVER_SENDING_NEWS, port,
					topic);
			sendThread.start();
			
			res = receiveTask.get().intValue();
			//Recupero il numero di news lette
			
			if (res == nOfNews)
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
