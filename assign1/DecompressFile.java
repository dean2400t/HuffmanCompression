package assign1;

/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */

/*
 * Class used to go through compressed file and extract bits
 */
public class DecompressFile {
	private int curByte;
	private int curBit;
	private byte[] fileByteArray;
	public DecompressFile(byte[] fileByteArray) {
		curByte=0;
		curBit=7;
		this.fileByteArray=fileByteArray;
	}
	
	public void nextBit()
	{
		curBit--;
		if (curBit<0)
		{
			curBit=7;
			curByte++;
		}
	}
	
	//removes the current bit value if 1 from the current byte and returns true if was 1 and false if 0
	public boolean getBitAndMove()
	{
		if (fileByteArray[curByte]-Math.pow(2, curBit)>=-128)
		{
			fileByteArray[curByte]-=Math.pow(2, curBit);
			nextBit();
			return true;
		}
		else
		{
			nextBit();
			return false;
		}
	}
	public byte getCurByte() {return fileByteArray[curByte];}
	public int getCurByteIndex(){return curByte;}
	public void nextByte() {curByte++;}
	public byte[] getFileByteArray(){return fileByteArray;}
	
}
