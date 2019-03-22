package gamepackage;

import java.awt.Color;
import java.awt.geom.*;

public class ColorCircle extends Ellipse2D.Float {
	
	public static float height = 30;
	public static float width = 30;
	private Color color;
	
	ColorCircle(float x, float y, Color color) {
		super(x,y,height,width);
		this.color = color;		
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
