package org.golde.btpr180;

public enum Justification implements IValue {

	LEFT(48),
	CENTER(49),
	RIGHT(50);

	private final int value;

	private Justification(int value){
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}
}
