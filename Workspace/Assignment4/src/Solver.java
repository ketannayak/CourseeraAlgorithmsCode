/*----------------------------------------------------------------
 *  Author:        Ketan Nayak
 *  Written:       8/21/2015
 *
 *  Purpose: Create a solver that solves grid puzzles using the Board ADT
 *  Detail:  Uses the Board class and implements the A* algorithm to give 
 *  the solution and the number of moves.
 *  
 *  Assignment here: http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 *  Check-list here: http://coursera.cs.princeton.edu/algs4/checklists/8puzzle.html
 *----------------------------------------------------------------*/
import java.util.Comparator;


public class Solver {
  
  private MinPQ<Node> originalGridQ;
  private MinPQ<Node> twinGridQ;
  private final Comparator<Node> hammingComparator = new hammingComparator();
  private final Comparator<Node> manhattanComparator = new manhattanComparator();
  private Node solvedNode = null;
  private boolean solvable = false;
  
  //This is the game node that we will use within the priority tree
  private class Node{
    
	  private Board board;
	  private int moves;
	  private Node prevNode;
	  
	  public Node(Board board, int moves, Node prevNode){
      
		  this.board = board;
		  this.moves = moves;
		  this.prevNode = prevNode;
	  }
   
	  public boolean equals(Object o) {
	    	 	
		  //Same object check	
		  if (o == this) return true;
  
		  //null object check
		  if (o == null) return false;
  
		  //object is different type
		  if (o.getClass() != this.getClass()) return false;
  
		  //We need to check if boards are the same
		  Node that = (Node) o;
		      
		  if (that.board.equals(board)) {
			  return true;
		  }
		  return false;
	  }
  }
  
  //Comparator function - Hamming
  private class hammingComparator implements Comparator<Node> {
      
	  public int compare(Node n, Node m) {
          return n.board.hamming() - m.board.hamming();
      }
	  
  }
  
  //Comparator function - Hamming
  private class manhattanComparator implements Comparator<Node> {
      
	  public int compare(Node n, Node m) {
          return n.board.manhattan() - m.board.manhattan() + n.moves - m.moves;
      }
  }
  
  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial){
      
      //Replace hammingComparator with manhattanComparator
      originalGridQ = new MinPQ<Node>(manhattanComparator);
      twinGridQ = new MinPQ<Node>(manhattanComparator);
      
      Node initialNode = new Node(initial, 0, null);      
      originalGridQ.insert(initialNode);
      
      Node initialTwin = new Node(initial.twin(), 0, null);
      twinGridQ.insert(initialTwin);
      
      //We'll use a boolean to track whether a solution has been found
      boolean hasbeensolved = false;
      
      while(!hasbeensolved){
    	  
    	  // solve Original Grid first
    	  Node currentNode = originalGridQ.delMin();
          if (currentNode.board.isGoal()) {
        	  
        	  solvedNode = currentNode;
              hasbeensolved = true;
              solvable = true;
              
          }

          //In each iteration, we'll search the neighbors and insert them into the tree
          for (Board board : currentNode.board.neighbors()) {
              
        	  //initialize node from neighbor board
        	  Node nextNode = new Node(board, currentNode.moves + 1, currentNode);
        	  
              if (nextNode.equals(currentNode.prevNode)) {
                  continue;
              }

              originalGridQ.insert(nextNode);
          }
          
          // solve the Twin grid simulatenously
          Node currenttwinNode = twinGridQ.delMin();
          if (currenttwinNode.board.isGoal()) {
        	  
        	  //solvedNode will still be null in this case as we found soultion to the twin
        	  hasbeensolved = true;
        	  solvable = false;
        	  
          }
          
          for (Board board : currenttwinNode.board.neighbors()) {

        	  //initialize node from neighbor board
        	  Node nexttwinNode = new Node(board, currenttwinNode.moves + 1, currenttwinNode);
        	  
              if (nexttwinNode.equals(currenttwinNode.prevNode)) {
                  continue;
              }

              twinGridQ.insert(nexttwinNode);
              
          }
          
    	  
      }
      
      
  }
    
  // is the initial board solvable?
  public boolean isSolvable(){ 
	  return solvable;
  }
    
    
  //Gives the number of moves taken to solve the system
  public int moves(){
	  return isSolvable() ?  solvedNode.moves :  -1 ;
  }

  //Set of boards gone through to get to the solution
  public Iterable<Board> solution(){
	  
	  if (isSolvable()) {
		  
		  //create new list and return it
		  Stack<Board> solutionPath = new Stack<Board>();
		  
		  Node currentNode = solvedNode;
		  solutionPath.push(solvedNode.board);
		  
		  while(currentNode.prevNode != null) {
			  solutionPath.push(currentNode.prevNode.board);
			  currentNode = currentNode.prevNode;
		  }
		  
		  return solutionPath;
		  
	  } else {

		  return null;
	  }
  }
    
   
  //Unit tests
  public static void main(String args[]){
	  // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
      
  }
       

}
