import java.awt.image.BufferedImage;


public class ImageProcessing 
{

	public static void main(String[] args) 
	{

		learnCar();
		
	}
	
	public static void learnCar()
	{
	    BufferedImage original = readInImage("training/car/car1-066-153.png");
		
        JVision jvis = new JVision();
        
   
        
        Segmentor segmentor = new Segmentor();
        BufferedImage segmentedImage = segmentor.segmentImage(original);
        

        displayAnImage(original, jvis, 1,1, "Car1");
        displayAnImage(segmentedImage, jvis, 301,1, "Car1 segmented");

		
	}
	
	
//	public void learnCow()
//	{
//		
//	}
//	
//	public void learnPear()
//	{
//		
//	}
//	
//	public void learnTomato()
//	{
//		
//	}

	public static BufferedImage readInImage(String filename)
	{
	    BufferedImage img;

	    img = ImageOp.readInImage(filename);
	    return img;
	}
	
	public static void displayAnImage(BufferedImage img, JVision display, int x, int y, String title)
	{
	    display.imdisp(img,title,x,y);
	}

	public void createAndDisplayHistogram(BufferedImage img,JVision display,int x,int y,String title) throws Exception
	{
	    Histogram hist = new Histogram(img);
	    GraphPlot gp = new GraphPlot(hist);
	    display.imdisp(gp,title,x,y);
	}
	
	public void displayFourImages(BufferedImage[] img,int a,int b,int c,int d,JVision display,String title)
	{
		displayAnImage(img[a], display, 1,1, "first image");
		displayAnImage(img[b], display, 301,1, "second image");
		displayAnImage(img[c], display, 1,301, "third image");
		displayAnImage(img[d], display, 301,301, "fourth image");
	}
	
	
}
