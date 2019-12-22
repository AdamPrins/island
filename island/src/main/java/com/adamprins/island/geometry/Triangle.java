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
 * @version 0.4.0
 * 		Removed generic Constructor 
 * 		
 *		
 */
public class Triangle {
	private ArrayList<Point> points;
	private static ArrayList<Point> depth0Points = Generate.baseTriangle().getPoints();
	private static ArrayList<Point> depth1Points = new ArrayList<Point>();
	private Color color;
	public static final int RANDOM_VARIATION=20;
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
	 * This sets the colours of triangles based on how inland they are
	 * 
	 * @param passedTriangles the ArrayList of triangles to be colored
	 */
	public static void calculateColors(ArrayList<Triangle> passedTriangles) {
		ArrayList<Triangle> triangles = new ArrayList<Triangle>(passedTriangles);
		ArrayList<ArrayList<Point>> pointDepths = new ArrayList<ArrayList<Point>>();
		pointDepths.add(new ArrayList<Point>(depth0Points));
		pointDepths.add(new ArrayList<Point>(depth1Points));
		
		int depth=0;
		while (triangles.size()>0) {
			ArrayList<Triangle> usedTriangles = new ArrayList<Triangle>();
			for (Triangle triangle:triangles) {
				ArrayList<Point> points = triangle.getPoints();
				if (points.removeAll(pointDepths.get(depth))) {
					Color color;
					switch (depth) {
					case 0:		//Deep ocean
						color = new Color(15,110,240);
						break;
					case 1:		//Coastal ocean
						color = new Color(60,200,240);
						break;
					case 2:		//Beach
						color = new Color(240,225,70);
						break;
					case 3:		//Grasslands
						color = new Color(22,220,15);
						break;
					case 4:		//Forest
						color = new Color(50,150,35);
						break;
					default:	//Hills
						color = new Color(130,140,140);
						break;
					}
					color = new Color(color.getRed()+triangle.r,color.getGreen()+triangle.g,color.getBlue()+triangle.b);
					triangle.setColor(color);
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
	
	/**
	 * A different color scheme I made. This outputs the trans flag.
	 * 
	 * @param passedTriangles the ArrayList of triangles to be colored
	 */
	public static void calculateColorsAlt(ArrayList<Triangle> passedTriangles) {
		ArrayList<Triangle> triangles = new ArrayList<Triangle>(passedTriangles);
		for (Triangle triangle:triangles) {
			int circleY = (int)triangle.getCircle().getCenterY();
			Color color;
			if (circleY<100 || circleY>400) {
				color = new Color(85,205,245);
			}
			else if (circleY<200 || circleY>300) {
				color = new Color(245,168,184);
			}
			else {
				color = new Color(245,245,245);
			}
			color = new Color(color.getRed()+triangle.r,color.getGreen()+triangle.g,color.getBlue()+triangle.b);
			triangle.setColor(color);
		}
	}
	
	public static void addDepth0Point(Point point) {
		depth0Points.add(point);
	}
	
	public static void addDepth1Point(Point point) {
		depth1Points.add(point);
	}
	
}
