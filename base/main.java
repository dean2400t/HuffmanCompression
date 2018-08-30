package base;
/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */
import java.io.File;

import assign1.HufmannEncoderDecoder;


public class main {

	public static void main(String[] args) {
		
		
		HufmannEncoderDecoder hf=new HufmannEncoderDecoder();
		String path="src/txt";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		String input_names[]=new String[listOfFiles.length];
		String output_names[]=new String[listOfFiles.length];
		    for (int i = 0; i < listOfFiles.length; i++) 
		      if (listOfFiles[i].isFile())
		      {
		    	  input_names[i]=path+"/"+listOfFiles[i].getName();
		    	  output_names[i]=path+"/compressed/"+listOfFiles[i].getName()+".compressed";
		      }
		hf.Compress(input_names, output_names);
		
		path="src/txt/compressed";
		File folderCompressed = new File(path);
		File[] listOfCompressedFiles = folderCompressed.listFiles();
		String[] input_compressed_names=new String[listOfCompressedFiles.length];
		String[] output_decompressed_names=new String[listOfCompressedFiles.length];
		String cutName;
		for (int i = 0; i < listOfCompressedFiles.length; i++) 
		      if (listOfCompressedFiles[i].isFile())
		      {
		    	  input_compressed_names[i]=path+"/"+listOfCompressedFiles[i].getName();
		    	  cutName=listOfCompressedFiles[i].getName();
		    	  cutName=cutName.substring(0, cutName.length()-11);
		    	  output_decompressed_names[i]=path+"/decompressed/"+cutName;
		      }
		
		hf.Decompress(input_compressed_names, output_decompressed_names);
		

		
	}
}
