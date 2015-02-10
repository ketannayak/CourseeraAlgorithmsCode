
public class QuickUnion {

	private int[] array;
	private int[] arraysize;
	
	public QuickUnion(int N){
		
		array = new int[N];
		
		for (int i=0;i<N;i++){
			array[i] =i;
			arraysize[i]=1;
		}
		
	}
	
	
	public void union(int p, int q)
	{
		if(!connected(p,q))
		{
			array[getroot(p)] = getroot(q);					
		
			if (arraysize[p]<arraysize[q])
			{
				array[getroot(p)] = getroot(q);
				arraysize[q] = arraysize[q]+arraysize[p];			
			} else
			{
				array[getroot(q)] = getroot(p);
				arraysize[p] = arraysize[p]+arraysize[q];
			}
			
		}
	}
	
	
	public boolean connected(int p, int q)
	{		
		return getroot(p)==getroot(q);
	}
	
	
	private int getroot(int q)
	{
		
		while(array[q]!=q)
		{
			array[q] = array[array[q]];
			q = array[q];
			
		}
		return q;
	}
	
	
}
