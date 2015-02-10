/*----------------------------------------------------------------
 *  Author:        Ketan Nayak
 *  Written:       1/26/2015
 *
 *  Purpose: Data type to run the percolation computations for a NxN grid
 *  Execution: java Percolation
 *  Runs only unit tests
 *  
 *  Assignment here: http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *  Check-list here: http://coursera.cs.princeton.edu/algs4/checklists/percolation.html
 *----------------------------------------------------------------*/
public class Percolation {

	private WeightedQuickUnionUF grid; //This instantiates the grid for the Weighted Quick Union method
	private int gridDimension; //Stores the value of N for the NxN grid
	private boolean[] gridState; //Stores of current connectivity state of the grid using a boolean array 
	
	//Constructor to create the grid of points for the percolation
	public Percolation(int N)
	{
		if (N <= 0) throw new IllegalArgumentException("You cannot specify the grid to have size zero or less than zero"); 
		
		//Create the grid with N*N +2 nodes, the last two are dummy
		grid = new WeightedQuickUnionUF((N*N)+2);
		gridDimension = N;
		gridState = new boolean[N*N];
		
	}
	
	//method to validate indices
	private void indexValidator(int i, int j)
	{
		if (i <= 0 || i > gridDimension) throw new IndexOutOfBoundsException(" Row index i out of bounds");
		if (j <= 0 || j > gridDimension) throw new IndexOutOfBoundsException(" Row index j out of bounds");
	}
	
	
	//Convert from X,Y to 1 D values
	private int xyTo1D(int i, int j)
	{
		return (i-1)*gridDimension+j-1;
	}
	

	//Opening a site at row i and column j
	public void open(int i, int j)
	{
		//Throw exceptions if incorrect bounds
		indexValidator(i, j);
		
		// open site (row i, column j) if it is not open already
		if (!isOpen(i, j))
		{
			//Open the particular site
			gridState[xyTo1D(i, j)] = true;	
			//Check which boxes need to be opened (Check all directions as appropriate)
			if ( j > 1 && gridState[xyTo1D(i, j-1)]) grid.union(xyTo1D(i, j), xyTo1D(i, j-1)); //Check left and open
			if ( j < gridDimension && gridState[xyTo1D(i, j+1)]) grid.union(xyTo1D(i, j), xyTo1D(i, j+1)); //Check right and open
			
			if (i > 1 && gridState[xyTo1D(i-1, j)]) //Check top and open
			{
				grid.union(xyTo1D(i, j), xyTo1D(i-1, j)); 
			} else if (i == 1) //Opening top row site - Connect to top dummy in this case
			{
				grid.union(xyTo1D(i, j), gridDimension*gridDimension);
			}
			
			if (i < gridDimension && gridState[xyTo1D(i+1, j)])//Check bottom and open	
			{
				grid.union(xyTo1D(i, j), xyTo1D(i+1, j)); 				
			} else if (i == gridDimension)//Opening bottom row site - Connect to bottom dummy in this case
			{
				grid.union(xyTo1D(i, j), gridDimension*gridDimension+1);
			}
			
		}
	}
	
	//Return if a site is open
	public boolean isOpen(int i, int j)
	{
		//Throw exceptions
		indexValidator(i, j);
		// is site (row i, column j) open?
		return gridState[xyTo1D(i, j)];
	}
	
	//Return if a site is full
	public boolean isFull(int i, int j)
	{
		// is site (row i, column j) full?
		indexValidator(i, j);
		//Site is full if it is connected to any of the sites in the top row
		    return grid.connected(gridDimension*gridDimension, xyTo1D(i, j));
		
	}
	
	
	// does the system percolate?
	public boolean percolates()
	{
		//Percolates only if the last two nodes in the grid are within the same component
		return grid.connected(gridDimension*gridDimension, (gridDimension*gridDimension)+1);
	}
	
	//Main - Just runs some unit tests
	public static void main(String[] args)
	{
		//Trivial case
		StdOut.println("-----Trivial Case-----");
		Percolation percGridtrivial = new Percolation(1);
		
		StdOut.println(percGridtrivial.percolates()); //Should not percolate
		
		percGridtrivial.open(1, 1);
		StdOut.println(percGridtrivial.percolates()); //Should percolate now
		StdOut.println("Answer should be false then true");
		
		//2x2 block case
		StdOut.println("-----2x2 Case-----");
		Percolation percGridtwo = new Percolation(2);
		
		percGridtwo.open(1, 1);
		StdOut.println(percGridtwo.percolates()); //Should not percolate
		
		percGridtwo.open(2, 2);
		StdOut.println(percGridtwo.percolates()); //Should not percolate
		
		percGridtwo.open(2, 1);
		StdOut.println(percGridtwo.percolates()); //Should percolate now
		
		percGridtwo.open(2, 1);
		StdOut.println(percGridtwo.percolates()); //Should percolate now
		
		percGridtwo.open(2, 2);
		StdOut.println(percGridtwo.percolates()); //Should percolate now
		StdOut.println("Answer should be false, false, true, true, true");
		
		
		//3x3 first case
		StdOut.println("-----3x3 first Case-----");
		Percolation percGridthreeone = new Percolation(3);
		
		percGridthreeone.open(1, 2);
		percGridthreeone.open(2, 1);
		percGridthreeone.open(2, 3);
		StdOut.println(percGridthreeone.percolates()); //Should not percolate
		
		percGridthreeone.open(3, 2);
		StdOut.println(percGridthreeone.percolates()); //Should not percolate
		
		percGridthreeone.open(2, 2);
		StdOut.println(percGridthreeone.percolates()); //Should percolate now
		StdOut.println("Answer should be false, false, true");
		
		//3x3 second case
		StdOut.println("-----3x3 second Case-----");
		Percolation percGridthreetwo = new Percolation(3);
		
		percGridthreetwo.open(1, 1);
		percGridthreetwo.open(1, 3);
		percGridthreetwo.open(3, 2);
		StdOut.println(percGridthreetwo.percolates()); //Should not percolate
		
		percGridthreetwo.open(2, 3);
		StdOut.println(percGridthreetwo.percolates()); //Should not percolate
		
		percGridthreetwo.open(3, 1);
		StdOut.println(percGridthreetwo.percolates()); //Should not percolate
		
		percGridthreetwo.open(2, 2);
		StdOut.println(percGridthreetwo.percolates()); //Should percolate now
		StdOut.println("Answer should be false, false, false, true");
		
	}
}
