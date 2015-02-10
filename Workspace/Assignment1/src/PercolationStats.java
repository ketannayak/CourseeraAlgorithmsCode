/*----------------------------------------------------------------
 *  Author:        Ketan Nayak
 *  Written:       1/26/2015
 *
 *  Purpose : Data type to run the T simulations on a NxN grid
 *  Execution : java PercolationStats 200 100
 *  
 *  Assignment here: http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *  Check-list here: http://coursera.cs.princeton.edu/algs4/checklists/percolation.html
 * 
 *----------------------------------------------------------------*/

public class PercolationStats {

    private double[] percolationTresholds; //Double array storing the percolation threshold for each simulation

    public PercolationStats(int N, int T)
	{
		// perform T independent experiments on an N-by-N grid
		if (N <= 0 || T <= 0) throw new IllegalArgumentException("You cannot specify the grid to have size zero or less than zero");
		
		percolationTresholds = new double[T];
		//Generate simulations
		for (int i = 0; i < T; i++)
		{
			double numflow = 0;
			Percolation perc = new Percolation(N);
			while (!perc.percolates())
			{
				int row = StdRandom.uniform(1, N+1);
				int col =  StdRandom.uniform(1, N+1);
				if (!perc.isOpen(row, col))
				{
					perc.open(row, col);
					numflow += 1;
				}

			}		
			percolationTresholds[i] = numflow/(N*N);
		}
	
	}
	 
	public double mean()
	{
		// sample mean of percolation threshold
		double sum = 0;
		for (int i = 0; i < percolationTresholds.length; i++)
			sum += percolationTresholds[i];
		
		return sum/percolationTresholds.length;
	}
	   
	public double stddev()
	{
		// sample standard deviation of percolation threshold
		double sum = 0;
		double mean = mean();
		if (percolationTresholds.length != 1)
		{	
			for (int i = 0; i < percolationTresholds.length; i++)
				sum += (percolationTresholds[i]-mean)*(percolationTresholds[i]-mean);
		
			return Math.sqrt(sum/(percolationTresholds.length-1));
		} else
		{
			return Double.NaN;
		}
	}
	 
	public double confidenceLo()
	{
		// low  endpoint of 95% confidence interval
		return mean()-(1.96*stddev()/Math.sqrt(percolationTresholds.length));
	}
	 
	public double confidenceHi()
	{
		// high endpoint of 95% confidence interval
		return mean()+(1.96*stddev()/Math.sqrt(percolationTresholds.length));
	}

	public static void main(String[] args)
	{
		
		
		if (args.length != 2) throw new IllegalArgumentException("You can only specify two integers");
		
		PercolationStats percStat = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		StdOut.println("mean                    =" + percStat.mean()); //Mean
		StdOut.println("stddev                  =" + percStat.stddev()); //Standard Deviation
		StdOut.println("95% confidence interval =" + percStat.confidenceLo()+", "+percStat.confidenceHi()); //Confidence level
		 
		
		
	}
	
}
