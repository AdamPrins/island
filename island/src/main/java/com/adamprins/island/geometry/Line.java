package com.adamprins.island.geometry;

import java.awt.Point;
import java.util.ArrayList;

/**
 * This is the container for the Line geometry
 *  
 * @authors Adam Prins
 * 
 * @version 0.1.0 
 * 		Initial Build
 *		
 */
public class Line {

	private Point p1;
	private Point p2;
	
	/**
	 * This creates a new Line object from two points
	 * p1 will always have the lower x value
	 * 
	 * @param p1 the first point
	 * @param p2 the second point
	 */
	public Line(Point p1, Point p2) {
		if (p1.x<=p2.x) {
			this.p1=p1;
			this.p2=p2;
		}
		else {
			this.p1=p2;
			this.p2=p1;
		}
		
	}
	
	/**
	 * This calculates and returns the midpoint of the line
	 * 
	 * @return the Midpoint of the Line
	 */
	public Point getMidpoint() {
		return new Point((p1.x+p2.x)/2,(p1.y+p2.y)/2);
	}
	
	/**
	 * Returns a copy of the points of this triangle
	 * 
	 * @return a new ArrayList with copies of the contained points
	 */
	public ArrayList<Point> getPoints() {
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(p1.getLocation());
		points.add(p2.getLocation());
		return points;
	}
	
	/**
	 * Compares an Object to this Line to test for equivalence
	 * 
	 * @return boolean true if they are the same, false otherwise.
	 * TODO possible remove one of the comparisons. Since p1 should always be the lower x value
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Line) {
			Line l = (Line)o;
			ArrayList<Point> points = l.getPoints();
			if ((points.get(0).equals(p1) && points.get(1).equals(p2)) ||
				(points.get(0).equals(p2) && points.get(1).equals(p1))) {
				return true;
			}
		}
		return false;
	}
	
}
