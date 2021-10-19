package org.golde.btpr180.barcode;

import org.golde.btpr180.IValue;

public enum HRIPosition implements IValue {

	 /**
     * Do not Print the text
     */
    NONE(48),  
    /** 
     * Print the text above the bar-code
     */
    ABOVE(49),
    /** 
     * Print the text below the bar-code
     */
    BELOW(50), 
    /** 
     * Print the text above and below the bar-code
     */
    ABOVE_AND_BELOW(51);
	
    private final int value;
    
    private HRIPosition(int value){
        this.value = value;
    }
    
    @Override
    public int getValue() {
		return value;
	}
	
}
