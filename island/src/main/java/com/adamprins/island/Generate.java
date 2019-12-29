package com.adamprins.island;

import java.awt.Point;
import java.util.ArrayList;

import com.adamprins.island.geometry.Circle;
import com.adamprins.island.geometry.Line;
import com.adamprins.island.geometry.Triangle;

/**
 * This is the generation tool for island
 * It handles the generation for all geometry of the application
 *  
 * @authors Adam Prins
 * 
 * @version 0.4.0 
 * 		Added distribution types for point generation
 * 		Types are: even, center, circle, diagonal, two peaks
 * 		
 *		
 */
public class Generate {
	
	public static final int CANVAS_BOUNDARIES = 10;
	public static final int CANVAS_CENTER = Canvas.DRAWING_SIZE/2;
	public static final int CANVAS_RADIUS = Canvas.DRAWING_SIZE/2 - CANVAS_BOUNDARIES;
	public static final double CANVAS_SCALE = (Canvas.DRAWING_SIZE-2*CANVAS_BOUNDARIES) / (double)Canvas.DRAWING_SIZE;
	public static enum Distribution {even, centerBias, circleBias, diagonalBias, twoPeak};
	
	private static final Point peak1 = Generate.point(Distribution.even);
	private static final Point peak2 = Generate.point(Distribution.even);
	
	/**
	 * Creates an ArrayList of n random points, that are somewhere on the canvas
	 * 
	 * @return an ArrayList of n points
	 */
	public static ArrayList<Point> points(int n, Distribution distribution) {
		ArrayList<Point> points = new ArrayList<Point>();
		for (int i=0; i<n; i++) {
			points.add(Generate.point(distribution));
		}
		
		return points;
	}
	
	/**
	 * Creates a random point that is somewhere on the canvas
	 * Will be CANVAS_BOUNDARIES pixels from the edge
	 * Is effected by different types of basis;
	 * 
	 * @param creates a point according to the passed distribution
	 * @return a new random Point
	 */
	public static Point point(Distribution distribution) {
		int x = -1;
		int y = -1;
		if (distribution.equals(Distribution.even)) {
			x = (int) (Math.random() * Canvas.DRAWING_SIZE * CANVAS_SCALE + CANVAS_BOUNDARIES);
			y = (int) (Math.random() * Canvas.DRAWING_SIZE * CANVAS_SCALE + CANVAS_BOUNDARIES);
		}
		else if (distribution.equals(Distribution.centerBias)) {
			x = (int) ((Math.random()-Math.random()) * CANVAS_RADIUS + CANVAS_CENTER);
			y = (int) ((Math.random()-Math.random()) * CANVAS_RADIUS + CANVAS_CENTER);
		}
		else if (distribution.equals(Distribution.circleBias)) {
			double dist;
			do {
				x = (int) (Math.random() * Canvas.DRAWING_SIZE * CANVAS_SCALE + CANVAS_BOUNDARIES);
				y = (int) (Math.random() * Canvas.DRAWING_SIZE * CANVAS_SCALE + CANVAS_BOUNDARIES);
				dist = Math.sqrt(Math.pow((x-CANVAS_CENTER), 2)+Math.pow((y-CANVAS_CENTER), 2));
			} while (dist>CANVAS_RADIUS);
		}
		else if (distribution.equals(Distribution.diagonalBias)) {
			do {
				int line = (int) (Math.random() * Canvas.DRAWING_SIZE * CANVAS_SCALE + CANVAS_BOUNDARIES);
				int xOffset = (int) ((Math.random()-Math.random()) * CANVAS_RADIUS * CANVAS_SCALE);
				int yOffset = (int) ((Math.random()-Math.random()) * CANVAS_RADIUS * CANVAS_SCALE);
				x = line + xOffset;
				y = line + yOffset;
			} while (x<0 || y<0 || x>Canvas.DRAWING_SIZE || y>Canvas.DRAWING_SIZE);
		}
		else if (distribution.equals(Distribution.twoPeak)) {
			int type = (int) (Math.random()*2);
			int peakRadius = Canvas.DRAWING_SIZE/5;
			Point peak;
			
			if (type == 1) peak = peak1;
			else peak = peak2;
			
			double dist;
			do {
				x = (int) ((Math.random()-Math.random()) * peakRadius + peak.x);
				y = (int) ((Math.random()-Math.random()) * peakRadius + peak.y);
				dist = Math.sqrt(Math.pow((x-CANVAS_CENTER), 2)+Math.pow((y-CANVAS_CENTER), 2));
			} while (dist>CANVAS_RADIUS || x<0 || y<0 || x>Canvas.DRAWING_SIZE || y>Canvas.DRAWING_SIZE);
		}
		return new Point(x,y);
	}
	
