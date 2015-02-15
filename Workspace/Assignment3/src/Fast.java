/*----------------------------------------------------------------
 *  Author:        Ketan Nayak
 *  Written:       2/14/2015
 *
 *  Purpose: Check sets of 4 points that are collinear
 *  Detail:  Check for sets of 4 points that are collinear using a fast sort algorithm
 *  
 *  Assignment here: http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 *  Check-list here: http://coursera.cs.princeton.edu/algs4/checklists/collinear.html
 *----------------------------------------------------------------*/
import java.util.Arrays;

public class Fast {
	
	public static void main(String args[]){
		
		In in = new In(args[0]);      // input file
        int N = in.readInt();         // N
        
		Point[] pointset = new Point[N]; //create points array
		
		//Scale the coordinate system
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		
		//Read in the points and draw them
		for(int i = 0; i < N; i++){
            pointset[i] = new Point(in.readInt(),in.readInt());
            pointset[i].draw();    
        }
		
		//sort by lexicographic nature
		Arrays.sort(pointset);
		
		//Create an empty array to store the sorted points
		Point[] referencepointset = new Point[pointset.length];
		
		// Loop through the points on which we will calculate the slope on
		for (int i = 0; i < N; i++){
			
			
			//Transfer over all the points into referencepointset
			System.arraycopy(pointset, 0, referencepointset, 0, pointset.length);
			
			//sort the referencepointset based on the point p
			Arrays.sort(referencepointset, pointset[i].SLOPE_ORDER);
			
			//initialize the previous slope to the slope to itself initially
			double prev_slope = pointset[i].slopeTo(referencepointset[i]);
			
			//Maintain 2 indices that will allow us to loop through the collinear points
			//Start them at zero initially
			int index1 = 0;
			int index2 = 0;
			
			//Consider points for which we need to calculate slope
			for(int j = 0; j < N; j++){
				
				//Calculate the slope for the current point
				double current_slope = pointset[i].slopeTo(referencepointset[j]);
				
				//If the slope of the current point is the same, then increment index2
				if (current_slope == prev_slope){
					index2++;
				} else {
					
					if(index2 - index1 >= 2){	
						
					 	//We need to print only if the starting point is less than all other points
					 	boolean isFirst = true;
					 	
					 	for(int k = index1; k <= index2; k++)
					 		if(pointset[i].compareTo(referencepointset[k]) >= 0) isFirst = false;
					 		
						if(isFirst){
							
							StdOut.print(pointset[i]);
							
							for(int k = index1; k <= index2; k++)
								StdOut.print(" -> " + referencepointset[k]);
							
							StdOut.print("\n");
							
							pointset[i].drawTo(referencepointset[index2]);
						}
					}
					
					//Set both the indices to the current point being looked at
					index1 = j;
					index2 = j;
					
					//Set the prev slope to be the current slope
					prev_slope = current_slope;
					
				}	
			
			}
			
			
			//We need another set of if condition followed by the for loop if the last point looked
			// at in the previous loop is a part of the collinear set			
			 if (index2 - index1 >= 2) {
				 
				 	
				 //We need to print only if the starting point is less than all other points
				 boolean isFirst = true;
				 for(int k = index1; k <= index2; k++)
					 if(pointset[i].compareTo(referencepointset[k]) >= 0) isFirst = false;
				 
				 if(isFirst){
					 StdOut.print(pointset[i]);
					 
					 for(int k = index1; k <= index2; k++)
						 StdOut.print(" -> " + referencepointset[k]);
					 
					 StdOut.print("\n");
					 
					 pointset[i].drawTo(referencepointset[index2]);
				 }
	         }
		}
	}
}

