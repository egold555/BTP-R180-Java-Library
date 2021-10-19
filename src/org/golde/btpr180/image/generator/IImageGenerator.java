package org.golde.btpr180.image.generator;

import org.golde.btpr180.Justification;
import org.golde.btpr180.image.ThermalImage;

public interface IImageGenerator<T> {

	public byte[] getBytes(ThermalImage image);
	public T setJustification(Justification justification);
	
}
