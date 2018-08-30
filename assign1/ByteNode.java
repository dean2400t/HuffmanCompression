package assign1;

/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */

//Class used because size of decompressed file is not known yet, it is linkedList of bytes
public class ByteNode {
	private ByteNode nextNode;
	private byte value;
	public ByteNode(byte value){
		this.value=value;
	}
	public void setNextByte(ByteNode next)
	{
		this.nextNode=next;
	}
	public ByteNode getNextByte(){return nextNode;}
	
	public byte getValue(){return value;}
}
