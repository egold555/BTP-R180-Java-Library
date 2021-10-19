package org.golde.btpr180.font;

import org.golde.btpr180.IValue;

public enum Font implements IValue {

	A(48),
	B(49),
	C(50);

	private final int value;

	private Font(int value){
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

}
