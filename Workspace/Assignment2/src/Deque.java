/*----------------------------------------------------------------
 *  Author:        Ketan Nayak
 *  Written:       2/9/2015
 *
 *  Purpose:  Create a generic data type Deque
 *  Detail:  A double-ended queue or deque (pronounced "deck") is a generalization 
 *  of a stack and a queue that supports inserting and removing items from either 
 *  the front or the back of the data structure
 *  Implementation: Using a doubly-linked list
 *  
 *  Assignment here: http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 *  Check-list here: http://coursera.cs.princeton.edu/algs4/checklists/queues.html
 *----------------------------------------------------------------*/
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	
	private Node first; //Stores the first node of the linked list
	private Node last; //Stores the last node of the linked list
	private int N; //Stores the current size of the linked list
	
	//Implements a Node class for a doubly linked list
	private class Node{
		Item item;
		Node next;
		Node previous;
	}
	
	//Creates an empty linked list with one node
	public Deque(){
		
		first = new Node();
		last = new Node();
		
		first.next = null; //Both the next and previous links are null
		first.previous = null;
		
		last = first; //We only have one node	

	}
	
	//Method to check if the Deque is empty
	public boolean isEmpty(){
		return N==0;
	}
	
    //Method to get the size of the items in the Deque
	public int size(){
		return N;	
	}
	
	//Add item to the start of the list
	public void addFirst(Item item){
		
		//throw an exception if item is null
		if (item == null) throw new NullPointerException("You can't add a null item");
		
		if (isEmpty()){
			
			//If list is empty, then we just add the item to the existing node
			last.item = item;
			N++;
			
		} else {
			
			//If list is not empty then add a node to the beginning
			Node oldfirst = first;
			first = new Node();
			first.item = item;
			first.previous = null;
			first.next = oldfirst;
			oldfirst.previous = first;
			N++;
			
		}
		
	}
	
	//Add item to the end of the list
	public void addLast(Item item){
		
		//throw an exception if item is null
		if (item == null) throw new NullPointerException("You can't add a null item");
		
		if (isEmpty()){
			
			//If is empty, then we already have a node, into which we can add an item
			last.item = item;
			N++;
			
		} else {
			
			//Otherwise we need to add a new node to the end
			Node oldlast = last;
			last = new Node();
			last.item = item;
			last.next = null;
			last.previous= oldlast;
			oldlast.next = last;
			N++;
		}
	
	}
	
	//Remove item from the start of the list
	public Item removeFirst(){
		
		if (isEmpty()) throw new java.util.NoSuchElementException("There are no items in the Deque");
		
		//If N is one, we can't remove the only node as we need to keep the node
		Item item = first.item;
		first.item = null;
		N--;

		if (!isEmpty()){

			//If N is more than one, then we need to point the first node to the second one
			first = first.next;
			//We need to make the first one have previous node as null
			first.previous = null;
		}
		
		return item;
	}
	
	//Remove item from the start of the list
	public Item removeLast(){
		
        if (isEmpty()) throw new java.util.NoSuchElementException("There are no items in the Deque");
		
		//If N is one, we can't remove the only node as we need to keep the node
		Item item = last.item;
		last.item = null;
		N--;
		
		if (!isEmpty()){

			//If N is more than one, then we need to point the last node to the second to last one
			last = last.previous;
			//We need to make the last one have next node as null
			last.next = null;
		}
		
		return item;
		
		
		
	}
	
	//Iterator method to support the iterable interface
	public Iterator<Item> iterator(){
		return new DoublyLinkedListIterator();	
	}
	
	//The class that implements the iterator class for this ADT
	private class DoublyLinkedListIterator implements Iterator<Item>
	{
		private Node current = first;
	
		//Existence of next is dependent on the current
		public boolean hasNext(){ 
			return current != null && !isEmpty(); 
		}
		
		//Remove method that needs to be a part of the iterator, but throws exception
		public void remove() { 
			throw new UnsupportedOperationException("This operation is unsupported");
		}
		
		//Find the next item
		public Item next()
		{
			if (!hasNext()) throw new java.util.NoSuchElementException("No items left");
			
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	
	public static void main(String args[]){
		
		//Trivial case
	    StdOut.println("-----Null Case-----");
	    Deque<Integer> dq = new Deque<Integer>();
	    StdOut.println("The size is " + dq.size());
	    StdOut.println("Is the Deque empty? :" + dq.isEmpty());
	    
	    //Empty to non-empty to empty to non-empty case
	    //One item case
	    StdOut.println("-----Add one first item-----");
	    dq.addFirst(1);
	    StdOut.println("The size is " + dq.size());
	    StdOut.println("Is the Deque empty? :" + dq.isEmpty());
	    StdOut.println("------Start of contents----------");
	    for(Integer i : dq)
	    	StdOut.println(i);
	    StdOut.println("--------End of contents----------");
	    	
	    //Remove the one item
	    StdOut.println("-----Remove last item------------");
	    dq.removeLast();
	    StdOut.println("The size is " + dq.size());
	    StdOut.println("Is the Deque empty? :" + dq.isEmpty());
	    StdOut.println("------Start of contents----------");
	    for(Integer i : dq)
	    	StdOut.println(i);
	    StdOut.println("--------End of contents----------");
	    
	    //Add one last item
	    StdOut.println("-----Add one last item-----");
	    dq.addLast(2);
	    StdOut.println("The size is " + dq.size());
	    StdOut.println("Is the Deque empty? :" + dq.isEmpty());
	    StdOut.println("------Start of contents----------");
	    for(Integer i : dq)
	    	StdOut.println(i);
	    StdOut.println("--------End of contents----------");
	    
	    
	    //Add a bunch of things at the beginning
	    StdOut.println("-----Add items at beginning and end-----");
	    dq.addFirst(1);
	    dq.addFirst(0);
	    dq.addFirst(-1);
	    dq.addLast(3);
	    dq.addLast(4);
	    dq.addLast(5);
	    
	    StdOut.println("The Deque should have : -1, 0, 1, 2, 3, 4, 5");
	    StdOut.println("The size is " + dq.size());
	    StdOut.println("Is the Deque empty? :" + dq.isEmpty());
	    StdOut.println("------Start of contents----------");
	    for(Integer i : dq)
	    	StdOut.println(i);
	    StdOut.println("--------End of contents----------");
	    
	    StdOut.println("-----Removing first and last-----");
	    dq.removeFirst();
	    dq.removeLast();
	    StdOut.println("The Deque should have : 0, 1, 2, 3, 4");
	    StdOut.println("The size is " + dq.size());
	    StdOut.println("Is the Deque empty? :" + dq.isEmpty());
	    StdOut.println("------Start of contents----------");
	    for(Integer i : dq)
	    	StdOut.println(i);
	    StdOut.println("--------End of contents----------");
	    
	    
	    //Test on string
	    StdOut.println("--------Test on String----------");
	    Deque<String> dqString = new Deque<String>();
	    StdOut.println("-----Add strings at beginning and end-----");
	    dqString.addFirst("works");
	    dqString.addFirst("code");
	    dqString.addFirst("This");
	    dqString.addLast("just");
	    dqString.addLast("fine");
	    dqString.addLast("!");
	    
	    StdOut.println("The Deque should have : This, code, works, just, fine, !");
	    StdOut.println("The size is " + dqString.size());
	    StdOut.println("Is the Deque empty? :" + dqString.isEmpty());
	    StdOut.println("------Start of contents----------");
	    for(String i : dqString)
	    	StdOut.println(i);
	    StdOut.println("--------End of contents----------");
	    
	    StdOut.println("-----Removing first and last-----");
	    dqString.removeFirst();
	    dqString.removeLast();
	    StdOut.println("The Deque should have : code, works, just, fine");
	    StdOut.println("The size is " + dqString.size());
	    StdOut.println("Is the Deque empty? :" + dqString.isEmpty());
	    StdOut.println("------Start of contents----------");
	    for(String i : dqString)
	    	StdOut.println(i);
	    StdOut.println("--------End of contents----------");
	    
	
	}
	
	
}
