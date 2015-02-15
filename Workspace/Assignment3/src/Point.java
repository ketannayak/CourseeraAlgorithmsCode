/*----------------------------------------------------------------
 *  Author:        Ketan Nayak
 *  Written:       2/14/2015
 *
 *  Purpose: Create an immutable ADT Point that can be used to check collinearity
 *  Detail:  A point data type that has the methods to compare itself, its slope to
 *  other points
 *  
 *  Assignment here: http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 *  Check-list here: http://coursera.cs.princeton.edu/algs4/checklists/collinear.html
 *----------------------------------------------------------------*/
import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER  = new SLOPE_ORDER();       // YOUR DEFINITION HERE
    
    
    //Adding the comparator to compare by slope order
    private final class SLOPE_ORDER implements Comparator<Point>{
    	
    	//Implement the compare method for the Comparator interface;
    	public int compare(Point first, Point second){
    		
    		//Compare the slopes of the first point to the second one
    		double slope_withFirst = slopeTo(first);
    		double slope_withSecond = slopeTo(second);
    		
    		if(slope_withFirst > slope_withSecond) return +1;
    		else if (slope_withFirst < slope_withSecond) return -1;
    		else return 0;
    	}
    	
    }

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    
    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
    	
    	if (that.y == y && that.x != x) return 0.0; //horizontal line
    	else if (that.y != y && that.x == x) return Double.POSITIVE_INFINITY; //vertical line
    	else if (that.y == y && that.x == x) return Double.NEGATIVE_INFINITY; // same point
    	else return ((double)(that.y - y)) / ((double)(that.x - x)); //other points  (cast slope as double)
        
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
    	
    	// compare the point to another point and return -1,0,+1
    	// depending on the comparison
    	if(y < that.y || (y == that.y && x < that.x)) return -1;
    	else if (y == that.y && x == that.x) return 0;
    	else return +1;
       
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
    	
    	//Create 4 points
        Point origin = new Point(0,0);
        Point xaxis = new Point(1,0);
        Point yaxis = new Point(0,1);
        Point diag = new Point(1,1);
        
        //Test the compareTo method
        StdOut.println("----------CompareTo() check-----------");
        StdOut.println(origin.compareTo(xaxis));
        StdOut.println(diag.compareTo(origin));
        StdOut.println(origin.compareTo(origin));
        StdOut.println(xaxis.compareTo(yaxis));
        StdOut.println(yaxis.compareTo(xaxis));
        StdOut.println("--------------------------------------");
        
        //Test the slopeTo method
        StdOut.println("----------slopeTo() check-----------");
        StdOut.println(origin.slopeTo(xaxis));
        StdOut.println(diag.slopeTo(origin));
        StdOut.println(origin.slopeTo(origin));
        StdOut.println(origin.slopeTo(yaxis));
        StdOut.println(xaxis.slopeTo(yaxis));
        StdOut.println("--------------------------------------");
        
        //Test the comparator
        Point[] points = new Point[4];
        points[0] = origin;
        points[1] = diag;
        points[2] = yaxis;
        points[3] = xaxis;
        Arrays.sort(points,origin.SLOPE_ORDER);
        StdOut.println("----------SLOPE_ORDER check-----------");
        for(Point p:points)
        	StdOut.println(p);
        StdOut.println("--------------------------------------");
        	
        
    
    	
    }
}