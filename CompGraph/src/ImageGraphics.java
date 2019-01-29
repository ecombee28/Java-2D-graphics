import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * @author jim and Eric Combee
 * Date: 1-23-19
 * Description: This is a program that demonstrates 2D graphics
 *              in Java.
 * 
 */

public class ImageGraphics extends JPanel {

    // A counter that increases by one in each frame.
    private int frameNumber;
    // The time, in milliseconds, since the animation started.
    private long elapsedTimeMillis;
    // This is the measure of a pixel in the coordinate system
    // set up by calling the applyLimits method.  It can be used
    // for setting line widths, for example.
    private float pixelSize;

    static int translateX = 0;
    static int translateY = 0;
    static double rotation = 0.0;
    static double scaleX = 1.0;
    static double scaleY = 1.0;
    CreateImage myImages = new CreateImage();
    BufferedImage tImage1 = myImages.getImage(CreateImage.letterSquare);
    BufferedImage tImage2 = myImages.getImage(CreateImage.letterX);
    BufferedImage tImage3 = myImages.getImage(CreateImage.simpleLine);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
    	   	
        JFrame window;
        window = new JFrame("Java Animation");  // The parameter shows in the window title bar.
        final ImageGraphics panel = new ImageGraphics(); // The drawing area.
        window.setContentPane(panel); // Show the panel in the window.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // End program when window closes.
        window.pack();  // Set window size based on the preferred sizes of its contents.
        window.setResizable(false); // Don't let user resize window.
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation( // Center window on screen.
                (screen.width - window.getWidth()) / 2,
                (screen.height - window.getHeight()) / 2);
        Timer animationTimer;  // A Timer that will emit events to drive the animation.
        final long startTime = System.currentTimeMillis();
        // Taken from AnimationStarter
        // Modified to change timing and allow for recycling
        animationTimer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (panel.frameNumber > 5) {
                    panel.frameNumber = 0;
                } else {
                    panel.frameNumber++;
                }
                panel.elapsedTimeMillis = System.currentTimeMillis() - startTime;
                panel.repaint();
            }
        });
        window.setVisible(true); // Open the window, making it visible on the screen.
        animationTimer.start();  // Start the animation running.
    }

    public ImageGraphics() {
        // Size of Frame
        setPreferredSize(new Dimension(1000, 600));
    }

    // This is where all of the action takes place
    // Code taken from AnimationStarter.java but modified to add the specific Images
    // Also added looping structure for Different transformations
    protected void paintComponent(Graphics g) {

        /* First, create a Graphics2D drawing context for drawing on the panel.
         * (g.create() makes a copy of g, which will draw to the same place as g,
         * but changes to the returned copy will not affect the original.)
         */
        Graphics2D g2 = (Graphics2D) g.create();

       
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        g2.setPaint(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, getWidth(), getHeight()); // From the old graphics API!

        /* Here, I set up a new coordinate system on the drawing area, by calling
         * the applyLimits() method that is defined below.  Without this call, I
         * would be using regular pixel coordinates.  This function sets the value
         * of the global variable pixelSize, which I need for stroke widths in the
         * transformed coordinate system.
         */
        // Controls your zoom and area you are looking at
        applyWindowToViewportTransformation(g2, -75, 75, -75, 75, true);

        AffineTransform savedTransform = g2.getTransform();
        
        switch (frameNumber) {
            case 1: // First frame is unmodified.
                 translateX = 0;
                 translateY = 0;
                 scaleX = 1.0;
                 scaleY = 1.0;
                 rotation = 0;
                 System.out.println("Original State");
                break;
            case 2: // Second frame translates each image by (-5, 7).
                translateX = -5;
                translateY = 7;
                System.out.println("Translate (-5,7)");
                break;
            case 3: // Third frame rotates each image by 45 degrees Counter  
                rotation = 45*Math.PI / 180.0;
                System.out.println("Rotate 45 degrees counter clockwise");
                break;
            case 4: // Third frame rotates each image by 90 degrees Clockwise
                rotation = 90*Math.PI / -180.0;
                System.out.println("Rotate 90 degrees clockwise");
                break;
            case 5: // Forth frame scales img by 2 on the x axis and .5 on the y axis
            	scaleX = 2.0;
                scaleY = 0.5;
                System.out.println("Scale");
                break;
          
            default:
                break;
        } // End switch
        
        // This will draw tImage1 which is a square
        g2.translate(translateX, translateY); // Move image.
        // To offset translate again
        g2.translate(-70,30);
        g2.rotate(rotation); // Rotate image.
        g2.scale(scaleX, scaleY); // Scale image.
        g2.drawImage(tImage1, 0, 0, this); // Draw image.
        g2.setTransform(savedTransform);
        
     // This will draw tImage2 which is a X
        g2.translate(translateX, translateY); 
        g2.translate(-30,0);
        g2.rotate(rotation); 
        g2.scale(scaleX, scaleY); 
        g2.drawImage(tImage2, 0, 0, this); 
        g2.setTransform(savedTransform);
      
     // This will draw tImage3 which is a line
        g2.translate(translateX, translateY); 
        g2.translate(0,-30);
        g2.rotate(rotation); 
        g2.scale(scaleX, scaleY); 
        g2.drawImage(tImage3, 0, 0, this); 
        g2.setTransform(savedTransform);

 
      
    }

    // Method taken directly from AnimationStarter.java Code
    private void applyWindowToViewportTransformation(Graphics2D g2,
            double left, double right, double bottom, double top,
            boolean preserveAspect) {
        int width = getWidth();   // The width of this drawing area, in pixels.
        int height = getHeight(); // The height of this drawing area, in pixels.
        if (preserveAspect) {
            // Adjust the limits to match the aspect ratio of the drawing area.
            double displayAspect = Math.abs((double) height / width);
            double requestedAspect = Math.abs((bottom - top) / (right - left));
            if (displayAspect > requestedAspect) {
                // Expand the viewport vertically.
                double excess = (bottom - top) * (displayAspect / requestedAspect - 1);
                bottom += excess / 2;
                top -= excess / 2;
            } else if (displayAspect < requestedAspect) {
                // Expand the viewport vertically.
                double excess = (right - left) * (requestedAspect / displayAspect - 1);
                right += excess / 2;
                left -= excess / 2;
            }
        }
        g2.scale(width / (right - left), height / (bottom - top));
        g2.translate(-left, -top);
        double pixelWidth = Math.abs((right - left) / width);
        double pixelHeight = Math.abs((bottom - top) / height);
        pixelSize = (float) Math.max(pixelWidth, pixelHeight);
    }

}
