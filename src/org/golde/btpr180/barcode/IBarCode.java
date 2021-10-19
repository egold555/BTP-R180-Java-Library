package org.golde.btpr180.barcode;

public interface IBarCode {

	//	int getMaxDataLength();
	public int getBarCodeType();
	public byte[] getData() throws Exception;

}
