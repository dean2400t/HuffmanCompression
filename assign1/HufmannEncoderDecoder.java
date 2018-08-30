

package assign1;
/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.PriorityQueue;

import base.compressor;

public class HufmannEncoderDecoder implements compressor
{
	/*
	 * Part A: Functions for compressing file
	 * 		A1: creating a huffman tree
	 * 		A2: creating bits representation of each byte with LinkedList (Paths)
	 * 		A3: Calculating size of compressed file
	 * 		A4: Creating compressed file byte array
	 * 
	 * Part B:Functions for decompression
	 *		B1: reading bits and recreating the codeBook
	 *		B2: rebulding huffman tree
	 *		B3: using huffman code to recreate the file
	 *
	 * Part C: Main Functions
	 */

	public HufmannEncoderDecoder()
	{
		
	}
	
	//Part A: Functions for compressing file 
	
	//A1: creating a huffman tree
	//function 'getHuffmanTree' receive an original file byte array, and returns a huffman tree according to it's bytes frequencies, and number of nodes/bytes present
		public ContainerClass getHuffmanTree(byte[] fileByteArray)
		{
			ContainerClass containerClass=new ContainerClass();
			int arrLength=fileByteArray.length;
			
			//using counting sort to find bytes frequencies
			int countShows[]=new int[256];
			for (int index=0;index<arrLength;index++)
				countShows[128+fileByteArray[index]]++;
			
			//using priorityQueue to create a heap
			PriorityQueue<Node> queue=new PriorityQueue<Node>(256,new priorityQueueComparator());
			
			int NumBytesPresent=256;
			//adding nodes to heap
			for (int index=0;index<256;index++)
			{
				//Inserting all none 0 frequency bytes into heap
				if (countShows[index]>0)
				{
					Node node=new Node(countShows[index],index,null,null);
					queue.add(node);
				}
				else
					NumBytesPresent--;
			}
			
			Node root=null;
			
			
			//Preparing 'NumBytesPresent' to return
			containerClass.setVal1(NumBytesPresent);
			
			/*
			 * By pulling the two bytes with the lowest frequencies, merging them under a new subTree which
			 * has the combined value of their frequencies, and returning it to the heap we will eventually
			 * merge all the Nodes into one tree with the bytesValues as the leafs of the tree
			 */
			if (NumBytesPresent>0)
			{
				while (queue.size()>1)
				{
					Node left=queue.peek();
					queue.poll();
					Node right=queue.peek();
					queue.poll();
					Node node=new Node(left.getFreq()+right.getFreq(),-1,left,right);
					root=node;
					queue.add(node);
				}
				
				//returning tree root and number of bytesPresent
				containerClass.setNode1(root);
				return containerClass;
			}
			return null;
		}
		
		
	//A2: creating bits representation of each byte with LinkedList (Paths)
		
