package org.golde.btpr180.barcode;

import java.io.ByteArrayOutputStream;

import org.golde.btpr180.Justification;
import org.golde.btpr180.TPConstants;

public class QRCode implements IBarCode, TPConstants {

	private Justification justification = Justification.LEFT;
	private String data;
	
	public void setData(String data) {
		
		if(data.length() > 253 || data.length() < 0) {
			throw new IllegalArgumentException("Size must be greater then 0, and less then 253");
		}
		
		this.data = data;
		System.out.println(data.length());
	}
	
	public void setJustification(Justification justification) {
		this.justification = justification;
	}
	
	@Override
	public int getBarCodeType() {
		return 76;
	}
	
	@Override
	public byte[] getData() throws Exception {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();

		bytes.write(ESC);
		bytes.write('a');
		bytes.write(justification.getValue());

		int numberOfBytes = data.length() + 2;
		int pL = numberOfBytes & 0xFF;
		int pH = (numberOfBytes & 0xFF00) >> 8 ;

		bytes.write(GS);
		bytes.write('k');
		bytes.write(getBarCodeType());
		
		bytes.write(pL); // pL size of bytes
		bytes.write(pH); // pH size of bytes
		bytes.write(data.getBytes(),0,data.length());

		return bytes.toByteArray();
	}

}
