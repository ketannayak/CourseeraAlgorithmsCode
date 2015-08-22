/*----------------------------------------------------------------
 *  Author:        Ketan Nayak
 *  Written:       8/21/2015
 *
 *  Purpose: Create an ADT that represents the board of a 8 puzzle
 *  Detail:  To be used by a solver that employs the A* algorithm
 *  
 *  Assignment here: http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 *  Check-list here: http://coursera.cs.princeton.edu/algs4/checklists/8puzzle.html
 *----------------------------------------------------------------*/
import java.util.Arrays;

public class Board {
	
	private int[][] board;
	private int N = 0;
	
	//Constructor for the Board ADT
	public Board(int[][] blocks){
		
		N = blocks.length;
		board = new int[N][N];
		
		//Set the board to the configuration provided by blocks
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				board[i][j] = blocks[i][j];
			}
		}	
	}

	//Returns the dimension of the puzzle
 	public int dimension(){
		return N;
	}
	
	//Returns the Hamming count
	public int hamming(){
		// Initialize the Hamming count 
		int hammingcount = 0; 
		
		//Loops through the array finding out of place values
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				 if (board[i][j] != i*N + j + 1 & board[i][j] != 0)
					 hammingcount++;
			}
		}	
		
		return hammingcount;
	}
		
	//Returns the Manhattan priority
	public int manhattan(){
	
		int manhattanscore = 0;
		
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				if (board[i][j] != 0)
		        {
		          int posn_i = (board[i][j]-1) / N;
		          int posn_j = (board[i][j]-1) % N;
		      
		          manhattanscore += Math.abs(i-posn_i) + Math.abs(j-posn_j);
		        }	
			}
		}
		
		return manhattanscore;		
	}

	// Returns if the board is the same as the goal board;
	public boolean isGoal(){
		return this.hamming() == 0;
	}
	
	//Create a twin board of this board
	public Board twin(){
		
		//Generate a twin board
		int[][] twinboard = new int[N][N];
	    for (int i = 0; i < N; i++){
	    	for (int j = 0; j < N; j++){
	    		twinboard[i][j] = board[i][j];
	    	}
	    }
	    
	    // Swap adjacent non-blank items
	    boolean swapped = false;
	    //Loop to find a set of non-blank items in the board
	    for (int i = 0; i < N; i++){
	        for (int j = 0; j < N-1; j++){
	          if (twinboard[i][j] != 0 & twinboard[i][j+1] != 0)
	          {
	            //Exchange non zero adjacent entries on the same row
	            swap_positions(twinboard, i, j, i, j + 1);
	            //Set swapped boolean to true
	            swapped = true;
	          }
	          if(swapped) break;
	          
	        }
	        if(swapped) break;
	    }
	    
	    //Create a twin Board object with the modified array
	    Board twin = new Board(twinboard);
	    return twin;
	    
	}
	
	//Equals implementation
	public boolean equals(Object y){
		
		//First check if the objects are the same
		if (y == this) return true;
		//Second check if the empty
	    if (y == null) return false;
	    //Third are the classes the same?
	    if (y.getClass() != this.getClass()) return false;
	    
	    //Cast the object as a Board 
	    Board compared = (Board) y;
	    //Equal if the items are in the exact same position
	    return Arrays.deepEquals(this.board, compared.board);  
	}
	
	//Return all the neighboring boards for this board
	public Iterable<Board> neighbors(){
		
		// Create a Queue to return an Iterable type
		Queue<Board> neighbor_boards = new Queue<Board>();		

		//Create a copy of the board and find the position of the zero
		//while we're at it
		//Generate a twin board
		int[][] templateboard = new int[N][N];
		//Locate the zero
		int z_x = 0;
		int z_y = 0;
		
	    for (int i = 0; i < N; i++){
	    	for (int j = 0; j < N; j++){
	    		templateboard[i][j] = board[i][j];
	        
	    		if(board[i][j] == 0){
	    			z_x = i;
	    			z_y = j;
	    		}
	    	}
	    }
		
	    
	    //There are four possible neighboring boards possible unless the zero
	    //is at one of the edges forcing some restrictions
	    
	    //Not on left side
	    if (z_x != 0){
	    	
	    	swap_positions(templateboard, z_x, z_y, z_x - 1, z_y); 
	        Board leftswappedBoard = new Board(templateboard);
	        neighbor_boards.enqueue(leftswappedBoard);
	        //swap back to get original template board
	        swap_positions(templateboard, z_x - 1, z_y, z_x, z_y);
		      
	    }
	    //Not on right side
	    if (z_x != (N-1)){
	    	
	    	swap_positions(templateboard, z_x, z_y, z_x + 1, z_y); 
	        Board rightswappedBoard = new Board(templateboard);
	        neighbor_boards.enqueue(rightswappedBoard);
	        //swap back to get original template board
	        swap_positions(templateboard, z_x + 1, z_y, z_x, z_y);
	    	
	    }
	    //Not on top side
	    if (z_y != 0){
	    	
	    	swap_positions(templateboard, z_x, z_y, z_x, z_y - 1); 
	        Board topswappedBoard = new Board(templateboard);
	        neighbor_boards.enqueue(topswappedBoard);
	        //swap back to get original template board
	        swap_positions(templateboard, z_x, z_y - 1, z_x, z_y);
	      
	    }
	    //Not on bottom side
	    if (z_y != (N-1)){
	    	
	    	swap_positions(templateboard, z_x, z_y, z_x, z_y + 1); 
	        Board topswappedBoard = new Board(templateboard);
	        neighbor_boards.enqueue(topswappedBoard);
	        //swap back to get original template board
	        swap_positions(templateboard, z_x, z_y + 1, z_x, z_y);
	    	
	    }
	    	
		return neighbor_boards;
		
	}
	
	//Convert the board object into a string of the specified format
	public String toString(){
		
		StringBuilder s = new StringBuilder();
		
		//First print the grid size
	    s.append(N + "\n");
	    
	    //Print each item in it's position	    
	    for (int i = 0; i < N; i++){
	    	for (int j = 0; j < board[0].length; j++){
	    		s.append(String.format("%2d ", board[i][j]));
	    	}
	    	s.append("\n");
	    }
	    
	    return s.toString();
	} 
	
	//We do the swaps a lot for the neighboring functions
	//So it's valuable to define a swap_positions function
	private void swap_positions(int[][] board, int x1, int y1, int x2, int y2){
		
		int temp = board[x1][y1];
	    board[x1][y1] = board[x2][y2];
	    board[x2][y2] = temp;
	}
	
	//Unit tests
	public static void main(String args[]){
		
		//Create 4 points
        //int[][] startgrid =  {{8, 0, 3}, {4, 1, 2}, {7, 6, 5}};
        //int[][] samegrid = {{8, 0, 3}, {4, 1, 2}, {7, 6, 5}};
		int[][] startgrid =  {{4, 6, 5, 11}, {1, 2, 3, 7}, {0, 10, 13, 12}, {9, 8, 15, 14}};
        int[][] samegrid = {{4, 6, 5, 11}, {1, 2, 3, 7}, {0, 10, 13, 12}, {9, 8, 15, 14}};
		
		
		
		
        Board start = new Board(startgrid);
        Board same = new Board(samegrid);
        
        //Test the dimension method
        StdOut.println("----------Dimension check-----------");
        StdOut.println("Dimension should be 3");
        StdOut.println(start.dimension());
        StdOut.println("--------------------------------------");
        
        //Test the hamming method
        StdOut.println("----------Hamming method check-----------");
        StdOut.println("Hamming count should be 5");
        StdOut.println(start.hamming());
        StdOut.println("--------------------------------------");
        
        //Test the manhattan method
        StdOut.println("----------Manhattan method check-----------");
        StdOut.println("Manhattan count should be 10");
        StdOut.println(start.manhattan());
        StdOut.println("--------------------------------------");
        
        //Test the isGoal method
        StdOut.println("----------isGoal check-----------");
        StdOut.println("isGoal should be false");
        StdOut.println(start.isGoal());
        StdOut.println("--------------------------------------");
        
        //Test the toString method
        StdOut.println("----------toString check-----------");
        StdOut.println(start.toString());
        StdOut.println("--------------------------------------");
        
        
        //Test the twin method
        StdOut.println("----------twin check-----------");
        Board twin = start.twin();
        StdOut.println(twin.toString());
        StdOut.println("--------------------------------------");
        
        //Test the equals method
        StdOut.println("----------equals check-----------");
        StdOut.println("equals should be true");
        StdOut.println(start.equals(same));
        StdOut.println("--------------------------------------");
        
        
        //Test the neighbors method
        StdOut.println("----------neighbors check-----------");
        Iterable<Board> neighborset = start.neighbors();
        StdOut.println(start.toString());
        StdOut.println("----------neighboring boards--------");
        //Print all the neighbor boards
        for(Board b: neighborset){
        	StdOut.println(b.toString());
        }
        StdOut.println("--------------------------------------");
	
		
	}
		

}
