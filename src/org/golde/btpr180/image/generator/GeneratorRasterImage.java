package org.golde.btpr180.image.generator;

import java.io.ByteArrayOutputStream;

import org.golde.btpr180.Justification;
import org.golde.btpr180.TPConstants;
import org.golde.btpr180.image.ThermalImage;

public class GeneratorRasterImage implements IImageGenerator<GeneratorRasterImage>, TPConstants {
    
    protected Justification justification;
    protected RasterBitImageMode rasterBitImageMode;


    public GeneratorRasterImage(){
        justification = Justification.LEFT;
        rasterBitImageMode = RasterBitImageMode.NORMAL;
    }
    
    /**
     * Set horizontal justification of bar-code
     * @param justification left, center or right
     * @return this object
     */
    public GeneratorRasterImage setJustification(Justification justification) {
        this.justification = justification;
        return this;
    }
    
    /**
     * Set the mode of Raster Bit Image.<p>
     * 
     * @param rasterBitImageMode mode to be used with GS v 0
     * @return this object
     * @see #getBytes(EscPosImage)
     */
    public GeneratorRasterImage setRasterBitImageMode(RasterBitImageMode rasterBitImageMode) {
        this.rasterBitImageMode = rasterBitImageMode;
        return this;
    }
    
    
    /**
     * Bit Image commands Assembly into ESC/POS bytes. <p>
     *  
     * Select justification <p>
     * ASCII ESC a n <p>
     *  
     * Print raster bit image <p>
     * ASCII GS v 0 m xL xH yL yH d1...dk <p>
     * 
     * @param image to be printed
     * @return bytes of ESC/POS
     */ 
    @Override
    public byte[] getBytes(ThermalImage image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //
        bytes.write(ESC);
        bytes.write('a');
        bytes.write(justification.getValue());
        
        //
        bytes.write(GS);
        bytes.write('v');
        bytes.write('0');
        bytes.write(rasterBitImageMode.getValue());
        //
        //  bytes in horizontal direction for the bit image
        int horizontalBytes = image.getHorizontalBytesOfRaster();
        int xL = horizontalBytes & 0xFF;
        int xH = (horizontalBytes & 0xFF00) >> 8 ;
        // 
        //  bits in vertical direction for the bit image
        int verticalBits = image.getHeightOfImageInBits();
        // getting first and second bytes separatted
        int yL = verticalBits & 0xFF;
        int yH = (verticalBits & 0xFF00) >> 8 ;
        
        bytes.write(xL);
        bytes.write(xH);
        bytes.write(yL);
        bytes.write(yH);
        // write raster bytes
        byte [] rasterBytes = image.getRasterBytes().toByteArray();
        bytes.write(rasterBytes,0,rasterBytes.length);
        

        //
        return bytes.toByteArray();
        
    }

}
