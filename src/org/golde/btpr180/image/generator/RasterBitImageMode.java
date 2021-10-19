package org.golde.btpr180.image.generator;

import org.golde.btpr180.IValue;

public enum RasterBitImageMode implements IValue {

	NORMAL(0),  
	DOUBLE_WIDTH(1),
	DOUBLE_HEIGHT(2), 
	QUADRUPLE(3);

	private int value;

	private RasterBitImageMode(int value){
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

}