		/*
		 * Function 'setHuffmanCodeToPathsArray' receive 'root' for huffman tree,
		 * initial level of 0 to calculate tree max level, 'pathsHeadArr' to return
		 * path for each byte, 'curPathStepToByte' a path of the current location,
		 * 'pathHead' the current path start node
		 * 
		 * it returns a complete path and it's length for each byte using array of paths, 
		 * and the max level as returned value
		 *  
		 */
		protected int setHuffmanCodeToPathsArray(Node root, int level, PathHead[] pathsArr, Path curPathStepToByte, Path pathHead)
	    {
			//stop condition for when we found a leaf
	        if (root.getLeft() == null && root.getRight() == null && root.getByteVal()>-1) 
	        {
	        	//moving data from huffman tree to paths array
	        	pathsArr[root.getByteVal()]=new PathHead();
	            pathsArr[root.getByteVal()].setIsBitOne(pathHead.getNextPath().isBitOne());
	            pathsArr[root.getByteVal()].setPathlength(level);
	            Path nextPath=pathHead.getNextPath().getNextPath();
	            pathsArr[root.getByteVal()].setNextBit(nextPath);
	            return level;
	        }
	        
	        /*
	         * whenever we go left a new path is copied so we will have
	         * a new linked path list for each byte
	         * 
	         * when we go right we don't need to create a new path,
	         * we continue on the current one
	         */
	        
	        //going left
	        Path CopiedPath = pathHead.copyPath();
	        Path newStepLeft=new Path();
	        //value '0'
	        newStepLeft.setIsBitOne(false);
	        CopiedPath.getLastBitPathInPath().setNextPath(newStepLeft);
	        int levelLeft=setHuffmanCodeToPathsArray(root.getLeft(), level+1, pathsArr, newStepLeft, CopiedPath);
	        
	        //going right
	        Path newStepRight=new Path();
	        //value '1'
	        newStepRight.setIsBitOne(true);
	        curPathStepToByte.setNextPath(newStepRight);
	        int levelRight=setHuffmanCodeToPathsArray(root.getRight(), level+1, pathsArr, newStepRight, pathHead);
	        
	        //returning the lower level
	        if (levelLeft>levelRight)
	        	return levelLeft;
	        else
	        	return levelRight;
	        
	    }
	//A3: Calculating size of compressed file
		
		/*
		 * function receives the huffman tree, and pathsArray for length of each Byte 
		 * and using the frequencies, calculate the bytes needed for the content and codeBook
		 * byte representation
		 */
		private long calcBitsForAllBytes(Node node,PathHead[] pathsArray)
		{
			//'num' for number of bytes to return
			long num=0;
			
			//moving down the tree until we reach the byte on the tree
			if (node.getLeft()
	                == null
	            && node.getRight()
	                   == null
	            && node.getByteVal()>-1) {
				int length=pathsArray[node.getByteVal()].getPathlength();
				//returning the (frequency+1(for CodeBook))* length of Byte representation
				return (node.getFreq()+1)*length;
	        }
			num+= calcBitsForAllBytes(node.getLeft(),pathsArray);
			num+= calcBitsForAllBytes(node.getRight(),pathsArray);
			return num;
		}
		
		/*
		 * function 'calcNumOfBitsForFile' receive huffman tree, number of bytes, representation array
		 * and returns number of bits needed to be used to compress the file
		 */
		private long calcNumOfBitsForFile(Node root,int numOfBytes, int numOfBitsForRep, PathHead[] pathsArray)
		{
			long num=0;
			//numOfBytes
			num+=8;
			//value of each byte
			num+=8*numOfBytes;
			//number of bits to come before the length of each byte representation
			num+=8;
			//bits for length before each byte
			num+=numOfBitsForRep*numOfBytes;
			//redundant bits at the end
			num+=3;
			//calculating size of file
			long temp=calcBitsForAllBytes(root, pathsArray);
			num+=temp;
			return num;
		}

	
	

	//A4: Creating compressed file byte array
		
		private int getValueOfBit(int bit)
		{
			return (int) Math.pow(2, bit);
		}
		
		/*
		 * function 'insertBitsIntoCompressedFile' receives a compressed file Class object,
		 * number value to insert, and number of bits representing that value.
		 * the function then insert the correct bits into the byte array in the 
		 * compressed file class
		 */
		protected void insertBitsIntoCompressedFile(CompressedFile compressedFile, int bitsAsByteToInsert, int numOfBits)
		{
			//loop for going through number of bits 
			for (int bitNumber=numOfBits-1;bitNumber>=0;bitNumber--)
			{
				//if the value of the current bit is in bound of byte than it means his value is one
				if (bitsAsByteToInsert -((int) Math.pow(2, bitNumber))>=0)
				{
					bitsAsByteToInsert-=(int) Math.pow(2, bitNumber);
					//adding '1' to compressedFile current bit in current byte
					compressedFile.addToByte(getValueOfBit(compressedFile.getBit()));
				}
				//"adding" '0' to current bit in current byte 
				compressedFile.nextBit();
			}
		}
		
