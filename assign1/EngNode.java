package assign1;
/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */

//irrelevant for question number 1
public class EngNode {
	//frequency of byte in file
		private int freq;
		//value of byte
		private int[] bytesVal;
		private int numOfBytes;
		//left and right subTrees to be used by nodes which do not represent a Byte
		private EngNode left;
		private EngNode right;
		
		//Path node for boolean array which represent bits order
		private Path bitsRepresentation;
		//number of bits in path array
		private int lengthOfRepresentation;
		
		//Node constructor
	    public EngNode(int freq,int[] bytesVal,EngNode left, EngNode right)
	    {
	    	this.freq=freq;
	    	this.bytesVal=bytesVal;
	    	this.numOfBytes=bytesVal.length;
	    	this.left=left;
	    	this.right=right;
	    }
	    
	    //GetSet functions
	    public int getFreq() {return freq;}
	    public void setFreq(int freq) {this.freq=freq;}

	    public int[] getBytesVal() {return bytesVal;}
	    public void setBytesVal(int[] bytesVal) {this.bytesVal=bytesVal;}

	    public int getLengthOfRepresentation() {return lengthOfRepresentation;}
	    public void setLengthOfRepresentation(int lengthOfRepresentation) {this.lengthOfRepresentation=lengthOfRepresentation;}
	    
	    public Path getBitsRepresentation() {return bitsRepresentation;}
	    public void setBitsRepresentation(Path bitsRepresentation) {this.bitsRepresentation=bitsRepresentation;}


	    public EngNode getLeft() {return left;}
	    public void setLeft(EngNode left) {this.left=left;}

	    public EngNode getRight() {return right;}
	    public void setRight(EngNode right) {this.right=right;}

		public int getNumOfBytes() {
			return numOfBytes;
		}

		public void setNumOfBytes(int numOfBytes) {
			this.numOfBytes = numOfBytes;
		}

}
