package com.adamprins.island.geometry;

import java.awt.Point;

/**
 * This is the container for the Circle geometry
 *  
 * @authors Adam Prins
 * 
 * @version 0.1.0 
 * 		Initial Build
 *		
 */
public class Circle {

	private double centerX;
	private double centerY;
	private double radius;
	
	/**
	 * This creates a new Circle object
	 * 
	 * @param x the x of the center of the circle
	 * @param y the y of the center of the circle
	 * @param r the radius of the circle
	 */
	public Circle (double x, double y, double r) {
		centerX = x;
		centerY = y;
		radius  = r;
	}
	
	/**
	 * returns a boolean that corresponds to whether a given 
	 * point is contained by this circle or not
	 * 
	 * @param point the point that we are testing
	 * @return true if it is inside the circle, false otherwise
	 */
	public boolean contains(Point point) {
		double dist = Math.sqrt(Math.pow((centerX-point.x),2)+Math.pow((centerY-point.y),2));
		return dist<radius;
	}
	
	/**
	 * Returns the x value of the Circles center
	 * 
	 * @return the centerX value
	 */
	public double getCenterX() {
		return centerX;
	}
	
	/**
	 * Returns the y value of the Circles center
	 * 
	 * @return the centerY value
	 */
	public double getCenterY() {
		return centerY;
	}
	
	/**
	 * Returns the value of the Circles radius
	 * 
	 * @return the radius value
	 */
	public double getRadius() {
		return radius;
	}
	
	/**
	 * Returns the top left corner X
	 * 
	 * @return the top left corner X
	 */
	public double getCornerX() {
		return centerX-radius;
	}
	
	/**
	 * Returns the top left corner Y
	 * 
	 * @return the top left corner Y
	 */
	public double getCornerY() {
		return centerY-radius;
	}
	
	
	
	
}
