package terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.adamprins.island.Generate;
import com.adamprins.island.Generate.Distribution;
import com.adamprins.island.geometry.Triangle;

/**
 * A secondary type for depth 1, replacing the coast
 * 
 * @author Adam Prins
 * 
 * @version 0.5.0
 * 		Implementation
 * 		repeatable generation of reef circles (kind of ugly)
 *
 */
public class Depth1_Reef extends Terrain {
	
	private ArrayList<Point> reefs;

	public Depth1_Reef(int r, int g, int b) {
		color = new Color(60+r,200+g,240+b);
		depth = 1;
	}
	
	/**
	 * Draws the Triangle with super.paint()
	 * Then draws the reef circles
	 */
	public void paint(Graphics g, Triangle triangle) {
		super.paint(g, triangle);
		
		if (reefs == null) {
			reefs = new ArrayList<Point>();
			while(reefs.size()<10) {
				Point p = Generate.point(Distribution.even);
				if (triangle.getPolygon().contains(p)) {
					reefs.add(p);
				}
			}
		}
		int t=3;
		for (Point p:reefs) {
			t++;
			int red=((int)(Math.pow(color.getRed(),t))%1000000000)%255;
			int green=(int)(Math.pow(color.getGreen(),t)%1000000000)%255;
			int blue=(int)(Math.pow(color.getBlue(),t)%1000000000)%255;
			g.setColor(new Color(red,green,blue));
			g.fillOval(p.x, p.y, 10, 10);
		}
	}
}
