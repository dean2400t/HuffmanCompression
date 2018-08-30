package base;
/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */

public interface compressor
{
	abstract public void Compress(String[] input_names, String[] output_names);
	abstract public void Decompress(String[] input_names, String[] output_names);

	abstract public byte[] CompressWithArray(String[] input_names, String[] output_names);
	abstract public byte[] DecompressWithArray(String[] input_names, String[] output_names);
}
