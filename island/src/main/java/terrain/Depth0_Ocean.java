package terrain;

import java.awt.Color;

/**
 * This is the default terrain for Depth 0, the ocean
 * 
 * @author Adam Prins
 * 
 * @version 0.5.0
 * 		Implementation
 */
public class Depth0_Ocean extends Terrain {

	public Depth0_Ocean(int r, int g, int b) {
		color = new Color(15+r,110+g,240+b);
		depth = 0;
	}
}
