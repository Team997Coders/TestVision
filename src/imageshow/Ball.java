package imageshow;

public class Ball {
	public int x, y, width, height;
	public Ball(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public boolean equals(Object other) {
		Ball b = (Ball) other;
		return b.x == x && b.y == y && b.width == width && b.height == height;
	}
}
