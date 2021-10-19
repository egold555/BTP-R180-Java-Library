package org.golde.btpr180.font;

import org.golde.btpr180.IValue;

public enum Underline implements IValue {

	NONE(48),
	ONE_DOT_THICK(49),
	TWO_DOTS_THICK(50);

	private final int value;

	private Underline(int value){
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}
}
