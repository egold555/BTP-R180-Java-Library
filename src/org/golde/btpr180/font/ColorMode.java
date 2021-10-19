package org.golde.btpr180.font;

import org.golde.btpr180.IValue;

public enum ColorMode implements IValue {

	BLACK_ON_WHITE(0),
	WHITE_ON_BLACK(1);

	private final int value;

	private ColorMode(int value){
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}
}