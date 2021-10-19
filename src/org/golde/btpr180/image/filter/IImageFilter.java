package org.golde.btpr180.image.filter;

import java.awt.image.BufferedImage;

public interface IImageFilter {

	public int zeroOrOne(int alpha, int red, int green, int blue, int x, int y);
	
	public default int getBitonalVal(BufferedImage image,int x, int y){
        int RGBA = image.getRGB(x, y);
        int alpha = (RGBA >> 24) & 0xFF;
        int red = (RGBA >> 16) & 0xFF;
        int green = (RGBA >> 8) & 0xFF;
        int blue = RGBA & 0xFF;
        
        return zeroOrOne(alpha, red, green, blue, x, y);
        
    }
	
}
