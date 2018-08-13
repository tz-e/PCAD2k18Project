package commons;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Spliterator;
import java.util.function.UnaryOperator;


//TODO rendere effettivamente questa classe concorrente
public  class ConcurrentList<T> extends AbstractList<T>{
	private int maxSize=10;
	private int size;
	private T[] arr;
	private PriorityQueue<Integer> freeIndexes;
	public ConcurrentList(){
		size=0;
		arr=(T[]) new Object[maxSize];
		freeIndexes=new PriorityQueue<Integer>();
	}
	public ConcurrentList(T[] arr) {
		this.arr=arr;
		size=arr.length; 
		maxSize=size*2;
		freeIndexes=new PriorityQueue<Integer>();
	}
	@Override
	public T get(int index) {
		return arr[index];
	}

	@Override
	public int size() {
		return size;
	}
	@Override
	public void add(int i, T value) {
		if(arr[i]==null) ++size;
		arr[i]=value;
	}
	@Override
	public T remove(int i) {
		T toRemove=arr[i];
		arr[i]=null;
		freeIndexes.add(i);
		return toRemove;
	}
	@Override 
	public boolean add(T value) {
	/**se non ho ancora l'array entro qui**/
		if(size<maxSize){ 
	/**
	 * se precedentemente ho cancellato una posizione intermedia il nuovo valore lo vado ad 
	 * inserire quise precedentemente ho cancellato una posizione intermedia 
	 * il nuovo valore lo vado ad inserire qui
	**/			
			if(!freeIndexes.isEmpty())
				this.add(freeIndexes.poll(), value);
			else {
	/**
	 * In caso contrario lo inserisco nell'ultima posizione e incremento il numero di elementi
	 **/
				this.add(size, value); 
				++size;
			}
			return true;
		}
		
	/**
	 * Quando la capienza massima e' stata raggiunta creo un nuovo array di dimensione raddoppiata
	**/

		arr=Arrays.copyOf(arr, maxSize*2);
		return true;
	}
	
	

}
