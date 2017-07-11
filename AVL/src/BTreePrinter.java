import java.util.*;

public class BTreePrinter {
	public static void main(String[] args) {

		BTreePrinter bp = new BTreePrinter();

		final String alphabet = "ABCDEFGHIJKLMNOP";
		final int N = alphabet.length();

		Random r = new Random();

		/*
		for(int k = 0; k <= 20; k++) {
			System.out.println("------ TREE " + k + " ---------");
			bp.makeEmpty();
			for (int i = 0; i < 10; i++) {
				String ran = ((Character)alphabet.charAt(r.nextInt(N))).toString();
				String ran2 = ((Character)alphabet.charAt(r.nextInt(N))).toString();
				String ranF = ran + ran2;
				if(bp.find(ranF) == null) {
					bp.insert(ranF, ranF);
				}
			}

			System.out.println("BALANCE? : " + bp.isBalancedNaive(bp.root));
			bp.printAVLNode(bp.root);
		}
		*/
		
		bp.insert("a", "a");
		bp.insert("b", "b");
		bp.insert("c", "c");
		bp.insert("d", "d");
		bp.insert("e", "e");
		bp.insert("f", "g");
		bp.insert("g", "g");
		
		bp.BFS();
	}
	
	public void BFS() {
		class JavaQueue{
	    	Queue<AVLNode> queue;
	    	protected JavaQueue(){
	    		queue = new LinkedList<AVLNode>();
	    	}
	    	protected void enqueue(AVLNode a){
	    		queue.add(a);
	    	}
	    	protected AVLNode dequeue(){
	    		return queue.poll();
	    	}
	    	protected AVLNode front(){
	    		return queue.peek();
	    	}
	    }
		
		if(root == null) {
			System.out.println("Empy");
		}else {
			JavaQueue q = new JavaQueue();
			q.enqueue(root);
			
			while(q.front() != null) {
				AVLNode current = q.dequeue();
				if(current.left != null) {
					q.enqueue(current.left);
				}
				if(current.right != null) {
					q.enqueue(current.right);
				}
				
				System.out.println(current.value + " -> ");
			}
		}
	}

	//start test suite
	public boolean isBalancedNaive(AVLNode root){
		if(root==null)return true;
		int heightdifference = getHeight(root.left)-getHeight(root.right);
		if(Math.abs(heightdifference)>1){
			return false;
		}else{
			return isBalancedNaive(root.left) && isBalancedNaive(root.right);
		}
	}

	//end test suite


	public static void printAVLNode(AVLNode root) {
		int maxLevel = BTreePrinter.maxLevel(root);

		printAVLNodeInternal(Collections.singletonList(root), 1, maxLevel);
	}

	private static <T extends Comparable<?>> void printAVLNodeInternal(List<AVLNode> nodes, int level, int maxLevel) {
		if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
			return;

		int floor = maxLevel - level;
		int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
		int firstSpaces = (int) Math.pow(2, (floor)) - 1;
		int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

		BTreePrinter.printWhitespaces(firstSpaces);

		List<AVLNode> newAVLNodes = new ArrayList<AVLNode>();
		for (AVLNode node : nodes) {
			if (node != null) {
				System.out.print(node.value);
				newAVLNodes.add(node.left);
				newAVLNodes.add(node.right);
			} else {
				newAVLNodes.add(null);
				newAVLNodes.add(null);
				System.out.print(" ");
			}

			BTreePrinter.printWhitespaces(betweenSpaces);
		}
		System.out.println("");

		for (int i = 1; i <= endgeLines; i++) {
			for (int j = 0; j < nodes.size(); j++) {
				BTreePrinter.printWhitespaces(firstSpaces - i);
				if (nodes.get(j) == null) {
					BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
					continue;
				}

				if (nodes.get(j).left != null)
					System.out.print("/");
				else
					BTreePrinter.printWhitespaces(1);

				BTreePrinter.printWhitespaces(i + i - 1);

				if (nodes.get(j).right != null)
					System.out.print("\\");
				else
					BTreePrinter.printWhitespaces(1);

				BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
			}

			System.out.println("");
		}

		printAVLNodeInternal(newAVLNodes, level + 1, maxLevel);
	}

