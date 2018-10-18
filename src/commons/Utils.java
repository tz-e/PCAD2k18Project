package commons;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Questa e' la classe che contiene tutti i metodi di controllo comuni a tutte
 * le funzionalita'
 * 
 * @author Daniele Atzeni
 */
public class Utils {
	public static String NOT_CONECTED = "You are not connected to the Server";

	public static void checkIfNull(Object... objs) {
		if (objs == null)
			throw new IllegalArgumentException();
		for (Object obj : objs) {
			if (obj == null)
				throw new IllegalArgumentException();
		}
	}

	public static void shutdownAndAwaitTermination(ExecutorService pool) {
		pool.shutdown();
		// non accettare piu thread
		try {
			// aspetta 1m e poi chiudi tutti i thread
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				pool.shutdownNow();
				// aspetta ancora 1m e poi manda un msg di errore
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			return;
		}
	}

}
