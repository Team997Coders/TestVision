package imageshow;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * NOTE NEED TO COPY (or drag) pick2 FROM THE USB TO THE DESKTOP BACKGROUND
 */

//this is creating the frame that will display the image.
public class TestIsPixelInBall {

	public static void main(String[] args){

		//objects for the frames/panels
		JFrame frame = new JFrame("afjklasd;fjl");
		ImagePanelx panel = new ImagePanelx();

		frame.add(panel);
		frame.setSize(300,300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}


//this is creating the panel that is going to be shown in the frame.
class ImagePanelx extends JPanel{
    private BufferedImage image;

    public ImagePanelx() {
		try {
			image = ImageIO.read(new File("C:\\Users\\999\\Downloads\\orangeBall.jpg"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		BallDetector b = new BallDetector(image);
		Graphics g = image.getGraphics();
		int w = image.getWidth(), h = image.getHeight();
		for (int x = 0; x != w; ++x) {
			for (int y = 0; y != h; ++y) {
				if (b.isPixelInBall(x, y)) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.RED);
				}
				g.drawRect(x, y, 1, 1);
			}
		}
    }

	//it displays things in the panel.
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth()/2, image.getHeight()/2, null);
    }
}
