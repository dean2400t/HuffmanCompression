package assign1;

/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */

/*
 * class CompressedFile keeps track of the current bit and byte
 * we are at and contains the future compressed file byte array
 */
public class CompressedFile {
	private int curByte;
	private int curBit;
	private byte[] fileByteArray;
	
	//constructor sets cur byte to 0, cur bit to 7 (the "left" one) and initialize the bytes to -128
	public CompressedFile(int sizeOfFile) {
		curByte=0;
		curBit=7;
		fileByteArray=new byte[sizeOfFile];
		for (int index=0; index<sizeOfFile; index++)
			fileByteArray[index]=-128;
	}
	
	//cycling bits and bytes
	public void nextBit()
	{
		curBit--;
		if (curBit<0)
		{
			curBit=7;
			curByte++;
		}
	}
	public void nextByte()
	{
		curByte++;
	}
	public int getBit(){return curBit;}
	public int getByte(){return curByte;}
	//adds value to current byte
	public void addToByte(int valueToAdd)
	{
		fileByteArray[curByte]+=valueToAdd;
	}
	public byte[] getCompressedFileByteArr(){return fileByteArray;}
}
