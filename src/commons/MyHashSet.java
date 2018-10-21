package commons;

import java.util.HashSet;

import exceptions.SubscriberAlreadySubbedException;

public class MyHashSet<V> extends HashSet<V>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Aggiunge l'elemento e elem e successivamente restituisce l'HashSet,
	 * se l'elemento era gia' presente viene lanciata un'eccezione
	 * @throws SubscriberAlreadySubbedException
	 */
	public MyHashSet<V> addAndReturn(V elem){
		super.add(elem);
		return this;
	}
	public MyHashSet (V e){
		super();
		super.add(e);
	}
}
