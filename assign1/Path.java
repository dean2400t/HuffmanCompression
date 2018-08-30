package assign1;
/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */
//Path class is used to save a Byte representation
public class Path {
	//next path node
	private Path nextBit;
	//value of 1 or 0
	boolean isBitOne;
	public Path() {
		
	}
	
	//setting next node
	public void setNextPath(Path nextPathNode) {
		setNextBit(nextPathNode);
	}
	
	public Path copyPath()
	{
		Path nextToCopy=getNextBit();
		Path newPathHead=new Path();
		//creating first node of new path and setting it's value as head bit of this path
		newPathHead.setIsBitOne(isBitOne);
		
		//curPathNode is used for walking along the path
		Path curPathNode=newPathHead;
		
		//newPathNode is used to create a new path
		Path newPathNode;
		
		//loop to copy the path
		while (nextToCopy!=null)
		{
			newPathNode=new Path();
			newPathNode.setIsBitOne(nextToCopy.isBitOne);
			curPathNode.setNextPath(newPathNode);
			curPathNode=newPathNode;
			nextToCopy=nextToCopy.getNextPath();
		}
		return newPathHead;
	}
	
	//function 'getLastBitPathInPath' go down the path and return the last node
	public Path getLastBitPathInPath()
	{
		Path tempPath=this;
		while (tempPath.getNextPath()!=null)
			tempPath=tempPath.getNextPath();
		return tempPath;
	}
	
	//GetsSets functions
	public void setIsBitOne(boolean isOne) {isBitOne=isOne;}
	public boolean isBitOne() {return isBitOne;}
	public Path getNextPath() {return getNextBit();}

	public Path getNextBit() {
		return nextBit;
	}

	public void setNextBit(Path nextBit) {
		this.nextBit = nextBit;
	}
}
