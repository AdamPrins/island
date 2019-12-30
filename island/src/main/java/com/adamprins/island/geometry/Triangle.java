package com.adamprins.island.geometry;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import com.adamprins.island.Canvas;
import com.adamprins.island.Generate;

/**
 * This is the container for the triangle geometry
 * It is stored locally as a list of 3 points, and is able
 * to pass different representations of the triangle when asked
 *  
 * @authors Adam Prins
 * 
 * @version 0.5.0
 * 		RANDOME_VARIATION set to 0 
 * 		Edited colouring method
 * 		
 *		
 */
public class Triangle {
	private ArrayList<Point> points;
	private Color color;
	public static final int RANDOM_VARIATION=0;
	public final int r;
	public final int g;
	public final int b;
	private Circle circle;

	/**
	 * Creates a new triangle object from an ArrayList of three points
	 * 
	 * @param points the three points that make up the triangle
	 */
	public Triangle(ArrayList<Point> points) {
		r=(int)(Math.random()*RANDOM_VARIATION-RANDOM_VARIATION/2);
		g=(int)(Math.random()*RANDOM_VARIATION-RANDOM_VARIATION/2);
		b=(int)(Math.random()*RANDOM_VARIATION-RANDOM_VARIATION/2);
		color=Color.white;
		if (points.size()!=3) {
			throw new IllegalArgumentException("Triagnles can only be created using 3 points");
		}
		else {
			this.points=points;
		}
	}
	
	/**
	 * Returns a Polygon made up of the contained points
	 * 
	 * @return a Polygon formed of the contained points
	 */
	public Polygon getPolygon() {
		Polygon polygon = new Polygon();
		for (Point point:points) {
			polygon.addPoint(point.x, point.y);
		}
		return polygon;
	}
	
	/**
	 * Returns a Circle that this triangle forms
	 * 
	 * @return a Circle
	 */
	public Circle getCircle() {
		if (circle == null) {
			circle = Generate.circle(points);
		}
		return circle;
	}
	
	/**
	 * Returns a copy of the points of this triangle
	 * 
	 * @return a new ArrayList with copies of the contained points
	 */
	public ArrayList<Point> getPoints() {
		ArrayList<Point> points = new ArrayList<Point>();
		for (Point point:this.points) {
			points.add(point.getLocation());
		}
		return points;
	}
	
	/**
	 * Returns a copy of the edges, in the form of an ArrayList of Line objects
	 * 
	 * @return a new ArrayList containing three Line objects
	 */
	public ArrayList<Line> getEdges() {
		ArrayList<Line> edges = new ArrayList<Line>();
		edges.add(new Line(points.get(0),points.get(1)));
		edges.add(new Line(points.get(0),points.get(2)));
		edges.add(new Line(points.get(1),points.get(2)));
		return edges;
	}
	
	/**
	 * Sets the color of the triangle
	 */
	public void setColor(Color color) {
		this.color=color;
	}
	
	/**
	 * Returns the color of the triangle
	 * 
	 * @return the color of the triangle
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 *
	 */
	@Override
	public String toString() {
		String s = "";
		s+=points.get(0)+"\n";
		s+=points.get(1)+"\n";
		s+=points.get(2)+"\n";
		return s;
	}
	
	/**
	 * This sets the colours of triangles based on the given image
	 * 
	 * @param passedTriangles the ArrayList of triangles to be colored
	 */
	public static void calculateColors(ArrayList<Triangle> triangles) {
		
		
		for (Triangle triangle:triangles) {
			ArrayList<Point> points = triangle.getPoints();
			int max_x = points.get(0).x;
			int max_y = points.get(0).y;
			int min_x = points.get(0).x;
			int min_y = points.get(0).y;
			
			for (int i=1; i<3; i++) {
				if (points.get(i).x>max_x)	max_x=points.get(i).x;
				if (points.get(i).y>max_y)	max_y=points.get(i).y;
				if (points.get(i).x<min_x)	min_x=points.get(i).x;
				if (points.get(i).y<min_y)	min_y=points.get(i).y;
			}
			if (max_x>=Canvas.DRAWING_SIZE) max_x=Canvas.DRAWING_SIZE-1;
			if (max_y>=Canvas.DRAWING_SIZE) max_x=Canvas.DRAWING_SIZE-1;
			if (min_x<0) min_x=0;
			if (min_y<0) min_y=0;
			
			int r=0;
			int g=0;
			int b=0;
			int pixels=0;
			Polygon poly = triangle.getPolygon();
			for (int x=min_x; x<max_x; x++) {
				for (int y=min_y; y<max_y; y++) {
					if (poly.contains(x, y)) {
						int[] pixel = Generate.pixelColor(x, y);
						r+=pixel[0];
						g+=pixel[1];
						b+=pixel[2];
						pixels++;
					}
					
				}
			}
			if (pixels==0) {
				int[] pixel = Generate.pixelColor(min_x, min_y);
				r+=pixel[0];
				g+=pixel[1];
				b+=pixel[2];
				pixels++;
			}
			
			triangle.setColor(new Color(r/pixels,g/pixels,b/pixels));
		}
	}
}
