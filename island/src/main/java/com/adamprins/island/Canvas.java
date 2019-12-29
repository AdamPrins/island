package com.adamprins.island;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import com.adamprins.island.geometry.Circle;
import com.adamprins.island.geometry.Triangle;

/**
 * This is the canvas for drawing the geometry of island
 * 
 *  
 * @authors Adam Prins
 * 
 * @version 0.5.0 
 * 		Painting of the triangles is now done in the triangles
 *		
 */
public class Canvas extends JPanel {
		
	private static final long serialVersionUID = 3290417118952335835L;
	
	/* The pixels needed to display the game window */
	public static final int DRAWING_SIZE = 500;
	
	private ArrayList<Triangle> triangles;
	private boolean paintCirclesBool = false;
	private boolean paintTriangleBool = false;
	private boolean paintPointBool = false;
	
	/**
	 * The constructor of this drawing component
	 */
	public Canvas() {
		/* creates a boarder around the canvas */
        setBorder(BorderFactory.createLineBorder(Color.black));
        triangles=new ArrayList<Triangle>();
    }

	/**
	 * Returns the preferred size of this component
	 * 
	 * @return returns the preferred dimensions of the component 
	 */
    public Dimension getPreferredSize() {
        return new Dimension(DRAWING_SIZE,DRAWING_SIZE);
    }
    
    
    /**
     * Paints the component
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);      
        
        /* Clears the canvas */
        g.setColor(Color.white);
        g.fillRect(0, 0, DRAWING_SIZE, DRAWING_SIZE);
        g.setColor(Color.black);
        
        for (Triangle triangle:triangles) {
            triangle.paint(g);
        }

        for (Triangle triangle:triangles) {
        	if (paintPointBool)  	{	paintPoints(g, triangle.getPoints());	}
        	if (paintTriangleBool)  {	paintTriangleOutline(g, triangle); 		}
            if (paintCirclesBool) 	{	paintCircle(g, triangle.getCircle()); 	}
        }
        
        
    }  
    
	/**
     * Paints the given points as small circles
     * 
     * @param g the Graphics to paint on
     * @param points the points you want to draw
     */
    private void paintPoints(Graphics g, ArrayList<Point> points) {
    	g.setColor(Color.blue);
    	int size = 4;
    	for (Point point:points) {
    		g.drawOval(point.x-2, point.y-2, size, size);
    	}
    }
    
    /**
     * Paints a given triangle's outline onto the canvas
     * 
     * @param g
     * @param triangle the triangle you want to draw
     */
    private void paintTriangleOutline(Graphics g, Triangle triangle) {
    	g.setColor(Color.black);
    	g.drawPolygon(triangle.getPolygon());
    }
    
    /**
     * Draws a given circle onto the canvas
     * 
     * @param g the Graphics to paint on
     * @param circle the circle you want to draw
     */
	private void paintCircle(Graphics g, Circle circle) {
    	g.setColor(Color.red);
    	int circleX = (int)(circle.getCornerX());
    	int circleY = (int)(circle.getCornerY());
    	int circleS = (int)(circle.getRadius() * 2);
    	g.drawOval(circleX, circleY, circleS, circleS);
    }
    
    /**
     * sets a new array for drawing triangles
     * 
     * @param triangles the array of triangles that will be drawn
     */
    public void setArray(ArrayList<Triangle> triangles) {
    	this.triangles=triangles;
    }
    
    /**
     * Toggles the visibility of the Points
     */
    public void togglePaintPointBool() {
    	paintPointBool=!paintPointBool;
    }
    
    /**
     * Toggles the visibility of the Triangles
     */
    public void togglePaintTriangleBool() {
    	paintTriangleBool=!paintTriangleBool;
    }
    
    /**
     * Toggles the visibility of the circles
     */
    public void togglePaintCircleBool() {
    	paintCirclesBool=!paintCirclesBool;
    }
    
}
