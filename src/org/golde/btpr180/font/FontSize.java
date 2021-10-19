package org.golde.btpr180.font;

import org.golde.btpr180.IValue;

public enum FontSize implements IValue {

	_1(0),
	_2(1),
	_3(2),
	_4(3),
	_5(4),
	_6(5),
	_7(6),
	_8(7);

	private final int value;

	private FontSize(int value){
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

}
