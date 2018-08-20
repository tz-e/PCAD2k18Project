package commons;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * Runnable che ha come scopo quello di leggere il contenuto di una coda ogni
 * tot secondi e stamparne il contenuto
 * 
 * @author Daniele Atzeni
 **/
public class Reader implements Runnable {
	private final ConcurrentLinkedQueue<NewsInterface> queue;

	public Reader(ConcurrentLinkedQueue<NewsInterface> q) {
		queue = q;
	}

	public void run() {
		try {
			System.out.println("Comincio a leggere");
			while (true) {
				TimeUnit.SECONDS.sleep(5);
				while (!queue.isEmpty()) {
					NewsInterface n = queue.poll();
					System.out.println("Topic: " + n.GetTopic() + "\\n Testo: " + n.GetText());
				}
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

	}
}