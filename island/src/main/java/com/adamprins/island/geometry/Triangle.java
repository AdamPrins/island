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
 * @version 0.1.0 
 * 		Initial Build
 *		
 */
public class Triangle {
	ArrayList<Point> points;

	/**
	 * Creates a new triangle object from an ArrayList of three points
	 * 
	 * @param points the three points that make up the triangle
	 */
	public Triangle(ArrayList<Point> points) {
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
	 * Returns the color of the triangle
	 * 
	 * @return the color of the triangle
	 */
	public Color getColor() {
		return Color.black;
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
}
