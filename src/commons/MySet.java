package commons;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import exceptions.SubscriberAlreadySubbedException;

public class MySet<K> {

	private KeySetView<Object, Boolean> set;

	public MySet() {
		set = ConcurrentHashMap.newKeySet();
	}

	public MySet(SubInterface sub) {
		this();
		set.add(sub);
		
	}
	public int size() {
		return set.size();
	}
	/**
	 * Aggiunge l'elemento e elem e successivamente restituisce l'HashSet, se
	 * l'elemento era gia' presente viene lanciata un'eccezione
	 * 
	 * @throws SubscriberAlreadySubbedException
	 */
	public MySet<K> addAndReturn(K elem) {
		/**
		 * Questo metodo ritorna null se l'oggetto da inserire era gia' presente nella
		 * collezione, la collezione altrimenti
		 */
		return set.add(elem) ? this : null;

	}

	public MySet<K> removeAndReturn(K elem) {
		/**
		 * Questo metodo ritorna null se l'oggetto da cancellare non era presente nella
		 * collezione, la collezione altrimenti
		 */
		return set.remove(elem) ? this : null;
	}
	public boolean remove(SubInterface sub) {
		return set.remove(sub);
	}
	public Object []values() {
		return  set.toArray();
	}

}
