package simuladorsemaforos;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

/**
*Programa que simula o tráfego de veículos por um semáforo entre duas ruas que se cruzam e tem direção única
@author Rogério de Paula Aguilar - rogeriodpaguilarbr@gmail.com
*
* os arquivos fontes estão com a codificação utf-8
* mais informações no arquivo leiame.txt
*
* Os códigos desta classe eu copiei dos seguintes endereços:
*
*http://stackoverflow.com/questions/8719473/affinetransform-truncates-image-what-do-i-wrong
*http://www.javaworld.com/community/node/7629
*
*/

public class ImagemUtil {

	/**
	 * original: http://stackoverflow.com/questions/8719473/affinetransform-truncates-image-what-do-i-wrong
	 * @param image
	 * @param quadrants
	 * @return
	 */
	
	public static BufferedImage rotacionarImagem(BufferedImage image, int quadrants) {

	    int w0 = image.getWidth();
	    int h0 = image.getHeight();
	    int w1 = w0;
	    int h1 = h0;
	    int centerX = w0 / 2;
	    int centerY = h0 / 2;

	    if (quadrants % 2 == 1) {
	        w1 = h0;
	        h1 = w0;
	    }

	    if (quadrants % 4 == 1) {
	        centerX = h0 / 2;
	        centerY = h0 / 2;
	    } else if (quadrants % 4 == 3) {
	        centerX = w0 / 2;
	        centerY = w0 / 2;
	    }

	    AffineTransform affineTransform = new AffineTransform();
	    affineTransform.setToQuadrantRotation(quadrants, centerX, centerY);
	    AffineTransformOp opRotated = new AffineTransformOp(affineTransform,
	            AffineTransformOp.TYPE_BILINEAR);
	    BufferedImage transformedImage = new BufferedImage(w1, h1,
	            image.getType());
	    transformedImage = opRotated.filter(image, transformedImage);

	    return transformedImage;

	}	

	
	   /**
	    * original: http://www.javaworld.com/community/node/7629
	    */
	   public static Image makeColorTransparent(final BufferedImage im, final Color color)
	   {
	      final ImageFilter filter = new RGBImageFilter()
	      {
	         // the color we are looking for (white)... Alpha bits are set to opaque
	         public int markerRGB = color.getRGB() | 0xFFFFFFFF;

	         public final int filterRGB(final int x, final int y, final int rgb)
	         {
	            if ((rgb | 0xFF000000) == markerRGB)
	            {
	               // Mark the alpha bits as zero - transparent
	               return 0x00FFFFFF & rgb;
	            }
	            else
	            {
	               // nothing to do
	               return rgb;
	            }
	         }
	      };

	      final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
	      return Toolkit.getDefaultToolkit().createImage(ip);
	   }	
	
}
