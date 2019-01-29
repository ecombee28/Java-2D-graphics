
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author jim and Eric Combee
 * Date: 1-23-19
 * Description: This class is used by ImageGraphics.java. CreateImage.java creates
 *              images that will be used by ImageGraphics.java. 
 */
public class CreateImage {
	// Constants
	// X Size of Images
	private final static int IMGSIZEX = 25;
	// Y Size of Images
	private final static int IMGSIZEY = 25;

	
	public static int[][] letterSquare = new int[IMGSIZEX][IMGSIZEY];
	public static int[][] letterX = new int[IMGSIZEX][IMGSIZEY];
	public static int[][] simpleLine = new int[IMGSIZEX][IMGSIZEY];

	
     // This method creates the square img
	public void generateSquare() {

		for(int i=0;i<IMGSIZEX;i++) {
			for(int j=0;j<IMGSIZEY;j++) {
				if(i == 0 || i == (IMGSIZEX-1)) {
					letterSquare[i][j] = 1;
				}else {
					letterSquare[i][0] = 2;
					letterSquare[i][IMGSIZEY-1] = 2;
				}

			}
		}

	}
	 // This method creates the X img
	public void generateX() {
        int left =0;
        int right = IMGSIZEX-1;
		for(int i=0;i<IMGSIZEX;i++) {
			letterX[i][left] = 1;
			letterX[i][right] = 2;
			left++;
			right--;
		}

	}
	 // This method creates the line img
	public void generateLine() {

		for(int i=0;i<IMGSIZEX;i++) {
			simpleLine[i][0] = 1;
		}

	}
	
	// Methods to apply pixel colors and
	public BufferedImage getImage(int[][] data) {
		generateSquare();
		generateX();
		generateLine();
		BufferedImage image = new BufferedImage(IMGSIZEX, IMGSIZEY,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < IMGSIZEX; x++) {
			for (int y = 0; y < IMGSIZEY; y++) {
				int pixelColor = data[x][y];
				// Set Colors based on Binary Image value
				if (pixelColor == 0) {
					pixelColor = Color.LIGHT_GRAY.getRGB();
				} else if(pixelColor == 1) {
					pixelColor = Color.BLUE.getRGB();
				}else {
					pixelColor = Color.RED.getRGB();
				}
				image.setRGB(x, y, pixelColor);
			} // End for y.
		} // End for x.
		return image;
	} // End getData method.



}
