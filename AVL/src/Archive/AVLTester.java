/* Tejveer Rai
 * June 2nd 2017
 * CSE 373 
 * Extra Credit Assignment 
 * 
 * AVLTester.java is meant to test input trees to check if they are 
 * appropriate AVL trees.
 * 
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AVLTester {

	// This method is will return true if the passed tree is a correct implementation
	// of an AVL tree.
	public static boolean verifyAVL(StringTree toTest){
		// TODO Return true if toTest is an AVL implementation of a String tree and false otherwise. 
		// All StringTree interface methods must behave correctly
		// You may assume that size() and isEmpty() return the correct values
		// Other than this, do not assume anything about the tree toTest, including its start size
		
		return insertAndFindTest(toTest) && BFSIteratorTest(toTest) && AVLStructureTest(toTest);
	}

	// You may use as many static helper functions as you think are necessary
	
	// Insertion and Find test that tests insert and find functions of the 
	// input tree, as well as the properties of a dictionary (finding a value from a key)
	public static boolean insertAndFindTest(StringTree toTest) {
		boolean[] results = new boolean[6];


		//small insertion test (100)
		toTest.makeEmpty(); //empty our tree first
		results[0] = isEmptyTest(toTest); //test if the empty function passed
		String[] smallInsert = generateInsertionArray(100); //generate our input array
		boolean smallInsertResult = true; 
		for(int i = 0; i< smallInsert.length; i++) {
			toTest.insert(smallInsert[i], smallInsert[i]);
		}

		for(int i = 0; i< smallInsert.length; i++) {
			try {
				toTest.find(smallInsert[i]);
			}catch(NoSuchElementException e) {
				smallInsertResult = false; //test failed
			}
		}
		results[1] = smallInsertResult; //store test result

		//medium insertion test (1000)
		toTest.makeEmpty();
		results[2] = isEmptyTest(toTest);
		String[] medInsert = generateInsertionArray(1000);
		boolean medInsertResult = true;
		for(int i = 0; i< medInsert.length; i++) {
			toTest.insert(medInsert[i], medInsert[i]);
		}

		for(int i = 0; i< medInsert.length; i++) {
			try {
				toTest.find(medInsert[i]);
			}catch(NoSuchElementException e) {
				medInsertResult = false;
			}
		}
		results[3] = medInsertResult;

		//large insertion test (10000)
		toTest.makeEmpty();
		results[4] = isEmptyTest(toTest);
		String[] largeInsert = generateInsertionArray(10000);
		boolean largeInsertResult = true;
		for(int i = 0; i< largeInsert.length; i++) {
			toTest.insert(largeInsert[i], largeInsert[i]);
		}

		for(int i = 0; i< largeInsert.length; i++) {
			try {
				toTest.find(largeInsert[i]);
			}catch(NoSuchElementException e) {
				largeInsertResult = false;
			}
		}
		results[5] = largeInsertResult;

		//check if any of our tests failed
		for(int i = 0; i<results.length; i++) { 
			if(results[i] == false) {
				return false;
			}
		}
		return true;
	}


	// Helper test method written to check emptiness of a tree
	public static boolean isEmptyTest(StringTree toTest) {
		return toTest.size() == 0;
	}

	// This method checks the functionality and correctness of a BFS Iterator
	// for an AVL Tree. 
	public static boolean BFSIteratorTest(StringTree toTest) {
		AVLTree expected = new AVLTree(); //expected output 
		toTest.makeEmpty(); //empty 
		
		String[] largeInsert = generateInsertionArray(10000); //insert large number 
		for(int i = 0; i< largeInsert.length; i++) {
			toTest.insert(largeInsert[i], largeInsert[i]);
			expected.insert(largeInsert[i], largeInsert[i]);
		}
		
		//retrieve iterators
		Iterator<String> it = toTest.getBFSIterator(); 
		Iterator<String> expIt = expected.getBFSIterator();
		
		//compare iterator results
		while(it.hasNext()) {
			if(!it.next().equals(expIt.next())) {
				return false;
			}
		}
		
		return true;
	}
	

	// This method verifies the AVL property of a input tree by 
	// inserting a custom set and comparing the expected level order output
	public static boolean AVLStructureTest(StringTree toTest) {
		String expectedBFS = "phtdlrxbfjnqsvyacegikmouwz"; //the expected output 
		toTest.makeEmpty(); 
		
		for(int i = 0;i<expectedBFS.length();i++) { //add expected characters
			String add = ((Character)expectedBFS.charAt(i)).toString();
			toTest.insert(add, add);
		}
		
		//retrieve iterator
		Iterator<String> it = toTest.getBFSIterator();
		
		//loop through level order and verify correctness
		int currentInd = 0;
		while(it.hasNext()) {
			String compare = ((Character)expectedBFS.charAt(currentInd)).toString();
			if(!(it.next()).equals(compare)) {
				return false;
			}
			currentInd++;
		}
		return true;
	}

	// This is a helper method to create a unique string array of the input size given.
	public static String[] generateInsertionArray(int size) {
		String[] strings = new String[size];
		String alpha = "_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int stringLength = 1;
		int alphaPosition = 1;

		for(int i = 0; i<size; i++) {
			if(alphaPosition%27 == 0) {
				alphaPosition = 1;
				stringLength++;
				i = i-1;
			}else {
				String addition = ((Character)alpha.charAt(alphaPosition)).toString();
				for(int j = 1; j < stringLength; j++) {
					addition += ((Character)alpha.charAt(alphaPosition)).toString();
				}
				strings[i] = addition;
				alphaPosition++;
			}

		}
		return strings;
	}
}

