import java.awt.image.BufferedImage;


public class Preprocessor 

{
	
	public BufferedImage preprocessImage(BufferedImage source)
	{
		
		enhanceBrightness(source);
		
		return source;
	}

	public short [] brightnessLut (int c)
	{
		short [] lut = new short [256];
		
		for (int i = 0; i < 256; i++)
		{
			if (i< -c)
			{
				lut[i] = 0;
			}
			else if (i > 255 - c)
			{
				lut[i] = 255;
			}
			else
			{			
			lut[i] = (short) (i+c);
			}
		}
		
		return lut;
	}
	
	public BufferedImage enhanceBrightness(BufferedImage source)
	{
		
		short[] lut = brightnessLut(50);
		
		BufferedImage enhancedImage = ImageOp.pixelop(source,lut);
		
		return enhancedImage;
		
	}
	
	public static short[] linearStretchLut(float m,float c)
	{
		short [] lut = new short [256];
		
		for (int i=0; i<256; i++)
		{
			if(i < (-c/m))
			{
				lut[i]=0;
			}
			else if (i >((255-c)/m))
			{
				lut[i]=255;
			}
			else
			{
				lut[i]=(short) ((m*i)+c);
			}
		}
		

		return lut;
	}

	public static BufferedImage enhanceContrast(BufferedImage source)
	{
		short[] lut = linearStretchLut((float) 1.66, 0);
		
		BufferedImage enhancedImage = ImageOp.pixelop(source,lut);
		
		return enhancedImage;

	}
	
	public short[] powerLawLut(float gamma)
	{
		
		short [] lut = new short [256];
		
		for (int i=0; i<256; i++)
		{
			lut[i] = (short) ((Math.pow(i, gamma))/(Math.pow(i,gamma-1)));
		}
		
		return lut;
	}
	
	public BufferedImage enhanceContrastPowerLaw(BufferedImage source)
	{
		short[] lut = powerLawLut((float) 0.1);
		
		BufferedImage enhancedImage = ImageOp.pixelop(source,lut);
		
		return enhancedImage;

	}
	
	public short[] histogramEqualisationLut (Histogram hist) throws HistogramException
	{
		short [] lut = new short [256];
		
		for (int i=0; i<256; i++)
		{		
			lut[i] = (short) Math.max(0, ( (256 * hist.getCumulativeFrequency(i) ) / hist.getNumSamples() ) - 1 );
		}
		
		return lut;
	}
	
	public BufferedImage performNoiseReduction(BufferedImage source)
	{
		final float[] LOWPASS3X3 = {1/9.f,1/9.f,1/9.f,
                					1/9.f,1/9.f,1/9.f,
                					1/9.f,1/9.f,1/9.f};
		
		final float[] LOWPASS5X5 = {1/25.f,1/25.f,1/25.f,1/25.f,1/25.f,
									1/25.f,1/25.f,1/25.f,1/25.f,1/25.f,
									1/25.f,1/25.f,1/25.f,1/25.f,1/25.f,
									1/25.f,1/25.f,1/25.f,1/25.f,1/25.f,
									1/25.f,1/25.f,1/25.f,1/25.f,1/25.f};
	
		
//		BufferedImage enhancedImage = ImageOp.convolver(source, LOWPASS3X3);
		
		BufferedImage enhancedImage = ImageOp.median(source, 5);
		
		return enhancedImage;
	}

}
