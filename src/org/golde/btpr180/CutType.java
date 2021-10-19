package org.golde.btpr180;

public enum CutType implements IValue {

	FULL(48),
	PART(49);

	private final int value;

	private CutType(int value){
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

}
