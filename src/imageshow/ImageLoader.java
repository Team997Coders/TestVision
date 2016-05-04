package imageshow;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * NOTE NEED TO COPY (or drag) pick2 FROM THE USB TO THE DESKTOP BACKGROUND
 */

//this is creating the frame that will display the image.
public class ImageLoader {

	public static void main(String[] args){

		//objects for the frames/panels
		JFrame frame = new JFrame("Image Frame!"); //Object for it and the string is the name for it.
		ImagePanelx panel = new ImagePanelx();

		//stuff needed for the frame to use.
		frame.add(panel);
		frame.setSize(300,300);					//sets the size of the frame
		frame.setLocationRelativeTo(null);		//default location of the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//what happends when you close it.
		frame.setVisible(true);					//lets you see it....

		// find the image
		//BallDetector detector = new BallDetector(image);
	}
}


//this is creating the panel that is going to be shown in the frame.
class ImagePanel extends JPanel{

    private BufferedImage image;
	//constructor for the panel
    public ImagePanel() {
		//it tries to set the image and if it does not work then.... it does an exception of some kind
		try {
			image = ImageIO.read(new File("C:\\Users\\999\\Downloads\\orangeBall.jpg")); //the string is the location of the file you wish to use
		} catch (IOException ex) {

		}
			BallDetector bd = new BallDetector(image);
			ArrayList<Ball> balls = bd.findBalls();
			for (Ball b : balls) {
				System.out.printf("Ball at %d, %d.  W: %d, H: %d\n", b.x, b.y, b.width, b.height);
				image.getGraphics().drawOval(b.x, b.y, b.width, b.height);
				
			}
		
    }

	//it displays things in the panel.
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth()/2, image.getHeight()/2, null);
    }
}
