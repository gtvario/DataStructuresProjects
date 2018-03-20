package cs2321;

import java.util.Comparator;

import net.datastructures.*;
/**
 * A Adaptable PriorityQueue based on an heap. 
 * 
 * Course: CS2321 Section ALL
 * Assignment: #3
 * @author Giulio Vario
 */

public class HeapPQ<K,V> implements AdaptablePriorityQueue<K,V> {
	
	ArrayList<Entry<K,V>> heapPQ;	
	Comparator<K> comp;
	
	public HeapPQ() {
		heapPQ = new ArrayList<Entry<K,V>>();
		comp = new DefaultComparator<K>();
	}
	
	public HeapPQ(Comparator<K> c) {
		heapPQ = new ArrayList<Entry<K,V>>();
		comp = c;
	}
	
	/**
	 * The entry should be bubbled up to its appropriate position 
	 * @param int move the entry at index j higher if necessary, to restore the heap property
	 */
	public void upheap(int j){
		if(j == 0) { //base case, if we are at the root we are done
			return;
		}
		
		int p = parent(j);//grab the parent
		if(comp.compare(heapPQ.get(j).getKey(), heapPQ.get(p).getKey()) <= 0) { //if the child is less than the parent, swap
			swap(j,p);
		}
		 
		upheap(p);//go again with the parent
	}
	
	/**
	 * The entry should be bubbled down to its appropriate position 
	 * @param int move the entry at index j lower if necessary, to restore the heap property
	 */
	
	public void downheap(int j){
		if(!(hasLeft(j))) { //if the node does not have a left child we are done
			return;
		}
		
		int s = left(j);//grab the left child
		
		if(hasRight(j) && comp.compare(heapPQ.get(s).getKey(), heapPQ.get(right(j)).getKey()) >= 0) { //if the left child is greater than the right, the right is the new smallest
			s = right(j);
		}
		
		if(comp.compare(heapPQ.get(s).getKey(), heapPQ.get(j).getKey()) <= 0) { //if the smallest child is smaller than the parent, swap
			swap(s, j);
		}
		
		downheap(s); //downheap again with the new smallest
	}

	/**
	 * Returns the size of the PQ
	 * 
	 * @return size of PQ
	 */
	@TimeComplexity ("O(1)")	
	@Override
	public int size() {
		/*
		 * TCJ
		 * The method just returns an int variable, which is a primitive operation. This is constant time.
		 */
		return heapPQ.size(); //get the size of the heap
	}

	/**
	 * Checks if the PQ is empty
	 * 
	 * @return true if PQ is empty, otherwise false
	 */
	@TimeComplexity ("O(1)")	
	@Override
	public boolean isEmpty() {
		/*
		 * TCJ
		 * The method returns a comparison, which is a primitive operation. This is constant time
		 */
		return heapPQ.isEmpty(); //check if the heap is empty
	}
	
	/**
	 * Inserts a new entry in the heap and keeps heap order
	 * 
	 * @param K new key
	 * @param V new value
	 * @return entry inserted
	 */
	@TimeComplexity ("O(log n)")
	@Override
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		/*
		 * TCJ
		 * Moves log n steps down the tree before inserting and then performs a log n operation
		 */
		boolean checked = checkKey(key);
		
		PQEntry<K,V> insertedEntry = new PQEntry<K,V>(key,value, heapPQ.size()); //create a new entry 
		
		heapPQ.addLast(insertedEntry); //put entry at the end of the list
		
		upheap(heapPQ.size() - 1); //restore heap order
		