	/**
	 * Creates an ArrayList of triangles using Delaunay Triangulation on a set
	 * of given points
	 * 
	 * @return an ArrayList of triangles
	 */
	public static ArrayList<Triangle> triangulation(ArrayList<Point> points) {
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		triangles.add(Generate.baseTriangle());
		
		for (Point point:points) {
			Generate.triangulation(triangles, point);
		}
		return triangles;
	}
	
	/**
	 * Creates an ArrayList of triangles using Delaunay Triangulation on a set
	 * of given points
	 * 
	 * @return an ArrayList of triangles
	 */
	public static ArrayList<Triangle> triangulation(ArrayList<Triangle> triangles, Point point) {
		ArrayList<Triangle> badTriangles = new ArrayList<Triangle>();
		ArrayList<Line> badEdges = new ArrayList<Line>();
		
		// Finds any bad triangles and flags them
		for (Triangle triangle:triangles) {
			if (triangle.getCircle().contains(point)) {
				badTriangles.add(triangle);
				badEdges.addAll(triangle.getEdges());
			}
		}
		// Iterates over bad triangles, removing them
		// and adds new connections
		for (Triangle triangle:badTriangles) {
			triangles.remove(triangle);
		}
		//Removes duplicate edges
		//This will be all the inner lines
		for (int i=0; i<badEdges.size(); i++) {
			for (int j=i+1; j<badEdges.size(); j++) {
				if (badEdges.get(i).equals(badEdges.get(j))) {
					badEdges.remove(j);
					badEdges.remove(i);
					i--;
					break;
				}
			}
		}
		//Adds new triangles created from remaining polygon edges to the new point
		for (Line newTriEdge:badEdges) {
			ArrayList<Point> newTri = newTriEdge.getPoints();
			newTri.add(point);
			triangles.add(new Triangle(newTri));
		}
		
		Triangle.calculateColors(triangles);
		return triangles;
	}
	
	/**
	 * Creates a Circle from an ArrayList of 3 points
	 * 
	 * @return a Circle
	 */
	public static Circle circle(ArrayList<Point> points) {
		if (points.size()!=3) {throw new IllegalArgumentException("This can only generate from 3 points");}
		double x1 = points.get(0).x;
		double x2 = points.get(1).x;
		double x3 = points.get(2).x;
		double y1 = points.get(0).y;
		double y2 = points.get(1).y;
		double y3 = points.get(2).y;
		
		double xy1 = (x1*x1+y1*y1);
		double xy2 = (x2*x2+y2*y2);
		double xy3 = (x3*x3+y3*y3);
		
		double A = x1*(y2-y3) - y1*(x2-x3) + x2*y3 - x3*y2;
		double B = xy1*(y3-y2) + xy2*(y1-y3) + xy3*(y2-y1);
		double C = xy1*(x2-x3) + xy2*(x3-x1) + xy3*(x1-x2);
		double D = xy1*(x3*y2-x2*y3) + xy2*(x1*y3-x3*y1) + xy3*(x2*y1-x1*y2);
		
		double x = (-1*B)/(2*A);
		double y = (-1*C)/(2*A);
		double r = Math.sqrt( (B*B+C*C-4*A*D)/(4*A*A) );
		
		return new Circle(x,y,r);
	}
	
	/**
	 * 
	 * @return a base triangle that encompasses the entire canvas
	 */
	public static Triangle baseTriangle() {
		ArrayList<Point> baseTriangle = new ArrayList<Point>();
		baseTriangle.add(new Point(0,-1*Canvas.DRAWING_SIZE));
		baseTriangle.add(new Point(0,Canvas.DRAWING_SIZE));
		baseTriangle.add(new Point(Canvas.DRAWING_SIZE*2,Canvas.DRAWING_SIZE));
		return new Triangle(baseTriangle);
	}
}
