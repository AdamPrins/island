package terrain;

import java.awt.Color;

public class Depth3_Grassland extends Terrain {
	
	public Depth3_Grassland(int r, int g, int b) {
		color = new Color(22+r,220+g,15+b);
		depth = 3;
	}
}
