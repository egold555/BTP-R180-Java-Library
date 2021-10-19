package org.golde.btpr180.image.filter;

import static java.lang.Math.round;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FilterDithered implements IImageFilter {
    protected int[][] ditherMatrix;
    protected final int matrixWidth;
    protected final int matrixHeight;

    
    /**
     * Creates a new BitonalOrderedDither with <code>ditherMatrix[matrixWidth][matrixHeight]</code> filled by zeros.<p>
     * 
     * @param matrixWidth width of <code>ditherMatrix</code> must be &gt; 0
     * @param matrixHeight height of <code>ditherMatrix</code> must be &gt; 0
     */
    public FilterDithered(int matrixWidth, int matrixHeight) {
        if(matrixWidth < 1 || matrixHeight < 1){
            throw new IllegalArgumentException("matrixWidth and matrixHeight must be > 0");
        }

        this.matrixWidth = matrixWidth;
        this.matrixHeight = matrixHeight;
        ditherMatrix = new int[this.matrixWidth][this.matrixHeight];
    }

    /**
     * Creates a new BitonalOrderedDither with <code>ditherMatrix[matrixWidth][matrixHeight]</code> <p>
     * automatically filled with values between <code>threshouldMin</code> and <code>threshouldMax</code>.<p>
     * 
     * @param matrixWidth width of <code>ditherMatrix</code> must be &gt; 0
     * @param matrixHeight height of <code>ditherMatrix</code> must be &gt; 0
     * @param thresholdMin min threshold must be between 0 and 255. 0 is lighter and 255 is darker.
     * @param thresholdMax max threshold must be between 0 and 255. 0 is lighter and 255 is darker.
     */
    public FilterDithered(int matrixWidth, int matrixHeight, int thresholdMin, int thresholdMax){
        this(matrixWidth, matrixHeight);

        if(thresholdMin < 0 || thresholdMin > 255){
            throw new IllegalArgumentException("values of threshould must be between 0 and 255");
        }
        if(thresholdMax < 0 || thresholdMax > 255){
            throw new IllegalArgumentException("values of threshould must be between 0 and 255");
        }
        if(thresholdMax < thresholdMin){
            throw new IllegalArgumentException("thresholdMax must be >= thresholdMin");
        }
        float matrixSize = (float)(matrixWidth * matrixHeight);
        float thresholdUtil = (float)(thresholdMax - thresholdMin);
        float valueToBeAddedOnEachPosition = (float)thresholdUtil / (matrixSize-1f);
        float positionValue = (float)thresholdMin;

        Random randomCoordinates = new Random(1);
        int shuffledX[] = shuffle(matrixWidth, randomCoordinates);
        int shuffledY[] = shuffle(matrixHeight, randomCoordinates);

        for(int x = 0; x < matrixWidth; x++){
            for(int y = 0; y < matrixHeight; y++){
                ditherMatrix[shuffledX[x]][shuffledY[y]] = round(positionValue);
                positionValue+=valueToBeAddedOnEachPosition;
            }
        }
    }
    private int[] shuffle(int size,Random random){
        Set<Integer> set = new HashSet<>();
        int intArray[] = new int[size];
        int i = 0;
        while(set.size() < size){
            int val = random.nextInt(size);
            if(set.contains(val)){
                continue;
            }
            set.add(val);
            intArray[i++] = val;
        }

        return intArray;
    }
    /**
     * Creates a new BitonalOrderedDither with default values.
     */
    public FilterDithered(){
        this(2,2,64,127);
    }
    
    /**
     * Set ditherMatrix value.
     * You can assemble special matrix, like watermark pattern.It's up to you.
     * @param ditherMatrix matrix filled by the user. Should be <code>new int[matrixWidth][matrixHeight]</code>
     */
    public void setDitherMatrix(int[][] ditherMatrix){
        this.ditherMatrix = ditherMatrix;        
    }

    /**
     * translate RGBA colors to 0 or 1 (print or not). <p>
     * the return is based on ditherMatrix values.
     * @param alpha range from 0 to 255
     * @param red range from 0 to 255
     * @param green range from 0 to 255
     * @param blue range from 0 to 255
     * @param x the X coordinate of the image 
     * @param y the Y coordinate of the image 
     * @return  0 or 1     
     */
    @Override
    public int zeroOrOne(int alpha, int red, int green, int blue, int x, int y) {
        int luminance = 0xFF;
        if (alpha > 127) {
            luminance = (red + green + blue) / 3;
        }
        int threshold = ditherMatrix[x % matrixWidth][y % matrixHeight];
        return (luminance < threshold) ? 1 : 0;
    }
}
