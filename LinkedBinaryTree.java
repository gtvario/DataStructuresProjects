package cs2321;
import java.util.Iterator;

import net.datastructures.*;
	

public class LinkedBinaryTree<E> implements BinaryTree<E>{

	protected Node<E> root;
	private int size;
	
	//general node creation function
	protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right){
		return new Node<E>(e, parent, left, right);
	}
	
	//checks if the node is valid
	protected Node<E> validate(Position<E> p) throws IllegalArgumentException{
		if(!(p instanceof Node)) {
			throw new IllegalArgumentException();
		}
		Node<E> node = (Node<E>) p;
		
		if(node.getParent() == node) {
			throw new IllegalArgumentException();
		}
		
		return node;
	}
	
	@Override
	public Position<E> root() {
		return root;
	}
	
	public LinkedBinaryTree( ) {
		root = null;
		size = 0;
	}
	
	@Override
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getParent();
	}

	@Override
	public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/* count only direct child of the node, not further descendant. */
	public int numChildren(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		int num = 0;
		
		if(node.getLeft() != null) { //check if node has left child
			num++;
		}
		if(node.getRight() != null) { //check if node has right child
			num++;
		}
		return num;
	}

	@Override
	public boolean isInternal(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return ((node.getLeft() != null) || (node.getRight() != null)); //if node has a child, it is internal
	}

	@Override
	public boolean isExternal(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);

		return ((node.getLeft() == null) && (node.getRight() == null)); //if node does not have a child, it is external
	}

	@Override
	public boolean isRoot(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getParent() == null; //only root has a null parent
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return root == null; //if there is not a root, there is not a tree
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new myIterator();
	}

	@Override
	public Iterable<Position<E>> positions() {
		return new PositionIterable();
	}

	@Override
	public Position<E> left(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getLeft();
	}

	@Override
	public Position<E> right(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getRight();
	}

	@Override
	public Position<E> sibling(Position<E> p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* creates a root for an empty tree, storing e as element, and returns the 
	 * position of that root. An error occurs if tree is not empty. 
	 */
	public Position<E> addRoot(E e) throws IllegalStateException {
		if(!isEmpty()) {	//if it already has a root, not a legal state
			throw new IllegalStateException();
		}
		
		root = createNode(e, null, null, null);	//make the new root
		size = 1;	//size is now 1
		return root;
	}
	
	/* creates a new left child of Position p storing element e, return the left child's position.
	 * If p has a left child already, throw exception IllegalArgumentExeption. 
	 */
	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p); //make sure node is a valid position
		
		if(parent.getLeft() != null) {	//if it already has a left node, can't place new node there
			throw new IllegalArgumentException();
		}
		
		Node<E> child = createNode(e, parent, null, null); //make new node
		parent.setLeft(child); //set node as left child of the parent
		size++;		//increase size of tree
		return child;
	}

	/* creates a new right child of Position p storing element e, return the right child's position.
	 * If p has a right child already, throw exception IllegalArgumentExeption. 
	 */
	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p); //make sure node is a valid position
		
		if(parent.getRight() != null) {  //if it already has a right node, can't place new node there
			throw new IllegalArgumentException();
		}
		
		Node<E> child = createNode(e, parent, null, null); //make new node
		parent.setRight(child); //set node as right child of the parent
		size++;  //increase size of the tree
		return child;
	}
	
	/* Attach trees t1 and t2 as left and right subtrees of external Position. 
	 * if p is not external, throw IllegalArgumentExeption.
	 */
	public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2)
			throws IllegalArgumentException {
		Node<E> node = validate(p);
		
		if(isInternal(p)) {		//node must be a leaf node
			throw new IllegalArgumentException();
		}
		
		size += t1.size() + t2.size();	//add on the size of both trees to the new tree
		
		if(!t1.isEmpty()) {			//attach t1 as the left subtree of the node
			t1.root.setParent(node);
			node.setLeft(t1.root);
			t1.root = null;
			t1.size = 0;
		}
		
		if(!t2.isEmpty()) {			//attach t2 as the right subtree of the node
			t2.root.setParent(node);
			node.setRight(t2.root);
			t2.root = null;
			t2.size = 0;
		}
	
	}
	
	protected static class Node<E> implements Position<E>{
		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;
		
		public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
			element = e;
			parent = above;
			left = leftChild;
			right = rightChild;
		}
		
		@Override
		public E getElement() throws IllegalStateException { return element; }
		
		public Node<E> getParent(){ return parent; }
		
		public Node<E> getLeft(){ return left; }
		
		public Node<E> getRight(){ return right; }
		
		public void setParent(Node<E> parentNode){ parent = parentNode;	}
		
		public void setLeft(Node<E> leftNode) { left = leftNode; }
		
		public void setRight(Node<E> rightNode) { right = rightNode; }
		
	}
	
	private class PositionIterable implements Iterable<Position<E>>{

		@Override
		public Iterator<Position<E>> iterator() {
			// TODO Auto-generated method stub
			return new PositionIterator();
		}		
	}
	
	private class PositionIterator implements Iterator<Position<E>>{

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Position<E> next() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private class myIterator implements Iterator<E>{

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