		/*
		 * function 'insertCodeBook' receive CompressedFile Class, byte representation array and number of bits
		 * needed to calculate later to know the length of each byte
		 */
		private void insertCodeBook(CompressedFile compressedFile, PathHead[] pathsArray, int numOfBitsForRep)
		{
			int numOfBitsForThisByte;
			for (int curByte=0;curByte<256;curByte++)
				if (pathsArray[curByte]!=null)
				{
					numOfBitsForThisByte=pathsArray[curByte].getPathlength();
					
					//inserting Byte value
					insertBitsIntoCompressedFile(compressedFile, curByte, 8);
					
					//inserting length of representation
					insertBitsIntoCompressedFile(compressedFile, numOfBitsForThisByte, numOfBitsForRep);
					
					//inserting all but the last two elements needed to represent the byte
					pathsArray[curByte].resetPathWalker();
					for (int curPath=0; curPath<pathsArray[curByte].getPathlength();curPath++)
					{
						if (pathsArray[curByte].getPathWalker().isBitOne())
							insertBitsIntoCompressedFile(compressedFile, 1, 1);
						else
							insertBitsIntoCompressedFile(compressedFile, 0, 1);
						pathsArray[curByte].walkerToNext();
					}
					//last element is representation length
				}
		}
		
		/*
		 * function 'generateCompressedFile' receive byte representation array,
		 * file byte array, number of bytes present in the file, number of bits
		 * to represent the length needed for each Byte representation, future
		 * size of compressed file, number of redundant bits.
		 * 
		 * function returns byte array containing the compressed file
		 */
		private byte[] generateCompressedFile(PathHead[] pathsArray ,byte[] fileByteArray, int numOfByteToRepresent, int numOfBitsForRep, int sizeOfCompressedByteArr, int redundentBits)
		{
			CompressedFile compressedFile=new CompressedFile(sizeOfCompressedByteArr);
			
			//codeBook
			//inserting number of bytes in codeBook
			compressedFile.addToByte((byte) numOfByteToRepresent);
			compressedFile.nextByte();
			
			//inserting number of bits neede to know the length of each byte
			compressedFile.addToByte((byte) numOfBitsForRep);
			compressedFile.nextByte();
			
			//inserting number of redundant bits
			insertBitsIntoCompressedFile(compressedFile, redundentBits, 3);
			
			//inserting remaining codeBook
			insertCodeBook(compressedFile, pathsArray, numOfBitsForRep);
			
			
			//Insert content
			int byteVal;
			
			//loop until end of original file byte array
			for (int curByte=0; curByte<fileByteArray.length; curByte++)
			{
				//getting correct index of byte from -128 to 127, 0-255
				byteVal=fileByteArray[curByte]+128;
				
				//adding the current byte representation to file
				pathsArray[byteVal].resetPathWalker();
				for (int curBit=0; curBit<pathsArray[byteVal].getPathlength(); curBit++)
				{
					{
						if (pathsArray[byteVal].getPathWalker().isBitOne())
							insertBitsIntoCompressedFile(compressedFile, 1, 1);
						else
							insertBitsIntoCompressedFile(compressedFile, 0, 1);
						pathsArray[byteVal].walkerToNext();
					}
				}
			}
			return compressedFile.getCompressedFileByteArr();
		}
	
	
	
