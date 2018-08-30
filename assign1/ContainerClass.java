package assign1;

/**
 * Assignment 1
 * Submitted by: 
 * Student 1.Dean Moravia 	ID# 302491741
 * Student 2.Hana Razilov 	ID# 31178472
 * Student 3.Aviya Shimon 	ID# 302813217
 */

import java.util.PriorityQueue;

//class used in order to return more than one variable from function
public class ContainerClass {
	private int val1;
	private Node node1;
	private EngNode engNode;
	private PriorityQueue<EngNode> queue;
	public ContainerClass()
	{
		
	}
	public void setVal1(int val){val1=val;}
	public int getVal1(){return val1;}
	
	public void setNode1(Node node){node1=node;}
	public Node getNode1(){return node1;}
	public EngNode getEngNode() {
		return engNode;
	}
	public void setEngNode(EngNode engNode) {
		this.engNode = engNode;
	}
	public PriorityQueue<EngNode> getQueue() {
		return queue;
	}
	public void setQueue(PriorityQueue<EngNode> queue) {
		this.queue = queue;
	}
}
