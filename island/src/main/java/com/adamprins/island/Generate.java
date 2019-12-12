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
 * @version 0.2.0 
 * 		abstracted canvas boundaries into static final fields
 * 		Added functionality for calculating triangle colours
 * 		Added additional comments
 *		
 */
public class Generate {
	
	public static final int CANVAS_BOUNDARIES = 10;
	public static final double CANVAS_SCALE = (Canvas.DRAWING_SIZE-2*CANVAS_BOUNDARIES) / (double)Canvas.DRAWING_SIZE;
	
	/**
	 * Creates an ArrayList of n random points, that are somewhere on the canvas
	 * 
	 * @return an ArrayList of n points
	 */
	public static ArrayList<Point> points(int n) {
		ArrayList<Point> points = new ArrayList<Point>();
		for (int i=0; i<n; i++) {
			points.add(Generate.point());
		}
		
		return points;
	}
	
	/**
	 * Creates a random point that is somewhere on the canvas
	 * Will be 50 pixels from the edge
	 * 
	 * @return a new random Point
	 */
	public static Point point() {
		int x = (int) (Math.random() * Canvas.DRAWING_SIZE * CANVAS_SCALE + CANVAS_BOUNDARIES);
		int y = (int) (Math.random() * Canvas.DRAWING_SIZE * CANVAS_SCALE + CANVAS_BOUNDARIES);
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