	private static void printWhitespaces(int count) {
		for (int i = 0; i < count; i++)
			System.out.print(" ");
	}

	private static <T extends Comparable<?>> int maxLevel(AVLNode node) {
		if (node == null)
			return 0;

		return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
	}

	private static <T> boolean isAllElementsNull(List<T> list) {
		for (Object object : list) {
			if (object != null)
				return false;
		}

		return true;
	}


	public class AVLNode{
		//Do not change these variable names
		String key;
		String value;
		AVLNode left;
		AVLNode right;


		//Place any additional fields you need here
		int height; 

		//TODO implement the node class here
		private AVLNode(String key, String value) {
			this.key = key;
			this.value = value;
			left = null;
			right = null;
			height = 0;
		}

	}

	//Use this variable as your root
	AVLNode root;
	int size;

	//You may use any additional fields here as you see fit

	public void makeEmpty() {
		// TODO Remove all elements from the AVL tree.
		root = null;
	}

	public int size() {
		// TODO Return the number of elements currently in the tree.
		return size;
	}

	public void insert(String key, String value) {
		// TODO Insert the <key,value> pair into the AVLTree
		// Throw an IllegalArgumentException if the client attempts to insert a duplicate key
		if(find(key) != null) {
			throw new IllegalArgumentException("Key already in tree!");

		}else{

			AVLNode insertion = new AVLNode(key, value);
			//System.out.println("root: " + root);			
			root = insertionHelper(insertion, root);
			size++;

		}
	}

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


		current.height = maxHeight(getHeight(current.left), getHeight(current.right)) + 1;
		//System.out.println("----");
		return current;
	}

	private AVLNode leftRotation(AVLNode rotator) {
		AVLNode rLeft = rotator.left;
		rotator.left = rLeft.right;
		rLeft.right = rotator;
		rotator.height = maxHeight( getHeight(rotator.left ), getHeight(rotator.right)) + 1;
		rLeft.height = maxHeight( getHeight(rLeft.left ), rotator.height ) + 1;
		return rLeft;

	}

	private AVLNode doubleLeftRotation(AVLNode rotator) {
		rotator.left = rightRotation(rotator.left );
		return leftRotation(rotator);
	}

	private AVLNode rightRotation(AVLNode rotator)
	{
		AVLNode rRight = rotator.right;
		rotator.right = rRight.left;
		rRight.left = rotator;
		rotator.height = maxHeight( getHeight(rotator.left), getHeight(rotator.right)) + 1;
		rRight.height = maxHeight( getHeight(rRight.right), rotator.height) + 1;
		return rRight;
	}

	private AVLNode doubleRightRotation(AVLNode rotator) {
		rotator.right = leftRotation(rotator.right);
		return rightRotation(rotator);
	}

	private int maxHeight(int height1, int height2) {
		if(height1 > height2) {
			return height1;
		}else {
			return height2;
		}
	}
	private int getHeight(AVLNode current) {
		if(current != null) {
			return current.height;
		}else {
			return -1;
		}
	}

	public String find(String key) {
		// TODO Return the value affiliated with the String key.
		// Throw an ObjectNotFoundException if the key is not in the AVLTree
		String searchResult = searchHelper(root, key);
		return searchResult;
	}

	private String searchHelper(AVLNode current, String toFind) {
		while(current != null) {
			String currentKey = current.key;

			if(currentKey.compareTo(toFind) > 0 ) {
				current = current.left;
			}else if(currentKey.compareTo(toFind) < 0) {
				current = current.right;
			}else {
				return current.value;
			}
		}
		return null;

	}
	public Iterator<String> getBFSIterator() {
		// TODO Only complete this section if you wish to attempt the 10 points EC
		// This function should return a BFSIterator: Starter code provided below
		return null;
	}

	/*	Define your private Iterator class below.

	private class BFSIterator implements Iterator<String>{

		public boolean hasNext() {
			// TODO Return true if the iterator has another value to return
			return false;
		}

		public String next() {
			// TODO Return the next value in a BFS traversal of the tree
			// It should start at the root and iterate through left children before right
			return null;
		}

	}
	 */


}


