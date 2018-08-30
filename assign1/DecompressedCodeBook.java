package assign1;

/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */

//Class used to reBuilt the codeBook
public class DecompressedCodeBook {
	//using paths to save the 1 and 0 representation
	
	//path is to be used as the current location
	private Path[] paths;
	//startOfPaths to be used as head
	private Path[] startOfPaths;
	private byte[] byteVal;
	public DecompressedCodeBook(int numOfLetters)
	{
		startOfPaths=new Path[numOfLetters];
		paths=new Path[numOfLetters];
		for (int index=0; index<numOfLetters; index++)
		{
			startOfPaths[index]=new Path();
			paths[index]=startOfPaths[index];
		}
		byteVal=new byte[numOfLetters];
	}
	public void setCurRepBit(int index, boolean isBitOne)
	{
		paths[index].setIsBitOne(isBitOne);
	}
	public void setNewPathNode(int index, boolean isBitOne)
	{
		Path newPath=new Path();
		newPath.setIsBitOne(isBitOne);
		paths[index].setNextPath(newPath);
		paths[index]=newPath;
	}
	public void setCurPathBit(int index, boolean isBitOne)
	{
		paths[index].setIsBitOne(isBitOne);
	}
	public Path getPath(int index) {return startOfPaths[index];}
	public byte getbyteVal(int index) {return byteVal[index];}
	public void setbyteVal(int index, byte val) {byteVal[index]=val;}
}
