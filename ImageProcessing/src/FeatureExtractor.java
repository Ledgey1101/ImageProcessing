import java.awt.image.BufferedImage;
import java.awt.image.Raster;


public class FeatureExtractor 
{
	
	public static int area(BufferedImage source)
	{
		int width = source.getWidth();
		int height = source.getHeight();
		int area=0;
		
		Raster rast = source.getRaster();
		
		for (int i=0; i<height; i++)
		{
			for (int j=0; j<width; j++)
			{
				int g = rast.getSample(i,j,0);
				if (g>0)
				{
					area++;
				}
			}
		}

		return area;
	}

	public int featureExtractFromAnImage(BufferedImage source)
	{
		int area = area(source);
		

		return area;
	}

}
