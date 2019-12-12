package com.adamprins.island.geometry;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import com.adamprins.island.Generate;

/**
 * This is the container for the triangle geometry
 * It is stored locally as a list of 3 points, and is able
 * to pass different representations of the triangle when asked
 *  
 * @authors Adam Prins
 * 
 * @version 0.2.0 
 * 		Added internal colour storage
 * 		Added set color method
 * 		Added caculateColors method that calculates triangle colors from their distance to the canvas edge
 * 		
 *		
 */
public class Triangle {
	ArrayList<Point> points;
	Color color;

	/**
	 * Creates a new triangle object from an ArrayList of three points
	 * 
	 * @param points the three points that make up the triangle
	 */
	public Triangle(ArrayList<Point> points) {
		color=Color.blue;
		if (points.size()!=3) {
			throw new IllegalArgumentException("Triagnles can only be created using 3 points");
		}
		else {
			this.points=points;
		}
	}
	
	/**
	 * Creates a new triangle object from 3 random new points
	 */
	public Triangle() {
		this(Generate.points(3));
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
		return Generate.circle(points);
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
	 * This sets the colours of triangles based on how inland they are
	 */
	public static void calculateColors(ArrayList<Triangle> passedTriangles) {
		ArrayList<Triangle> triangles = new ArrayList<Triangle>(passedTriangles);
		ArrayList<ArrayList<Point>> pointDepths = new ArrayList<ArrayList<Point>>();
		pointDepths.add(Generate.baseTriangle().getPoints());
		
		int depth=0;
		while (triangles.size()>0) {
			ArrayList<Triangle> usedTriangles = new ArrayList<Triangle>();
			for (Triangle triangle:triangles) {
				ArrayList<Point> points = triangle.getPoints();
				if (points.removeAll(pointDepths.get(depth))) {
					switch (depth) {
					case 0:
						triangle.setColor(Color.blue);
						break;
					case 1:
						triangle.setColor(Color.cyan);
						break;
					case 2:
						triangle.setColor(Color.yellow);
						break;
					case 3:
						triangle.setColor(Color.green);
						break;
					case 4:
						triangle.setColor(Color.LIGHT_GRAY);
						break;
					default:
						triangle.setColor(Color.white);
						break;
					}
					if (pointDepths.size()<=(depth+1)) {
						pointDepths.add(new ArrayList<Point>());
					}
					pointDepths.get(depth+1).addAll(points);
					usedTriangles.add(triangle);
				};
			}
			triangles.removeAll(usedTriangles);
			depth++;
		}
	}
	
	
}
