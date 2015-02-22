/*----------------------------------------------------------------
 *  Author:        Ketan Nayak
 *  Written:       2/15/2015
 *
 *  Purpose: Create a solver that solves grid puzzles using the Board ADT
 *  Detail:  Uses the Board class and implements the A* algorithm to give 
 *  the solution and the number of moves.
 *  
 *  Assignment here: http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 *  Check-list here: http://coursera.cs.princeton.edu/algs4/checklists/8puzzle.html
 *----------------------------------------------------------------*/
public class Solver {
	
	//Number of moves
	private int M;
	
	//Initialize and run the solver	
	public Solver(Board initial){
		
	}
	
	//Tells us whether the grid supplied is solvable or not
	public boolean isSolvable(){
		
	}
	
	//Gives the number of moves taken to solve the system
	public int moves(){
		return isSolvable() ?  M :  -1 ;
	}

	//Set of boards gone through to get to the solution
	public Iterable<Board> solution(){
		
		if(isSolvable()){
			
			
			
		} else{
			return null;
		}
	}
	
	//Unit tests
	public static void main(String args[]){
		
	}
	
	
	
	
}
