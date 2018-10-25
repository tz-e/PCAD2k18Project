package commons;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Reader che ha come scopo quello di leggere il contenuto di una coda ogni tot
 * secondi e stamparne il contenuto
 * 
 * @author Daniele Atzeni
 **/
public class Reader implements Callable<List<NewsInterface>> {
	private final LinkedBlockingQueue<NewsInterface> queue;
	private volatile boolean running;
	List<NewsInterface> l;

	public Reader(LinkedBlockingQueue<NewsInterface> q) {
		running = false;
		queue = q;
		l = new LinkedList<NewsInterface>();
	}

	@Override
	public List<NewsInterface> call() {
		running = true;

		try {
			Thread.sleep(10);

			System.out.println("Comincio a leggere");
			long last = System.currentTimeMillis() / 1000;
			while ((System.currentTimeMillis() / 1000) - last < 30 && running) {// se non leggo niente per un minuto
																				// esco dal ciclo
				// System.out.println("Current " + System.currentTimeMillis() / 1000 + " Last: "
				// + last);
				Thread.sleep(5);
				while (!queue.isEmpty()) {
					NewsInterface n = queue.take();
					l.add(n);
					last = System.currentTimeMillis() / 1000; // mi salvo il tempo dell'ultima volta che ho letto
																// qualcosa
					System.err.println("Topic: " + n.GetTopic() + "\\n Testo: " + n.GetText());
				}

			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return null;
		}
		System.out.println("Ho letto " + l.size() + " news");
		stopThread();
		return l;
	}

	public void stopThread() {
		running = false;
	}

}