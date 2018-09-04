package commons;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * Runnable che ha come scopo quello di leggere il contenuto di una coda ogni
 * tot secondi e stamparne il contenuto
 * 
 * @author Daniele Atzeni
 **/
public class Reader extends Thread {
	private final ConcurrentLinkedQueue<NewsInterface> queue;
	private volatile boolean running;

	public Reader(ConcurrentLinkedQueue<NewsInterface> q) {
		running = true;
		queue = q;
	}

	public void run() {
		try {
			System.out.println("Comincio a leggere");
			long last = System.currentTimeMillis() / 1000;
			while ((System.currentTimeMillis() / 1000) - last < 10 && running) {// se non leggo niente per un minuto
																				// esco dal ciclo
				System.out.println("Current " + System.currentTimeMillis() / 1000 + " Last: " + last);
				TimeUnit.SECONDS.sleep(5);
				while (!queue.isEmpty()) {
					NewsInterface n = queue.poll();
					last = System.currentTimeMillis() / 1000; // mi salvo il tempo dell'ultima volta che ho letto
																// qualcosa
					System.out.println("Topic: " + n.GetTopic() + "\\n Testo: " + n.GetText());
				}

			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("Non ho altro da leggere");
		stopThread();
	}
	public void stopThread() {
		running=false;
		this.interrupt();
	}
}