		//function 'compressFile' receive byte array of the original file and returns it's compressed byte array
		protected byte[] compressFile(byte[] fileByteArray)
	{
			//A1
			//using container class in order to receive huffman tree and number of bytes present in the file
			ContainerClass rootAndNumOfBytesWhichShow=getHuffmanTree(fileByteArray);
			
			//checking if huffman tree was built successfully
			if (rootAndNumOfBytesWhichShow!=null)
			{
				int numOfBytesWhichShow=rootAndNumOfBytesWhichShow.getVal1();
				Node root=rootAndNumOfBytesWhichShow.getNode1();
				
				//using Path class array to receive each byte value representation as path linkedList 
				PathHead[] paths=new PathHead[256];
				Path pathHead=new Path();
				
				//A2
				//receiving Path for each byte
				int level=setHuffmanCodeToPathsArray(root, 0, paths, pathHead, pathHead);
				
				double bitsForLenght=Math.log(level)/Math.log(2);
				bitsForLenght=Math.ceil(bitsForLenght);
				int numOfBitsForRep=(int) bitsForLenght;
				
				//A3
				//calculating size of compressed file and redundant bits 
				long numOfBitsForFile=calcNumOfBitsForFile(root, numOfBytesWhichShow, (int) bitsForLenght, paths);
				double tempForSizeOfCompressedFile=Math.ceil(numOfBitsForFile*0.125);
				int SizeOfCompressedFile=(int)tempForSizeOfCompressedFile;
				int redundentBits=8-(int) (numOfBitsForFile%8);
				
				//A4
				//generating compressed file to Byte array
				return generateCompressedFile(paths,fileByteArray, numOfBytesWhichShow, numOfBitsForRep, SizeOfCompressedFile, redundentBits);
			}
			return null;
	}
	
		/*
		 * function receive output name for file, and bytes array, and creates the file
		 * and writing the bytes to the file
		 */
		protected void writeCompressedToFile(String output_name, byte[] compressedByteArr)
		{
			FileOutputStream fop = null;
			File file;
			try{
				file = new File(output_name);
				fop = new FileOutputStream(file);
				//checking if file already exist
				if (!file.exists()) {
					file.createNewFile();
				}
				//writing to file
				fop.write(compressedByteArr);
				fop.flush();
				fop.close();
				System.out.println(output_name + " created successfully;");
			} catch (IOException e) {
			e.printStackTrace();
			}finally {
			try {
				if (fop != null) { fop.close(); }
			} catch (IOException e) {
				e.printStackTrace();
				}
			}
		}
	
	
	//Part B: Functions for decompression
	
		//B1: reading bits and recreating the codeBook
		
		/*
		 * function 'getIntValueFromCompressedFile' receive DecompressFile class
		 * containing compressed byte array, and number of bits to be read
		 * and returns the combined int value of the bits
		 */
		private int getIntValueFromCompressedFile(DecompressFile decompressFile, int numOfBits)
		{
			int numToReturn=0;
			boolean isOne;
			//loop starting from the "left" bit to the "right"
			for (int index=numOfBits-1;index>=0;index--)
			{
				isOne=decompressFile.getBitAndMove();
				//if current bit is one than adding his value to the returned number
				if (isOne)
					numToReturn+=Math.pow(2, index);
			}
			return numToReturn;
		}
		
		/*
		 * function 'DecompressedCodeBook' receive DecompressFile class,
		 * number of bytes represented and number of bits needed to know the length
		 * of the representation.
		 * 
		 * function returns a rebuilt codeBook
		 */
		private DecompressedCodeBook getCodeBook(DecompressFile decompressFile, int numOfBytesRepresented, int numOfBitsForRep)
		{
			//creating DecompressedCodeBook object	
			DecompressedCodeBook codeBook=new DecompressedCodeBook(numOfBytesRepresented);
			int byteRepLength;
			for (int curByte=0; curByte<numOfBytesRepresented; curByte++)
			{
				//reading current byte value
				int byteVal=getIntValueFromCompressedFile(decompressFile, 8);
				codeBook.setbyteVal(curByte, (byte) byteVal);
				
				//reading current representation length
				byteRepLength=getIntValueFromCompressedFile(decompressFile, numOfBitsForRep);
				
				//reading first bit of representation to the path in the codeBook
				if (decompressFile.getBitAndMove())
					codeBook.getPath(curByte).setIsBitOne(true);
				else
					codeBook.getPath(curByte).setIsBitOne(false);
				
				//if byte is represented by more than two bits we will enter the loop
				if (byteRepLength>=3)
				{
					//reading the remaining bits for representation but the last
					for (int index=1; index<byteRepLength-1; index++)
						if (decompressFile.getBitAndMove())
							codeBook.setNewPathNode(curByte, true);
						else
							codeBook.setNewPathNode(curByte, false);
				}
				//reading the last bit
				//if there is represented by a single bit it is already entered
				if (byteRepLength>=2)
				{
					Path lastStep=new Path();
					if (decompressFile.getBitAndMove())
					{
						lastStep.setIsBitOne(true);
						codeBook.getPath(curByte).getLastBitPathInPath().setNextPath(lastStep);
					}
					else
					{
						lastStep.setIsBitOne(false);
						codeBook.getPath(curByte).getLastBitPathInPath().setNextPath(lastStep);
					}
				}

			}
			return codeBook;
		}
		
