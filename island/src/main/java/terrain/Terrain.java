package terrain;

import java.awt.Color;
import java.awt.Graphics;

import com.adamprins.island.geometry.Triangle;

/**
 * This is the superclass for all terrain classes. It defines the basic methods and their implementation
 * 
 * @author Adam Prins
 * 
 * @version 0.5.0
 * 		Implementation
 *
 */
public abstract class Terrain {
	
	/* The amount a colour can vary */
	public static final int RANDOM_VARIATION=20;
	/* The Max depth of Terrain */
	public static final int MAX_DEPTH=5;
	
	protected Color color;
	protected int depth;
	
	/**
	 * Returns the colour of the Terrain
	 * 
	 * @return the colour of this terrain
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Returns the depth of the Terrain
	 * 
	 * @return the depth of the terrain, it varies from 0 - MAX_DEPTH
	 */
	public int getDepth() {
		return depth;
	}
	
	/**
	 * Rolls the random offset based on the RANDOM_VARIATION
	 * 
	 * @return an int defining the colour offset
	 */
	public static int colorOffset() {
		return (int)(Math.random()*RANDOM_VARIATION-RANDOM_VARIATION/2);
	}
	
	/**
	 * The method rolls for and fetches a terrain for a given depth, and applies the colour offset to it.
	 * 
	 * @param depth the depth of the triangle
	 * @param r the triangles red offset
	 * @param g the triangles green offset
	 * @param b the triangles blue offset
	 * 
	 * @return the Terrain for the Triangle
	 */
	public static Terrain getTerrain(int depth, int r, int g, int b) {
		double roll = Math.random()*100;
		switch (depth) {
		case 0:
			if (roll<0) {
				
			}
			else {
				return new Depth0_Ocean(r,g,b);
			}
		case 1:
			if (roll<5) {
				return new Depth1_Reef(r,g,b);
			}
			else {
				return new Depth1_Coast(r,g,b);
			}
		case 2:
			if (roll<0) {
				
			}
			else {
				return new Depth2_Beach(r,g,b);
			}
		case 3:
			if (roll<20) {
				return new Depth3_Bog(r,g,b);
			}
			else {
				return new Depth3_Grassland(r,g,b);
			}
		case 4:
			if (roll<0) {
				
			}
			else {
				return new Depth4_Forest(r,g,b);
			}
		default:
			if (roll<0) {
				
			}
			else {
				return new Depth5_Mountain(r,g,b);
			}
		}
		throw new IllegalArgumentException("No Terrain selected. Should be unreachable");
	}
	
	/**
	 * 
	 * @param g the graphics used to draw with
	 * @param triangle the triangle that is being drawn
	 */
	public void paint(Graphics g, Triangle triangle) {
		g.setColor(color);
    	g.fillPolygon(triangle.getPolygon());
	}
	
	
}
