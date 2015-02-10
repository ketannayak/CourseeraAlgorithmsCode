/*----------------------------------------------------------------
 *  Author:        Ketan Nayak
 *  Written:       2/10/2015
 *
 *  Purpose:  Create a generic data type RandomizedQueue
 *  Detail:  A randomized queue is similar to a stack or queue, except that
 *  the item removed is chosen uniformly at random from items in the data structure
 *  Implementation: Using a resizing array
 *  
 *  Assignment here: http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 *  Check-list here: http://coursera.cs.princeton.edu/algs4/checklists/queues.html
 *----------------------------------------------------------------*/
import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item> {

	private int N; //size of the queue
	private Item[] a; //array of items
	
	
	//Initialize the queue and also suppress the warning
	@SuppressWarnings("unchecked")
	public RandomizedQueue(){
		a = (Item[]) new Object[2];
	}
	
	//Method to check if the Randomized Queue is empty
	public boolean isEmpty(){
		return N==0;
	}
	
	//Method to return the size of the Randomized Queue
	public int size(){
		return N;
	}

	//Add an item to the queue
	public void enqueue(Item item){
		
	}
	
	//Randomly return and delete an item from the queue
	public Item dequeue(){
		
	}
	
	public Item sample(){
		
	}
	
	//Iterator method to support the iterable interface
		public Iterator<Item> iterator(){
			return new RandomizedQueueIterator();	
		}
		
		//The class that implements the iterator class for this ADT
		private class RandomizedQueueIterator implements Iterator<Item>
		{
			
			//Existence of next is dependent on the current
			public boolean hasNext(){ 
				
			}
			
			//Remove method that needs to be a part of the iterator, but throws exception
			public void remove() { 
				throw new UnsupportedOperationException("This operation is unsupported");
			}
			
			//Find the next item
			public Item next()
			{
				
			}
		}
		
		public static void main(String args[]){
			
		}
		
	
	
}
