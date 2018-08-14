package commons;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.PriorityQueue;
/**
 * Questa classe implementa una lista concorrente, oltre ai metodi get / size / 
 * add (int, T) / remove ho fatto anche l'override di add (T) per rendere alcune operazioni piu' pratiche
 * @author Daniele Atzeni
 */
//TODO Testare questa clsse
public class ConcurrentList<T> extends AbstractList<T> {
	private int maxSize = 10;
	private int size;
	private T[] arr;
	private PriorityQueue<Integer> freeIndexes;

	public ConcurrentList() {
		size = 0;
		arr = (T[]) new Object[maxSize];
		freeIndexes = new PriorityQueue<Integer>();
	}

	public ConcurrentList(T[] arr) {
		this.arr = arr;
		size = arr.length;
		maxSize = size * 2;
		freeIndexes = new PriorityQueue<Integer>();
	}

	@Override
	public T get(int index) {
		synchronized (this) {
			return arr[index];
		}
	}

	@Override
	public int size() {
		synchronized (this) {
			return size;
		}
	}

	@Override
	public void add(int i, T value) {
		synchronized (this) {
			if (arr[i] == null)
				++size;
			arr[i] = value;
		}
	}

	@Override
	public T remove(int i) {
		synchronized (this) {
			T toRemove = arr[i];
			arr[i] = null;
			freeIndexes.add(i);
			return toRemove;
		}
	}

	@Override
	public boolean add(T value) {
		/**
		 * se non ho ancora riempito l'array entro qui
		 **/
		if (size < maxSize) {
			/**
			 * se precedentemente ho cancellato una posizione intermedia il nuovo valore lo
			 * vado ad inserire quise precedentemente ho cancellato una posizione intermedia
			 * il nuovo valore lo vado ad inserire qui
			 **/
			if (!freeIndexes.isEmpty())
				this.add(freeIndexes.poll(), value);
			else {
				/**
				 * In caso contrario lo inserisco nell'ultima posizione e incremento il numero
				 * di elementi
				 **/
				int temp = findNextEmptyIndex();
				if (temp != -1)
					this.add(size, value);
				else
					return false; // qualcosa e' andato storto
				++size;
			}
			return true;
		}

		/**
		 * Quando la capienza massima e' stata raggiunta creo un nuovo array di
		 * dimensione raddoppiata
		 **/

		arr = Arrays.copyOf(arr, maxSize * 2);
		return true;
	}

	private int findNextEmptyIndex() {
		/**
		 * Questo metodo trova il primo indice libero a partire da size, visto che
		 * potrebbe essere stati inseriti dei valori successivi a size
		 **/
		for (int i = size + 1; i < arr.length; ++i)
			if (arr[i] == null)
				return i;
		return -1;
	}

}