		return insertedEntry; //return the new entry
	}

	protected boolean checkKey(K key) throws IllegalArgumentException{
		try {
			return (comp.compare(key, key) == 0);
		} catch(ClassCastException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Finds the minimum key in the heap
	 * 
	 * @return minimum entry
	 */
	@TimeComplexity ("O(1)")
	@Override
	public Entry<K, V> min() {
		/*
		 * TCJ
		 * Accesses the array, which is a constant time operation
		 */
		if(heapPQ.isEmpty()) { //check if the heap is empty
			return null;
		}
		
		return heapPQ.get(0); //get the root node
	}

	/**
	 * Finds the minimum key and removes it
	 * 
	 * @return the removed key
	 */
	@TimeComplexity ("O(log n)")
	@Override
	public Entry<K, V> removeMin() {
		/*
		 * TCJ
		 * Removes entry by stepping down the tree logn steps and restores the heap
		 */
		if(heapPQ.isEmpty()) { //check if the heap is empty
			return null;
		}
		PQEntry<K,V> removedVal = (PQEntry<K, V>) heapPQ.get(0); //get the minimum value
		
		heapPQ.set(0, heapPQ.get(heapPQ.size() - 1)); //replace the minimum entry with the last entry
		
		heapPQ.removeLast(); //get rid of the last node
		
		downheap(0); //restore heap order
		
		return removedVal; //return the removed entry
	}

	/**
	 * Removes selected entry from the heap and restores heap order
	 * 
	 * @param entry to be removed
	 */
	@TimeComplexity ("O(log n)")
	@Override
	public void remove(Entry<K, V> entry) throws IllegalArgumentException {
		/*
		 * TCJ
		 * Removes entry by stepping down the tree logn steps and restores the heap
		 */
		PQEntry<K,V> removedEntry = (PQEntry<K,V>) validateEntry(entry); //validate the entry and set it to a new entry variable
		
		int i = removedEntry.getIndex();
		if(i == heapPQ.size() - 1) { //if the entry to remove is at the end of the list
			heapPQ.remove(heapPQ.size() - 1); //get rid of element at the end of the list
		}
		else {
			swap(i, heapPQ.size() - 1); //swap the entry with the last entry
			heapPQ.remove(heapPQ.size() - 1); //get rid of last entry
			downheap(i);
		}
		
	}

	/**
	 * Replaces key of the entry
	 * 
	 * @param entry to replace key of
	 * @param K new key
	 */
	@TimeComplexity ("O(log n)")
	@Override
	public void replaceKey(Entry<K, V> entry, K key) throws IllegalArgumentException {
		/*
		 * TCJ
		 * This changes a key in the entry, which is a constant time operation
		 */
		PQEntry<K,V> tempEntry = (PQEntry<K,V>) validateEntry(entry); //validate the entry
		
		if(!(comp.compare(key, key) == 0)) { //validate the key
			throw new IllegalArgumentException();
		}
		
		tempEntry.setKey(key); //set the new key
		
		reHeap(tempEntry.getIndex()); //restore heap order
	}

	/**
	 * Replaces the value of entry
	 * 
	 * @param value to replace in entry
	 * @param entry to replace value of 
	 */
	@TimeComplexity ("O(1)")
	@Override
	public void replaceValue(Entry<K, V> entry, V value) throws IllegalArgumentException {
		/*
		 * TCJ
		 * This changes a value in the entry, which is a constant time operation
		 */
		validateEntry(entry);
		PQEntry<K,V> tempEntry = (PQEntry<K,V>) (entry); //validate the entry
		
		tempEntry.setValue(value); //set the new value
	}
	

	public PQEntry<K,V> validateEntry(Entry<K,V> entry) {
		if(!(entry instanceof PQEntry)) { //check if entry is actually an entry
			throw new IllegalArgumentException();
		}
		PQEntry<K,V> tempEntry = (PQEntry<K,V>) (entry);
		
		if(tempEntry.getIndex() > heapPQ.size() -1) { //check if entry is in the list
			throw new IllegalArgumentException();
		}
		
		return tempEntry;
	}
	

	public int parent(int i) {
		return ((i-1)/2);
	}
	

	public int left(int i) {
		return (2*i + 1);
	}
	

	public int right(int i) {
		return(2*i + 2);
	}
	
	public boolean hasLeft(int i) {
		return left(i) < heapPQ.size();
	}
	
	
	public boolean hasRight(int i) {
		return right(i) < heapPQ.size();
	}
	
	
	public void swap(int i, int j) {
		Entry<K,V> tempEntry = heapPQ.get(i);
		
		//swap the index of each entry
		((PQEntry<K,V>) heapPQ.get(i)).setIndex(j);
		((PQEntry<K,V>) heapPQ.get(j)).setIndex(i);
		
		//swap each entry
		heapPQ.set(i, heapPQ.get(j));
		heapPQ.set(j, tempEntry);
	}
	
	public void reHeap(int i) {
		if(i >0 && comp.compare(heapPQ.get(i).getKey(), heapPQ.get(parent(i)).getKey()) < 0) { //if new key is smaller than its parent, upheap, else downheap
			upheap(i);
		}
		else {
			downheap(i);
		}
	}
	
	static class PQEntry<K,V> implements Entry<K,V>{
		private K k;
		private V v;
		private int i;
		
		public PQEntry(K key, V value, int index) {
			k = key;
			v = value;
			i = index;
		}
		
		@Override
		public K getKey() {
			return k;
		}

		@Override
		public V getValue() {
			return v;
		}
		
		public void setKey(K key) {
			k = key;
		}
		
		public void setValue(V value) {
			v = value;
		}
		
		public int getIndex() {
			return i;
		}
		
		public void setIndex(int i) {
			this.i = i;
		}
	}



}
