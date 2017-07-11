/* Tejveer Rai
 * June 2nd 2017
 * CSE 373 
 * Extra Credit Assignment 
 * 
 * AVLTree.java is an implementation of a self-balancing BST Tree that 
 * maintains the AVL property during insertion.
 * 
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AVLTree implements StringTree{

	//AVL Node class defines the nodes that will be holding the data
	//for our AVL tree
	public class AVLNode{
		//Do not change these variable names
		String key; 
		String value;
		AVLNode left; //left child
		AVLNode right; //right child


		//Place any additional fields you need here
		int height; //height of subtree

		//TODO implement the node class here
		public AVLNode(String key, String value) {
			this.key = key;
			this.value = value;
			left = null;
			right = null;
			height = 0;
		}

	}

	//Use this variable as your root
	AVLNode root; 
	int size; //current size of AVL tree

	//You may use any additional fields here as you see fit

	// This method completely empties our AVL tree
	public void makeEmpty() { 
		// TODO Remove all elements from the AVL tree.
		root = null;
		size = 0;
	}

	// This method returns the current size of our AVL tree
	public int size() {
		// TODO Return the number of elements currently in the tree.
		return size;
	}

	// This method is in charge of inserting a key and the associated value
	// into the AVL tree
	public void insert(String key, String value) {
		// TODO Insert the <key,value> pair into the AVLTree
		// Throw an IllegalArgumentException if the client attempts to insert a duplicate key
		try {
			find(key);
			throw new IllegalArgumentException("Key already in tree!");
		}catch(NoSuchElementException e) {
			AVLNode insertion = new AVLNode(key, value);		
			root = insertionHelper(insertion, root);
			size++;
		}	

	}

	// This is the helper method for inserting that appropriately calls 
	// any required rotations for inserting
	public AVLNode insertionHelper(AVLNode newNode, AVLNode current) {

		if(current == null) {
			current = newNode;
		}else if(newNode.key.compareTo(current.key) < 0) {

			current.left = insertionHelper(newNode, current.left);

			if(getHeight(current.left) - getHeight(current.right) == 2) {

				if(newNode.key.compareTo(current.left.key) < 0) {
					current = leftRotation(current);

				}else {
					current = doubleLeftRotation(current);
				}
			}

		}else if(newNode.key.compareTo(current.key) > 0) {
			current.right = insertionHelper(newNode, current.right);

			if(getHeight(current.right) - getHeight(current.left) == 2) {
				if(newNode.key.compareTo(current.right.key) > 0) {
					current = rightRotation(current);
				}else {
					current = doubleRightRotation(current);
				}
			}

		}
		current.height = maxHeight(getHeight(current.right), getHeight(current.left)) + 1;
		return current;
	}

	// This method is for conducting a SINGLE LEFT rotation 
	private AVLNode leftRotation(AVLNode rotator) {
		AVLNode rLeft = rotator.left;
		rotator.left = rLeft.right;
		rLeft.right = rotator;
		rotator.height = maxHeight(getHeight(rotator.right), getHeight(rotator.left )) + 1;
		rLeft.height = maxHeight(rotator.height, getHeight(rLeft.left )) + 1;
		return rLeft;

	}

	// This method is for a DOUBLE LEFT rotation 
	private AVLNode doubleLeftRotation(AVLNode rotator) {
		rotator.left = rightRotation(rotator.left );
		return leftRotation(rotator);
	}

	// This method is for a SINGLE RIGHT rotation
	private AVLNode rightRotation(AVLNode rotator)
	{
		AVLNode rRight = rotator.right;
		rotator.right = rRight.left;
		rRight.left = rotator;
		rotator.height = maxHeight(getHeight(rotator.right), getHeight(rotator.left)) + 1;
		rRight.height = maxHeight(rotator.height, getHeight(rRight.right)) + 1;
		return rRight;
	}

	// This method is for a DOUBLE RIGHT rotation
	private AVLNode doubleRightRotation(AVLNode rotator) {
		rotator.right = leftRotation(rotator.right);
		return leftRotation(rotator);
	}

	// This method returns the maximum height when comparing two subtrees
	private int maxHeight(int height1, int height2) {
		if(height1 > height2) {
			return height1;
		}else {
			return height2;
		}
	}
	
	// This method returns the height for a passed node 
	private int getHeight(AVLNode current) {
		if(current != null) {
			return current.height;
		}else {
			return -1;
		}
	}

	// This method is used to search for keys in our AVL tree
	public String find(String key) {
		// TODO Return the value affiliated with the String key.
		// Throw an ObjectNotFoundException if the key is not in the AVLTree
		String searchResult = searchHelper(root, key);
		return searchResult;
	}

	// This method assists searching and iterates through our tree to find the key
	private String searchHelper(AVLNode current, String toFind) {
		boolean found = false;
		while(current != null) {
			String currentKey = current.key;

			if(currentKey.compareTo(toFind) > 0 ) {
				current = current.left;
			}else if(currentKey.compareTo(toFind) < 0) {
				current = current.right;
			}else {
				found = true;
				return current.value;
			}
		}

		if(!found) {
			throw new NoSuchElementException();
		}

		return null;

	}
	
	// This returns a breadth first search iterator for our AVL tree
	public Iterator<String> getBFSIterator() {
		// TODO Only complete this section if you wish to attempt the 10 points EC
		// This function should return a BFSIterator: Starter code provided below
		return new BFSIterator();
	}

	//Define your private Iterator class below.

	// This class defines a BFS Iterator for AVL Trees
	private class BFSIterator implements Iterator<String>{

		// This class defines the queue that will be used in our BFS Iterator
		public class ListQueue {
			private class Node{
				//TODO Implement Linked List Node

				private AVLNode data; //data contained within the Node
				private Node pointer; //the next node the current node points to

				//constructor method for the node that creates a node
				private Node(AVLNode constructorData, Node constructorPointer){
					data = constructorData;
					pointer = constructorPointer;
				}

				//getter method for retrieving data from the note
				private AVLNode getData() {
					return data;
				}

				//getter method for retrieving the node being pointed to
				private Node getPointer() {
					return pointer;
				}

				//setter method for setting or updating the node being pointed to
				private void setPointer(Node newNode) {
					pointer = newNode;
				}

			}

			//Class variables here, if necessary
			private Node front; //the first node in our queue 
			private Node back; //the node in the back of our queue 

			//Constructor for the ListQueue 
			public ListQueue(){
				//TODO Implement constructor
				front = null; //initiate the queue as empty
				back = null; 
			}

			/*This method allows for adding to our queue. The input string is placed at the back of the 
			 * list, following the first in, first out rule of queues. 
			 */
			public void enqueue(AVLNode toInput) {
				// TODO Implement enqueue

				if(front == null) {
					front = new Node(toInput, null); //set the front to the added element if queue is empty
					back = front;
				}else {
					Node newPointer = new Node(toInput, null);
					back.setPointer(newPointer); //otherwise just set it to the back element of the queue
					back = newPointer;
				}


			}

			/*This method lets the user remove from the queue. The element that is removed is returned as
			 * it is taken out of the front of the queue.
			 */
			public AVLNode dequeue(){
				// TODO Implement dequeue

				if(front == null) { //test to see if queue is empty
					return null;
				}

				AVLNode data = front.getData();
				front = front.getPointer();
				return data;
			}

			/* This method simply returns the current front element of our queue to the user.
			 */
			public AVLNode front(){ 
				// TODO Implement front
				try {
					return front.getData(); //It's possible the front has no data, so handle it accordingly
				}catch(NullPointerException e) { 
					return null; //return null if getting the data gives an exception 
				}
			}

		}

		//creating our output queue
		ListQueue output = new ListQueue();

		// constructor for the BFS Iterator, properely conducts level order traversal 
		public BFSIterator() {

			ListQueue q = new ListQueue();
			q.enqueue(root);

			while(q.front() != null) {
				AVLNode current = q.dequeue();
				if(current.left != null) {
					q.enqueue(current.left);
				}
				if(current.right != null) {
					q.enqueue(current.right);
				}

				output.enqueue(current);
			}

		}


		// This method is to check if there is an element left in the iterator
		public boolean hasNext() {
			// TODO Return true if the iterator has another value to return
			return output.front != null;
		}

		// This method returns the next element available in the iterator
		public String next() {
			// TODO Return the next value in a BFS traversal of the tree
			// It should start at the root and iterate through left children before right

			AVLNode dq = output.dequeue();

			if(dq == null) {
				return null;
			}else {
				return dq.value;
			}
		}
	}

}