		//B2: rebulding huffman tree
		
		/*
		 * function 'rebuildTree' receives a DecompressedCodeBook class with a filled codebook
		 * and number of bytes represented in the codeBook and returns a rebuilt huffman tree
		 */
		private Node rebuildTree(DecompressedCodeBook codeBook, int numOfBytesRepresented)
		{
			Path start=new Path();
			Node root=new Node(0, -1, null, null);
			Node newLeaf;
			//loop for, inserting the representation into the huffman tree
			for (int index=0; index<numOfBytesRepresented; index++)
			{
				//finding the place for the byte in the tree
				newLeaf=insertToTreeAndGetLeaf(root, codeBook.getPath(index));
				
				//setting the new leaf representation and value
				newLeaf.setBitsRepresentation(codeBook.getPath(index));
				newLeaf.setByteVal(codeBook.getbyteVal(index));
			}
			return root;
		}
	
		
	/*
		 * function 'insertToTreeAndGetLeaf' receive a new node
		 * and a filled path and moves and builds the tree according
		 * to the path
		 */
		private Node insertToTreeAndGetLeaf(Node node, Path path)
	{
		do
		{
			//for bit value '1' 
			if (path.isBitOne()==false)
				if (node.getLeft()==null)
				{
					//building a new branch
					Node newNode=new Node(0,-1,null,null);
					node.setLeft(newNode);
					node=newNode;
					path=path.getNextPath();
				}
				else
				{
					//entering an existing branch
					node=node.getLeft();
					path=path.getNextPath();
				}
			else
				if (node.getRight()==null)
				{
					Node newNode=new Node(0,-1,null,null);
					node.setRight(newNode);
					node=newNode;
					path=path.getNextPath();
				}
				else
				{
					node=node.getRight();
					path=path.getNextPath();
				}
		}while (path != null);
		return node;
	}
	
		
		//B3: using huffman code to recreate the file
		
		/*
		 * function 'getByteValFromHuffmanTree' receive a huffman tree and
		 * a DecompressFile Class with a compressed file in it according to the current
		 * bits and bytes returns a the byte the current representation represent
		 */
		private byte getByteValFromHuffmanTree(Node root, DecompressFile decompressFile)
		{
			//stop condition when we reach a leaf (we can check either left or right)
			if (root.getLeft()==null)
				return (byte) (root.getByteVal()-128);
			//according to the current bit in the compressed file we either move right or left
			if (decompressFile.getBitAndMove())
				return getByteValFromHuffmanTree(root.getRight(), decompressFile);
			else
				return getByteValFromHuffmanTree(root.getLeft(), decompressFile);
		}
		
