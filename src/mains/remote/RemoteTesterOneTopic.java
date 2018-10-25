package mains.remote;

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
import static commons.Utils.ipMainComputer;
import static commons.Utils.listsAreEqual;
import static commons.Utils.ipLenovo;
import static commons.Utils.ipDell;

import static commons.Utils.SERVER_REMOTE;
import static commons.Utils.createNews;
import static commons.Utils.topicA;
import static commons.Utils.port;
import static commons.Utils.LOCALHOST;

import commons.Utils;

public class RemoteTesterOneTopic {
	public static void main(String[] args) {
		int nOfNews = 20;
		ExecutorService pool = Executors.newFixedThreadPool(2);
		List<NewsInterface> initialNewsB = createNews(20, topicA, "News B numero: ");
		List<NewsInterface> initialNewsA = createNews(20, topicA, "News A numero: ");

		FutureTask<List<NewsInterface>> receiveTask1 = new FutureTask<List<NewsInterface>>(
				new ClientReceiving("Receiver 1", LOCALHOST, LOCALHOST, SERVER_REMOTE, port, topicA));
		Thread receiveThread1 = new Thread(receiveTask1);
		FutureTask<List<NewsInterface>> receiveTask2 = new FutureTask<List<NewsInterface>>(
				new ClientReceiving("Receiver 2", LOCALHOST, LOCALHOST, SERVER_REMOTE, port, topicA));
		Thread receiveThread2 = new Thread(receiveTask2);
		// Creati i thread che riceveranno le news

		receiveThread1.start();
		receiveThread2.start();
		// lanciati i due thread riceventi

		try {
			TimeUnit.SECONDS.sleep(10); // aspetto 10 secondi prima di lanciare i thread che invieranno le news
			pool.submit(new ClientSending("Client1", LOCALHOST, LOCALHOST, SERVER_REMOTE, port, topicA, initialNewsB));
			pool.submit(new ClientSending("Client2", LOCALHOST, LOCALHOST, SERVER_REMOTE, port, topicA, initialNewsA));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<NewsInterface> res1 = null;
		List<NewsInterface> res2 = null;
		try {
			res1 = receiveTask1.get();
			res2 = receiveTask2.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("res2: " + res2 + " res1: " + res1);
		boolean result = true;
		// if (listsAreEqual(res1,initialNews) &&listsAreEqual(res2,initialNews))
		// controllo che entrambi i risultati siano uguali, cosi' da vedere se
		// effettivamente tutte le news previste sono state inviate e ricevute
		// correttamente
		/*
		 * System.out.println("Tutto sembra essere andato okey."); else
		 * System.out.println("Qualcosa e' andato storto.");
		 */

		if (res1.size() != initialNewsA.size() + initialNewsB.size())
			result = false;
		for (NewsInterface news : res1)
			if (!(initialNewsA.contains(news) || initialNewsB.contains(news)))
				result = false;

		if (res2.size() != initialNewsA.size() + initialNewsB.size())
			result = false;
		for (NewsInterface news : res2)
			if (!(initialNewsA.contains(news) || initialNewsB.contains(news)))
				result = false;
			System.out.println(result ?"Tutto sembra essere andato okey.":"Qualcosa e' andato storto.");
		Utils.shutdownAndAwaitTermination(pool);
	}
}
