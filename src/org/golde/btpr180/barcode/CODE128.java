package org.golde.btpr180.barcode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.golde.btpr180.Justification;
import org.golde.btpr180.TPConstants;

//BUG: After 9 characters, code becomes unscannable. Should work though?
public class CODE128 implements IBarCode, TPConstants {

	private String data;
	private HRIPosition position = HRIPosition.NONE;
	private Justification justification = Justification.LEFT;
	//private FontName font = FontName.Font_A_Default;
	private int width = 2;
	private int height = 100;
	
	public void setData(String data) {
		this.data = data;
	}
	
	public void setPosition(HRIPosition position) {
		this.position = position;
	}
	
	public void setJustification(Justification justification) {
		this.justification = justification;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
//	public void setFont(FontName font) {
//		this.font = font;
//	}
	
	public void setWidth(int width) {
		if(width < 2 || width > 6) {
			throw new IllegalArgumentException("Width must be between 2 and 6");
		}
		this.width = width;
	}
	
	@Override
	public int getBarCodeType() {
		return 73;
	}
	
	@Override
	public byte[] getData() throws IOException {
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		
		
		bytes.write(GS);
		bytes.write('h');
		bytes.write(height);

		bytes.write(GS);
		bytes.write('w');
		bytes.write(width);

        bytes.write(GS);
        bytes.write('H');
        bytes.write(position.getValue());

//        bytes.write(GS);
//        bytes.write('f');
//        bytes.write(font.getValue());

        bytes.write(ESC);
        bytes.write('a');
        bytes.write(justification.getValue());
        
        bytes.write(GS);
        bytes.write('k');
		
		bytes.write(getBarCodeType());
		
		bytes.write(2 + data.length());
		
		bytes.write(123);
		bytes.write(66);
		
		bytes.write(data.getBytes());
		
		return bytes.toByteArray();
	}

//	@Override
//	public int getMaxDataLength() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

}
