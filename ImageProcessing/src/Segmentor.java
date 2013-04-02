import java.awt.image.BufferedImage;
import java.awt.image.Raster;


public class Segmentor 
{
	
	public BufferedImage segmentImage(BufferedImage source)
	{
		BufferedImage result;
//		result = performEdgeExtraction(source);
		result = performAutomaticThresholding(source);
		result = postprocessAnImage(result);
		
		
		return result;
	}
	
	public static BufferedImage performEdgeExtraction(BufferedImage source)
	{
		final float[] HIGHPASS1X2 = {-10.f,10.f,
       	       						0.f,0.f};
		
		final float[] HIGHPASS2X1 = {-10.f,0.f,
									10.f,0.f};
		
		
		BufferedImage source1 = ImageOp.convolver(source, HIGHPASS1X2);
		BufferedImage source2 = ImageOp.convolver(source, HIGHPASS2X1);
		
		BufferedImage result = 	ImageOp.imagrad(source1, source2);
		
		return result;
	}
	
	public static short[] thresholdLut(int t)
	{
		short [] lut = new short [256];
		
		for (int i=0; i<256; i++)
		{
			if (i <= t)
			{
				lut[i]=255;
			}
			else 
			{
				lut[i]=0;
			}
		}
		
		return lut;
	}
	
	public static BufferedImage thresholdAnImage(BufferedImage source, int t)
	{
		short [] lut = thresholdLut(t);
		
		BufferedImage binaryImage = ImageOp.pixelop(source,lut);
		
		return binaryImage;
	}
	
	public static int mean(BufferedImage source)
	{
		int width = source.getWidth();
		int height = source.getHeight();
		
		int pixelTotal = width*height;
		int totalGrayLevel=0;
		
		
		Raster rast = source.getRaster();
		
		for (int i=0; i<height; i++)
		{
			for (int j=0; j<width; j++)
			{
				int g = rast.getSample(i,j,0);
				totalGrayLevel = totalGrayLevel + g;
			}
		}
		
		int mean = totalGrayLevel/pixelTotal;

		return mean;
	}
	
	public static int standardDeviation(BufferedImage source)
	{
		int width = source.getWidth();
		int height = source.getHeight();
		int pixelTotal = width*height;
		
		int deviationTotal=0;
		
		int mean = mean(source);
		Raster rast = source.getRaster();
		
		for (int i=0; i<height; i++)
		{
			for (int j=0; j<width; j++)
			{
				int g = rast.getSample(i,j,0);
				int gDev  = g - mean;
				int gSq = (int) Math.pow(gDev, 2);
				
				deviationTotal += gSq;
	
	
			}
		}
		
		deviationTotal = deviationTotal/(pixelTotal);
		
		int standardDeviation = (int) Math.sqrt(deviationTotal);
		
		return standardDeviation;
		
	}
	
	public static BufferedImage performAutomaticThresholding(BufferedImage source)
	{
		int mean = mean(source);
		int standardDeviation = standardDeviation(source);
		double a = -0.5;
		
		int threshold = (int) (mean - a*standardDeviation);
		
		BufferedImage thresholdedImage = thresholdAnImage(source, threshold);
		
		return thresholdedImage;
	}
	

	public static BufferedImage postprocessAnImage(BufferedImage source)
	{
		int m = 10;
		
		BufferedImage openedImage = ImageOp.open(source,m);
		BufferedImage closedImage = ImageOp.close(openedImage,m);
		
		return closedImage;
	}


}
