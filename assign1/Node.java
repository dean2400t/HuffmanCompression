package assign1;
/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */
//class node represent a huffman node
public class Node {
	//frequency of byte in file
	private int freq;
	//value of byte
	private int byteVal;
	//left and right subTrees to be used by nodes which do not represent a Byte
	private Node left;
	private Node right;
	
	//Path node for boolean array which represent bits order
	private Path bitsRepresentation;
	//number of bits in path array
	private int lengthOfRepresentation;
	
	//Node constructor
    public Node(int freq,int byteVal,Node left, Node right)
    {
    	this.freq=freq;
    	this.byteVal=byteVal;
    	this.left=left;
    	this.right=right;
    }
    
    //GetSet functions
    public int getFreq() {return freq;}
    public void setFreq(int freq) {this.freq=freq;}

    public int getByteVal() {return byteVal;}
    public void setByteVal(int byteVal) {this.byteVal=byteVal;}

    public int getLengthOfRepresentation() {return lengthOfRepresentation;}
    public void setLengthOfRepresentation(int lengthOfRepresentation) {this.lengthOfRepresentation=lengthOfRepresentation;}
    
    public Path getBitsRepresentation() {return bitsRepresentation;}
    public void setBitsRepresentation(Path bitsRepresentation) {this.bitsRepresentation=bitsRepresentation;}


    public Node getLeft() {return left;}
    public void setLeft(Node left) {this.left=left;}

    public Node getRight() {return right;}
    public void setRight(Node right) {this.right=right;}
}