		/*
		 * function decompresOneFile receives a DecompressFile class
		 * with a compressed file in it, number of represented bytes
		 * and number of bits for representing each byte.
		 * The function returns byte array representing the decompressed file 
		 */
		public byte[] decompresOneFile(DecompressFile decompressFile, int numOfRepresentedBytes, int numOfBitsRepForLetter)
		{
			//getting redundant bits
			int redundentBits=getIntValueFromCompressedFile(decompressFile, 3);
			
			//rebuilding the codeBook B1
			DecompressedCodeBook codeBook=getCodeBook(decompressFile, numOfRepresentedBytes, numOfBitsRepForLetter);
			
			//rebuilding the huffman tree B2
			Node root=rebuildTree(codeBook, numOfRepresentedBytes);
			
			int compressedFileLength=decompressFile.getFileByteArray().length;
			
			//getting first byte value from content
			byte curByteValue=getByteValFromHuffmanTree(root, decompressFile);
			
			//counting the number of bytes in content of compressed file will  allow us to build static byte[] array
			int numOfBytes=2;
			
			//using ByteNode as linkList to dynamically extract the bytes
			ByteNode decompressedByteList=new ByteNode(curByteValue);
			curByteValue=getByteValFromHuffmanTree(root, decompressFile);
			ByteNode curByteNode=new ByteNode(curByteValue);
			decompressedByteList.setNextByte(curByteNode);
			
			//loop through the compressed file and extract the bytes
			//the last byte will not be read in the loop because it might have redundant bits
			while (decompressFile.getCurByteIndex()<compressedFileLength-1)
			{
				curByteValue=getByteValFromHuffmanTree(root, decompressFile);
				curByteNode.setNextByte(new ByteNode(curByteValue));
				curByteNode=curByteNode.getNextByte();
				numOfBytes++;
			}
			
			//if there are no redundant bits, then the last byte contains one more byte
			if (redundentBits==0)
			{
				curByteValue=getByteValFromHuffmanTree(root, decompressFile);
				curByteNode.setNextByte(new ByteNode(curByteValue));
				curByteNode=curByteNode.getNextByte();
				numOfBytes++;
			}
			
			//transferring the dynamic array to a static byte array
			byte[] newFileByteArray=new byte[numOfBytes];
			for (int index=0; index<numOfBytes; index++)
			{
				newFileByteArray[index]=decompressedByteList.getValue();
				decompressedByteList=decompressedByteList.getNextByte();
			}
			return newFileByteArray;
		}
		
