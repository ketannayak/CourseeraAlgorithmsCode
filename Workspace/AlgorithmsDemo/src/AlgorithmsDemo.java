

public class AlgorithmsDemo {
	
	public static void main(String[] args)
	{
		int N = StdIn.readInt();
		QuickFind quickfindobject = new QuickFind(N);
		while (!StdIn.isEmpty())
		{
		
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (!quickfindobject.connected(p,q))
			{
				quickfindobject.union(p,q);
				StdOut.println(p + "" + q);
				
			}
			
			
		}
		
	}

}


