package com.adamprins.island.geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import com.adamprins.island.Canvas;
import com.adamprins.island.Generate;

import terrain.*;

/**
 * This is the container for the triangle geometry
 * It is stored locally as a list of 3 points, and is able
 * to pass different representations of the triangle when asked
 *  
 * @authors Adam Prins
 * 
 * @version 0.5.0
 * 		Triangles have a Terrain now
 * 		Triangles hold individual offset rgb now
 * 		Triangles now have a paint method that invokes the paint in the terrain 
 * 		Added a setTerrain() method
 * 		
 *		
 */
public class Triangle {
	private ArrayList<Point> points;
	private Circle circle;
	private Terrain terrain;
	private int r;
	private int g;
	private int b;

	/**
	 * Creates a new triangle object from an ArrayList of three points
	 * 
	 * @param points the three points that make up the triangle
	 */
	public Triangle(ArrayList<Point> points) {
		r=Terrain.colorOffset();
		g=Terrain.colorOffset();
		b=Terrain.colorOffset();
		terrain=new Depth0_Ocean(r,g,b);
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
	 * Sets the terrain of the triangle
	 */
	public void setTerrain(int depth) {
		this.terrain=Terrain.getTerrain(depth, r, g, b);
	}
	
	/**
	 * Returns the color of the triangle
	 * 
	 * @return the color of the triangle
	 */
	public Color getColor() {
		return terrain.getColor();
	}
	
	/**
	 * Returns the depth of the triangle
	 * 
	 * @return the depth of the triangle
	 */
	public int getDepth() {
		return terrain.getDepth();
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
	
	public void paint(Graphics g) {
		terrain.paint(g, this);
	}
	
	/**
	 * This sets the colours of triangles based on how inland they are
	 * 
	 * @param passedTriangles the ArrayList of triangles to be colored
	 */
	public static void calculateColors(ArrayList<Triangle> passedTriangles) {
		ArrayList<Triangle> triangles = new ArrayList<Triangle>(passedTriangles);
		ArrayList<ArrayList<Point>> pointDepths = new ArrayList<ArrayList<Point>>();
		for (int i=0; i<Terrain.MAX_DEPTH; i++) {
			pointDepths.add(new ArrayList<Point>());
		}
		for (Triangle triangle:triangles) {
			ArrayList<Point> points = triangle.getPoints();
			for (Point point:points) {
				int span=3;
				if (point.x<span*Generate.CANVAS_BOUNDARIES ||
					point.y<span*Generate.CANVAS_BOUNDARIES ||
					point.x>(Canvas.DRAWING_SIZE-span*Generate.CANVAS_BOUNDARIES) || 
					point.y>(Canvas.DRAWING_SIZE-span*Generate.CANVAS_BOUNDARIES)) {
					pointDepths.get(0).add(point);
				}
			}
		}
		
		int depth=0;
		while (triangles.size()>0) {
			ArrayList<Triangle> usedTriangles = new ArrayList<Triangle>();
			for (Triangle triangle:triangles) {
				ArrayList<Point> points = triangle.getPoints();
				if (points.removeAll(pointDepths.get(depth))) {
					if (triangle.getDepth()!=depth) {
						triangle.setTerrain(depth);
					}
					
					if (pointDepths.size()<=(depth+1)) {
						pointDepths.add(new ArrayList<Point>());
					}
					pointDepths.get(depth+1).addAll(points);
					usedTriangles.add(triangle);
				}
			}
			triangles.removeAll(usedTriangles);
			depth++;
		}
	}
}
