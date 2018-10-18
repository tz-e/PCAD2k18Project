package mains.remote;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import commons.ClientReceiving;
import commons.ClientSending;
import commons.Topic;
import commons.Utils;

public class RemoteTesterTwoTopic {
	public static void main(String[] args) {
		int nOfNews=20;
		ExecutorService pool=Executors.newFixedThreadPool(3);;
		FutureTask<Integer> receiveTask1=new FutureTask<Integer>(new ClientReceiving("Receiver 1", "192.168.1.127", "192.168.1.19", "S_REMOTE", 8000, new Topic("C","A"), new Topic("B","D")));
		Thread receiveThread1=new Thread(receiveTask1);
		// Creati i thread che riceveranno le news
		
		
		receiveThread1.start();
		// lanciati i thread riceventi
		
		try {
			TimeUnit.SECONDS.sleep(10); //aspetto 10 secondi prima di lanciare i thread che invieranno le news
			pool.submit(new ClientSending("Client1", nOfNews, "192.168.1.127", "192.168.1.19", "S_REMOTE", 8000, new Topic("C","A")));
			pool.submit(new ClientSending("Client2", nOfNews, "192.168.1.127", "192.168.1.19", "S_REMOTE", 8000, new Topic("B","D")));

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int res=0;
		try {
			res = receiveTask1.get().intValue();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(res==nOfNews*2) 
			//controllo che entrambi i risultati siano uguali, cosi' da vedere se effettivamente tutte le news previste sono state inviate e ricevute correttamente
			System.out.println("Tutto sembra essere andato okey.");
		else 
			System.out.println("Qualcosa e' andato storto.");
		Utils.shutdownAndAwaitTermination(pool);
	}
}
