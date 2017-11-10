package tests;

public class Pixel {

	class Color {
		int r;
		int g;
		int b;
	};

	private Color color;

	public void setColor(int r, int g, int b) {
		color.r = r;
		color.r = g;
		color.b = b;
	}
	
	public void setColor(int[] rgb) {
		color.r = rgb[0];
		color.r = rgb[1];
		color.b = rgb[2];
	}
	
	public int[] getColor() {
		int c[] = new int[3];
		c[0] = color.r;
		c[1] = color.g;
		c[2] = color.b;
		return c;
	}

}
