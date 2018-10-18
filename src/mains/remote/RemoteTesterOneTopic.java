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

public class RemoteTesterOneTopic {
	public static void main(String[] args) {
		int nOfNews=200;
		ExecutorService pool=Executors.newFixedThreadPool(2);


		FutureTask<Integer> receiveTask1=new FutureTask<Integer>(new ClientReceiving("Receiver 1", "192.168.1.127", "192.168.1.19", "S_REMOTE", 8000, new Topic("C","A")));
		Thread receiveThread1=new Thread(receiveTask1);
		FutureTask<Integer> receiveTask2=new FutureTask<Integer>(new ClientReceiving("Receiver 2", "192.168.1.127", "192.168.1.19", "S_REMOTE", 8000, new Topic("C","A")));
		Thread receiveThread2=new Thread(receiveTask2);
		// Creati i thread che riceveranno le news
		
		
			
		receiveThread1.start();
		receiveThread2.start();
		// lanciati i due thread riceventi
		
		try {			

			TimeUnit.SECONDS.sleep(10); //aspetto 10 secondi prima di lanciare i thread che invieranno le news
			pool.submit(new ClientSending("Client1", nOfNews, "192.168.1.127", "192.168.1.19", "S_REMOTE", 8000, new Topic("C","A")));
			pool.submit(new ClientSending("Client2", nOfNews, "192.168.1.127", "192.168.1.19", "S_REMOTE", 8000, new Topic("B","D")));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Integer res1=null;
		Integer res2=null;
		try {
			res1 = receiveTask1.get();
			res2=receiveTask2.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("res2: "+res2+" res1: "+res1);
		if(!res2.equals(new Integer(0)) && !res1.equals(new Integer(0)) && res2.equals(res1)) 
			//controllo che entrambi i risultati siano uguali, cosi' da vedere se effettivamente tutte le news previste sono state inviate e ricevute correttamente
			
			System.out.println("Tutto sembra essere andato okey.");
		else 
			System.out.println("Qualcosa e' andato storto.");
		Utils.shutdownAndAwaitTermination(pool);

	}
	
}