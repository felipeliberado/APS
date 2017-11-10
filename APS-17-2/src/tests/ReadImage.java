package tests;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ReadImage {
	
	public Pixel[][] pixels;
	
	
	/*public void readImage(String path) {
		BufferedImage image = ImageIO.read(new File(path));

		pixels = new Pixel[image.getHeight()][image.getWidth()];

        for(int y = 0; y< image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
            	
            	pixels[x][y].setColor(getPixelData(image, x, y));
            }
        }
	}
	
	private int[] getPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);

		int rgb[] = new int[] {
		    (argb >> 16) & 0xff, //red
		    (argb >>  8) & 0xff, //green
		    (argb      ) & 0xff  //blue
		};
		
	}*/
	
	public static void makeGray(BufferedImage img)
	{
	    for (int y = 0; y < img.getHeight(); ++y)
		for (int x = 0; x < img.getWidth(); ++x)
	    {
	        int rgb = img.getRGB(x, y);
	        
	        int r = (rgb >> 16) & 0xFF;
	        int g = (rgb >> 8) & 0xFF;
	        int b = (rgb & 0xFF);

	        int grayLevel = (r + g + b) / 3;
	        int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel; 
	        img.setRGB(x, y, gray);
	    }
	}

}
