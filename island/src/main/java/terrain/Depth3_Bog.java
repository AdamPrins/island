package terrain;

import java.awt.Color;

public class Depth3_Bog extends Terrain {
	
	public Depth3_Bog(int r, int g, int b) {
		color = new Color(150+r,110+g,70+b);
		depth = 3;
	}
}