		/*
		 * function 'writeDecompressedFile' receives an output name and a
		 * decompressed byte array. It then writes the byte array
		 * into the file, and creates the file if needed
		 */
		public void writeDecompressedFile(String output_name, byte[] decompressedByteArray)
		{
			FileOutputStream fop = null;
			File file;
			try {
				file = new File(output_name);
				fop = new FileOutputStream(file);
				//checking if file exist
				if (!file.exists()) 
					file.createNewFile();
				//writing the file
				fop.write(decompressedByteArray);
				fop.flush();
				fop.close();
				System.out.println(output_name+" was successfuly restored");
	
				} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fop != null) {
						fop.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	//Part C: main functions
		public void Compress(String[] input_names, String[] output_names)
		{
			/* two dimensional byte array. first is index of file, the second is the file byte array.
			 * one for the original files and the other is for the compressed files
			 */
			byte[][] filesCompressedBytesArray=new byte[input_names.length][];
			byte[][] fileByteArray=new byte[input_names.length][];
			
			for (int curFile=0; curFile<input_names.length;curFile++)
				try{
					if (input_names[curFile]!=null)
					{
						//getting byte array for original file
						fileByteArray[curFile]= Files.readAllBytes(new File(input_names[curFile]).toPath());
						
						//getting byte array for compressed file using compressFile function
						filesCompressedBytesArray[curFile]=compressFile(fileByteArray[curFile]);
						
						//calculating success
						int sizeOfOriginalFile=fileByteArray[curFile].length;
						int sizeOfCompressed=filesCompressedBytesArray[curFile].length;
						int precentFromOriginalFile=(sizeOfCompressed*100)/sizeOfOriginalFile;
						
						//if compression successful and optimal a new compressed file will be created 
						if (precentFromOriginalFile<100)
						{
							writeCompressedToFile(output_names[curFile], filesCompressedBytesArray[curFile]);
							System.out.println(" " + precentFromOriginalFile+"% from original size.");
						}
						else
							System.out.println("This comprassion was unable to compress the file: "  
						+ input_names[curFile] + ", size from original: "+ precentFromOriginalFile + "%.");
					}
				}catch(IOException e){}
		}
	
		public void Decompress(String[] input_names, String[] output_names)
		{
			for (int index=0; index<input_names.length; index++)
			{
				byte[] fileByteArray;
				try {
					//checking that file exist
					if (input_names[index]!=null)
					{
						//inserting file into byte array
						fileByteArray = Files.readAllBytes(new File(input_names[index]).toPath());
						
						//creating DecompressFile object and inserting byte array 
						DecompressFile decompressFile=new DecompressFile(fileByteArray);
						
						//getting number of bytes represented in the codeBoook
						int numberOfRepresentedBytes= decompressFile.getCurByte()+128;
						
						//casting earlier to byte might make 256 become -128
						if (numberOfRepresentedBytes==0)
							numberOfRepresentedBytes=256;
						decompressFile.nextByte();
						
						//getting number of bytes needed to represent the bytes
						int numOfBitsRepForLetter=decompressFile.getCurByte()+128;
						decompressFile.nextByte();
						
						//receiving decompressed array
						byte[] decompressedByteArray=decompresOneFile(decompressFile, numberOfRepresentedBytes, numOfBitsRepForLetter);
						
						//restoring the original file
						writeDecompressedFile(output_names[index], decompressedByteArray);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	
		public byte[] CompressWithArray(String[] input_names, String[] output_names)
		{
			byte[][] filesCompressedBytesArray=new byte[input_names.length][];
			byte[][] fileByteArray=new byte[input_names.length][];
			for (int curFile=0; curFile<input_names.length;curFile++)
				try{
					if (input_names[curFile]!=null)
					{
						fileByteArray[curFile]= Files.readAllBytes(new File(input_names[curFile]).toPath());
						filesCompressedBytesArray[curFile]=compressFile(fileByteArray[curFile]);
						int sizeOfOriginalFile=fileByteArray[curFile].length;
						int sizeOfCompressed=filesCompressedBytesArray[curFile].length;
						int precentFromOriginalFile=(sizeOfCompressed*100)/sizeOfOriginalFile;
						if (precentFromOriginalFile<100)
						{
							writeCompressedToFile(output_names[curFile], filesCompressedBytesArray[curFile]);
							System.out.println(" " + precentFromOriginalFile+"% from original size.");
							return filesCompressedBytesArray[curFile];
						}
						else
							System.out.println("This comprassion was unable to compress the file: "  
						+ input_names[curFile] + ", size from original: "+ precentFromOriginalFile + "%.");
					}
				}catch(IOException e){}
			return null;
		}
	
	
		public byte[] DecompressWithArray(String[] input_names, String[] output_names)
		{
			for (int index=0; index<input_names.length; index++)
			{
				byte[] fileByteArray;
				try {
					if (input_names[index]!=null)
					{
						fileByteArray = Files.readAllBytes(new File(input_names[index]).toPath());
						DecompressFile decompressFile=new DecompressFile(fileByteArray);
						int numOfLetters= decompressFile.getCurByte()+128;
						if (numOfLetters==0)
							numOfLetters=256;
						decompressFile.nextByte();
						int numOfBitsRepForLetter=decompressFile.getCurByte()+128;
						decompressFile.nextByte();
						byte[] decompressedByteArray=decompresOneFile(decompressFile, numOfLetters, numOfBitsRepForLetter);
						writeDecompressedFile(output_names[index], decompressedByteArray);
						return decompressedByteArray;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}
	
	}
