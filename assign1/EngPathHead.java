package assign1;

/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */

//irrelevant for question number 1
public class EngPathHead extends Path{
	private byte[] value;
	private int pathlength;
	private Path pathWalker;
	
	public EngPathHead()
	{
		
	}
	
	//function 'copyPath' returns a new path with the same values as this path
		public EngPathHead copyPath()
		{
			Path nextToCopy=getNextBit();
			EngPathHead newPathHead=new EngPathHead();
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

		public int getPathlength() {
			return pathlength;
		}

		public void setPathlength(int pathlength) {
			this.pathlength = pathlength;
		}

		public byte[] getValue() {
			return value;
		}

		public void setValue(byte[] value) {
			this.value = value;
		}

		public Path getPathWalker() {
			return pathWalker;
		}

		public void setPathWalker(Path pathWalker) {
			this.pathWalker = pathWalker;
		}
		
		public void resetPathWalker()
		{
			pathWalker=new Path();
			pathWalker.setNextBit(getNextPath());
			pathWalker.setIsBitOne(isBitOne());
		}
		public void walkerToNext()
		{
			pathWalker=pathWalker.getNextBit();
		}

}

