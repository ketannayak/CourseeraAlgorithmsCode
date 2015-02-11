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
	private int first = 0; //index of the first item
	private int last = 0; //index of the last item
	
	//Initialize the queue with two elements and also suppress the warning
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

	//Resize the array to a specific size
	private void resize(int newsize){
	
		@SuppressWarnings("unchecked")
		Item[] temp = (Item[]) new Object[newsize];
		for (int i = 0; i < N; i++){
			// account for the wrap around
			temp[i] = a[(first + i) % a.length];	
		}
		a = temp;
        first = 0;
        last  = N;		
	}
	
	//Add an item to the queue
	public void enqueue(Item item){
		
		if (item == null) throw new java.lang.NullPointerException("Can't add Null");
		
		// double size of array if max capacity
		if (a.length == N) resize(2*a.length); 
		//increment last and then save the item
        a[last++] = item;        
        
        // wrap-around
        if (last == a.length) last = 0;          
        N++;
		
	}
	
	//Randomly return and delete an item from the queue
	public Item dequeue(){
		
		if (isEmpty()) throw new java.util.NoSuchElementException("Queue is empty");
		
		//get the index within the queue randomly
		int i =  StdRandom.uniform(N);
		
        Item item = a[(first + i) % a.length]; //remove item from its position in array
        
        //swap this random and last item
        int lastindex = last == 0 ? (a.length-1) : (last-1);
        a[(first + i) % a.length] = a[lastindex];
                
        a[lastindex] = null; // loitering
        N--;
        last = lastindex;
        
        // reduce the size of array
        if (N > 0 && N == a.length/4) resize(a.length/2);
        return item;
		
	}
	
	//Sample an item from the queue without really removing it
	//returns the value of a sample item in the queue
	public Item sample(){
		
		if (isEmpty()) throw new java.util.NoSuchElementException("Queue is empty");
		//return a random number based on the valid indices present in the queue
        return a[(first + StdRandom.uniform(N)) % a.length];
		
	}
	
	//Iterator method to support the iterable interface
	public Iterator<Item> iterator(){
		return new RandomizedQueueIterator();	
	}
		
	//The class that implements the iterator class for this ADT
	private class RandomizedQueueIterator implements Iterator<Item>{
		
		private int index = 0;
		private int[] shuffledIndexes = new int[N];
		
		
		//Existence of next is dependent on the current
		public boolean hasNext(){ 			
			return index < N;
		}
			
		//Remove method that needs to be a part of the iterator, but throws exception
		public void remove(){ 
			throw new UnsupportedOperationException("This operation is unsupported");
		}
			
		//Find the next item
		public Item next(){
			
			if (!hasNext()) throw new java.util.NoSuchElementException("No next item in queue");
	        
			if (index == 0) {
	            for (int i = 0; i < N; i++)
	            	shuffledIndexes[i] = i;
	            StdRandom.shuffle(shuffledIndexes);
	        }
			Item item  = a[(first + shuffledIndexes[index]) % a.length];
			index++;
			
			return item;
			
		}
	}
		
	//Unit testing
	public static void main(String args[]){
		
		//Trivial case
	    StdOut.println("-----Null Case-----");
	    RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
	    StdOut.println("The size is " + rq.size());
	    StdOut.println("Is the Queue empty? :" + rq.isEmpty());
	    
	    //Empty to non-empty to empty to non-empty case
	    //One item case
	    StdOut.println("-----Add one first item-----");
	    rq.enqueue(1);
	    StdOut.println("The size is " + rq.size());
	    StdOut.println("Is the Queue empty? :" + rq.isEmpty());
	    StdOut.println("------Start of contents----------");
	    for(Integer i : rq)
	    	StdOut.println(i);
	    StdOut.println("--------End of contents----------");
	    	
	    //Remove the one item
	    StdOut.println("-----Remove last item------------");
	    rq.dequeue();
	    StdOut.println("The size is " + rq.size());
	    StdOut.println("Is the Queue empty? :" + rq.isEmpty());
	    StdOut.println("------Start of contents----------");
	    for(Integer i : rq)
	    	StdOut.println(i);
	    StdOut.println("--------End of contents----------");
	    
	    
	  //Add a bunch of things at the beginning
	    StdOut.println("-----Add items at beginning and end-----");
	    rq.enqueue(1);
	    rq.enqueue(2);
	    rq.enqueue(3);
	    rq.enqueue(4);
	    rq.enqueue(5);
	    rq.enqueue(6);
	    
	    StdOut.println("The Queue should have : 1,2,3,4,5,6");
	    StdOut.println("The size is " + rq.size());
	    StdOut.println("Is the Deque empty? :" + rq.isEmpty());
	    StdOut.println("------Start of contents----------");
	    for(Integer i : rq)
	    	StdOut.println(i);
	    StdOut.println("--------End of contents----------");
	    
	    StdOut.println("-----Getting a random sample-----");
	    StdOut.println("A sample is " + rq.sample());
	    StdOut.println("A sample is " + rq.sample());
	    StdOut.println("A sample is " + rq.sample());
	    
	    StdOut.println("-----Removing an item-----");
	    StdOut.println("Removed item is  " + rq.dequeue());
	    StdOut.println("Removed item is  " + rq.dequeue());
	    StdOut.println("The Queue should have 4 items");
	    StdOut.println("The size is " + rq.size());
	    StdOut.println("Is the Deque empty? :" + rq.isEmpty());
	    StdOut.println("------Start of contents----------");
	    for(Integer i : rq)
	    	StdOut.println(i);
	    StdOut.println("--------End of contents----------");
	    
	    
	    RandomizedQueue<String> rqString = new RandomizedQueue<String>();
	    rqString.enqueue("A");
	    rqString.enqueue("B");
	    rqString.enqueue("C");
	    rqString.enqueue("D");
	    rqString.enqueue("E");
	    rqString.enqueue("F");
	    
	    StdOut.println("The Queue should have : A,B,C,D,E,F");
	    StdOut.println("The size is " + rqString.size());
	    StdOut.println("Is the Deque empty? :" + rqString.isEmpty());
	    StdOut.println("------Start of contents----------");
	    for(String i : rqString)
	    	StdOut.println(i);
	    StdOut.println("--------End of contents----------");
	    
	    StdOut.println("-----Getting a random sample-----");
	    StdOut.println("A sample is " + rqString.sample());
	    StdOut.println("A sample is " + rqString.sample());
	    StdOut.println("A sample is " + rqString.sample());
	    
	    StdOut.println("-----Removing an item-----");
	    StdOut.println("Removed item is  " + rqString.dequeue());
	    StdOut.println("Removed item is  " + rqString.dequeue());
	    StdOut.println("The Queue should have 4 items");
	    StdOut.println("The size is " + rqString.size());
	    StdOut.println("Is the Deque empty? :" + rqString.isEmpty());
	    StdOut.println("------Start of contents----------");
	    for(String i : rqString)
	    	StdOut.println(i);
	    StdOut.println("--------End of contents----------");
	    
	    
	    
	    
			
	}
		
	
	
}
