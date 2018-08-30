package assign1;
/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */

//irrelevant for question number 1
import java.util.Comparator;

public class PrioritiyEngQueueComperator implements Comparator<EngNode>{


	public int compare(EngNode left, EngNode right) {
		return left.getFreq()-right.getFreq();
	}

}
