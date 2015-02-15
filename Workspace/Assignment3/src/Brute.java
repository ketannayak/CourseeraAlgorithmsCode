/*----------------------------------------------------------------
 *  Author:        Ketan Nayak
 *  Written:       2/14/2015
 *
 *  Purpose: Check sets of 4 points that are collinear
 *  Detail:  Check for sets of 4 points that are collinear using a brute force algorithm
 *  
 *  Assignment here: http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 *  Check-list here: http://coursera.cs.princeton.edu/algs4/checklists/collinear.html
 *----------------------------------------------------------------*/
import java.util.Arrays;

public class Brute {
	
	public static void main(String args[]){
		
		In in = new In(args[0]);      // input file
        int N = in.readInt();         // N
        
		Point[] pointset = new Point[N]; //create points array
		
		//Scale the coordinate system
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		
		//Read in the points and draw them
		for(int i = 0; i < N; i++) {
            pointset[i] = new Point(in.readInt(),in.readInt());
            pointset[i].draw();    
        }
		
		Arrays.sort(pointset);
		
		for(int i = 0; i < N; i++){
			for(int j = i+1; j < N; j++){
				for(int k = j+1; k < N; k++){
					//Run the inner loop only if the three points so far are collinear
					if(pointset[i].slopeTo(pointset[j]) == pointset[i].slopeTo(pointset[k])){
						for(int l = k+1; l < N; l++){
							//Only if the fourth point is collinear with the three already collinear points
							if(pointset[i].slopeTo(pointset[k]) == pointset[i].slopeTo(pointset[l])){
								
								//Print the line segment discovered
								StdOut.println(pointset[i] +" -> "+ pointset[j] +" -> "+ pointset[k] +" -> "+ pointset[l]);
								//Draw the line segment 
								pointset[i].drawTo(pointset[l]);								
							}	
						}
					}		
				}
			}
		}
	}
}
