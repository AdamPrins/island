package terrain;

import java.awt.Color;

public class Depth1_Coast extends Terrain {

	public Depth1_Coast(int r, int g, int b) {
		color = new Color(60+r,200+g,240+b);
		depth = 1;
	}
}
