package imageshow;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class BallDetector {
	private BufferedImage _image;
	private int width, height;
	public static final int BALL_CAMERA_WIDTH = 100;

	public BallDetector(BufferedImage image) {
		_image = image;
		width = _image.getWidth();
		height = _image.getHeight();
	}

	public boolean isPixelInBall(int x, int y) {
		if (x >= width || x < 0 ||
			y >= height || y < 0) {
			return false;
		}

		final int THRESHOLD = 42;

		final Color rgb = new Color(_image.getRGB(x, y));
		final int red = rgb.getRed();
		final int green = rgb.getGreen();
		final int blue = rgb.getBlue();

		final boolean inThreshold =
				(Math.abs(red - 183) < THRESHOLD &&
				  Math.abs(green - 105) < THRESHOLD &&
				  Math.abs(blue - 83) < THRESHOLD);
		//final boolean isAvgInRange = (average >= GRAYMIN && average <= GRAYMAX);
		return inThreshold;
	}
	
	public int findHorizontalEdge(int x, int y, int deltaX) {
		if (Math.abs(deltaX) == 1) {
			return x;
		} else if (isPixelInBall(x, y)) {
			return findHorizontalEdge(x + deltaX, y, deltaX); 
		} else {
			return findHorizontalEdge(x - deltaX / 2, y, deltaX / 2);
		}
	}
	
	public int findVerticalEdge(int x, int y, int deltaY) {
		if (Math.abs(deltaY) == 1) {
			return y;
		} else if (isPixelInBall(x, y)) {
			return findVerticalEdge(x, y + deltaY, deltaY); 
		} else {
			return findVerticalEdge(x, y - deltaY / 2, deltaY / 2);
		}
	}
	
	/** @predicate isPixelInBall(x, y) */
	public Ball findBall(int x, int y) {
		// b = ball
		int bx, by, bwidth, bheight;

		final int SEARCH_WIDTH = 200;
		int leftx = findHorizontalEdge(x, y, -SEARCH_WIDTH);
		int rightx = findHorizontalEdge(x, y, SEARCH_WIDTH);
		int centerx = (leftx + rightx) / 2;
		int btopy = findVerticalEdge(centerx, y, -SEARCH_WIDTH);
		int bboty = findVerticalEdge(centerx, y, SEARCH_WIDTH);
		int centery = (btopy + bboty) / 2;
		int bleftx = findHorizontalEdge(centerx, centery, -SEARCH_WIDTH);
		int brightx = findHorizontalEdge(centerx, centery, SEARCH_WIDTH);

		bx = bleftx;
		by = btopy;
		bwidth = brightx - bleftx;
		bheight = bboty - btopy;

		double div = (double) bwidth / bheight;

		double threshold = 0.5;

		if (div < 1 - threshold || div > 1 + threshold) {
			return null;
		} else {
			return new Ball(bx, by, bwidth, bheight);
		}
	}

	public ArrayList<Ball> findBalls() {
		ArrayList<Ball> list = new ArrayList<Ball>();
		for (int x = 0; x < width; x += BALL_CAMERA_WIDTH) {
			for (int y = 0; y < height; y += BALL_CAMERA_WIDTH) {
				if (isPixelInBall(x, y)) {
					Ball b = findBall(x, y);
					if (b != null || list.contains(b)) {
						list.add(b);
					}
				}
			}
		}
		return list;
	}
	
	/*public static void main(String[] args) {
		try {
			BallDetector bd = new BallDetector(ImageIO.read(new File("C:\\Users\\999\\Downloads\\orangeBall.jpg")));
			ArrayList<Ball> balls = bd.findBalls();
			for (Ball b : balls) {
				System.out.printf("Ball at %d, %d.  W: %d, H: %d\n", b.x, b.y, b.width, b.height);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
