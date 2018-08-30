package assign1;
/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */


import java.util.Comparator;

//class needed by priorityQueue to define the heap  
public class priorityQueueComparator implements Comparator<Node>{


	public int compare(Node left, Node right) {
		return left.getFreq()-right.getFreq();
	}
	

}
