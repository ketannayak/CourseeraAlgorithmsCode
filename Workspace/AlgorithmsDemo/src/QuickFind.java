
public class QuickFind
{
	
	private int[] array;
	
	public QuickFind(int N)
	{		
		array = new int[N];
		
		for (int i=0;i<N;i++){
			array[i] =i;
		}
		
	}
	
	void union (int p, int q)
	{
		if(!connected(p,q))
		{
			int qid = array[q];
			int pid = array[p];
			
			for(int i=0;i<array.length;i++){
				if(array[i]==pid)
					array[i]=qid;
			}
					
		}
		
		
	}
	
	
	boolean connected(int p, int q)
	{
		return array[p]==array[q];
	}

}
