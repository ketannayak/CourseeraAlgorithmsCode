
public class Subset {
	
	public static void main(String args[]){
		
		//Create the random queue
		RandomizedQueue<String> rq = new RandomizedQueue<String>();	        
		
		//Get the value of k
		int k = Integer.parseInt(args[0]);
		
		// read strings from std input:
	    while (!StdIn.isEmpty()){
	    	String s = StdIn.readString();
	        rq.enqueue(s);
	        
	    } 
	    
	    //Print out k items randomly
	    for (int i = 0; i < k; i++){
	    	StdOut.println(rq.dequeue());
	    }
		
		
		
	}

}
