/**
 * @author Giulio Vario
 * gvario@mtu.edu
 * CS2321
 */
package cs2321;

import java.util.Iterator;

import net.datastructures.List;

public class ArrayList<E> implements List<E> {

	private E[] data;
	private int size;
	private int capacity;

	public ArrayList() {
		capacity = 16;
		data = (E[]) new Object[capacity];
		size = 0;
	}

	/**
	 * Returns the number of elements in the list.
	 * 
	 * @return number of elements in the list
	 */
	@TimeComplexity("O(1)")
	@Override
	public int size() {
		/*
		 * TCJ
		 * The method just returns an int variable, which is a primitive operation. This is constant time.
		 */
		return size;
	}

	/**
	 * Tests whether the list is empty.
	 * 
	 * @return true if the list is empty, false otherwise
	 */
	@TimeComplexity("O(1)")
	@Override
	public boolean isEmpty() {
		/*
		 * TCJ
		 * The method returns a comparison, which is a primitive operation. This is constant time
		 */
		return size == 0;
	}

	/**
	 * Returns (but does not remove) the element at index i.
	 * 
	 * @param i
	 *            the index of the element to return
	 * @return the element at the specified index
	 * @throws IndexOutOfBoundsException
	 *             if the index is negative or greater than size()-1
	 */
	@TimeComplexity("O(1)")
	@Override
	public E get(int i) throws IndexOutOfBoundsException {
		/*
		 * TCJ
		 * Method accesses an array and returns the element in that spot. This is a primitive operation, therefore constant time
		 */
		if (i < 0 || i > size) {
			throw new IndexOutOfBoundsException();
		}
		return data[i];
	}

	/**
	 * Replaces the element at the specified index, and returns the element
	 * previously stored.
	 * 
	 * @param i
	 *            the index of the element to replace
	 * @param e
	 *            the new element to be stored
	 * @return the previously stored element
	 * @throws IndexOutOfBoundsException
	 *             if the index is negative or greater than size()-1
	 */
	@TimeComplexity("O(1)")
	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		/*
		 * TCJ
		 * The method accesses data in an array and puts data in, which are both primitive 
		 * operations. This means it is constant time and O(1)
		 */
		// checks if index inputed is a valid index
		if (i < 0 || i > size) {
			throw new IndexOutOfBoundsException();
		}
		
		E currElement = data[i]; //grab the current element
		data[i] = e; //replace the current element in index i with new element

		return currElement;
	}

	/**
	 * Inserts the given element at the specified index of the list, shifting all
	 * subsequent elements in the list one position further to make room.
	 * 
	 * @param i
	 *            the index at which the new element should be stored
	 * @param e
	 *            the new element to be stored
	 * @throws IndexOutOfBoundsException
	 *             if the index is negative or greater than size()
	 */
	@TimeComplexity("O(n)")
	@Override
	public void add(int i, E e) throws IndexOutOfBoundsException {
		/*
		 * TCJ
		 * The elements get shifted over when a new one is being added
		 * The worst case is that n elements are shifted over when i = 0
		 * This makes the worst case of adding O(n)
		 */

		// checks if index inputed is a valid index
		if (i < 0 || i > size) {
			throw new IndexOutOfBoundsException();
		}

		// doubles array if trying to add past capacity
		if (size == capacity) {
			int newCapacity = capacity * 2;
			E[] newArray = (E[]) new Object[newCapacity];

			for (int k = 0; k < data.length; k++) {
				newArray[k] = data[k];
			}

			data = newArray;
			capacity = newCapacity;
		}

		// shifts elements over from the index where you add a new element
		for (int j = size - 1; j >= i; j--) {
			data[j + 1] = data[j];
		}

		// add a new element
		data[i] = e;

		// increase the size of the list
		size++;

	}

	/**
	 * Removes and returns the element at the given index, shifting all subsequent
	 * elements in the list one position closer to the front.
	 * 
	 * @param i
	 *            the index of the element to be removed
	 * @return the element that had be stored at the given index
	 * @throws IndexOutOfBoundsException
	 *             if the index is negative or greater than size()
	 */
	@TimeComplexity("O(n)")
	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		/*
		 * TCJ
		 * all of the elements from i + 1 to the last one shift to the left
		 * When i = 0, the worst case, there are n shifts so the worst case is
		 * O(n).
		 */
		// checks if index inputed is a valid index
		if (i < 0 || i >= size) {
			throw new IndexOutOfBoundsException();
		}

		E objectRemoved = data[i]; //grabs data that is being removed

		//overwrite the data and shrink the list
		for (int j = i; j < size - 1; j++) {
			data[i] = data[i + 1];
		}

		//reflect the new size by decreasing size
		size--; 

		return objectRemoved;
	}

	/**
	 * Returns an iterator of the elements stored in the list.
	 * 
	 * @return iterator of the list's elements
	 */
	@TimeComplexity("O(1)")
	@Override
	public Iterator<E> iterator() {
		/*
		 * TCJ
		 * This method returns a new iterator. This is a primitive operation that 
		 * makes the best and worst case O(1).
		 */
		return new myIterator();
	}

	/**
	 * Class that makes an iterator of the elements stored in the list	 *
	 */
	private class myIterator implements Iterator<E> {

		int cursor = 0;

		/**
		 * Checks if there is a next element
		 * 
		 * @return true if there is another element, otherwise false
		 */
		@Override
		public boolean hasNext() {
			return cursor < size;
		}

		/**
		 * Grabs what the next element in the list is
		 * 
		 * @return next element in the list
		 */
		@Override
		public E next() {
			E e = (E) data[cursor];
			cursor++; //move cursor to the next spot
			return e;
		}

	}

	/**
	 * Adds an element to the start of the list
	 * 
	 * @param e element to be added
	 * @throws IndexOutOfBoundsException
	 */
	@TimeComplexity("O(n)")
	public void addFirst(E e) throws IndexOutOfBoundsException {
		/*
		 * TCJ
		 * This is the worst case for the add method causing 
		 * n elements to be shifted over making it O(n)
		 */
		this.add(0, e);
	}

	/**
	 * Adds an element to the end of the list
	 * 
	 * @param e element to be added
	 * @throws IndexOutOfBoundsException
	 */
	@TimeComplexity("O(1)")
	public void addLast(E e) throws IndexOutOfBoundsException {
		/*
		 * TCJ
		 * Adds an element to a free spot in the array, which is a primitive operation
		 * making this constant time. 
		 */
		this.add(size, e);
	}

	/**
	 * Removes the element in the first spot of the list
	 * 
	 * @return element removed
	 * @throws IndexOutOfBoundsException
	 */
	@TimeComplexity("O(n)")
	public E removeFirst() throws IndexOutOfBoundsException {
		/*
		 * TCJ
		 * This causes n elements to be shifted to the left making it the worst case
		 * of O(n)
		 */
		return this.remove(0);
	}

	/**
	 * Removes an element from the end of the list
	 * 
	 * @return element removed
	 * @throws IndexOutOfBoundsException
	 */
	@TimeComplexity("O(1)")
	public E removeLast() throws IndexOutOfBoundsException {
		/*
		 * TCJ
		 * This is the best case for the remove. Getting rid of an element
		 * at the end causes no elements to be shifted making this constant time
		 * and an O(1)
		 */
		return this.remove(size - 1);
	}

}
