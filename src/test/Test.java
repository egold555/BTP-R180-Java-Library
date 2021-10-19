package test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import org.golde.btpr180.CutType;
import org.golde.btpr180.Justification;
import org.golde.btpr180.TPConstants;
import org.golde.btpr180.ThermalPrinter;
import org.golde.btpr180.ThermalPrinterOutputStream;
import org.golde.btpr180.font.ColorMode;
import org.golde.btpr180.font.Font;
import org.golde.btpr180.font.FontSize;
import org.golde.btpr180.font.Style;
import org.golde.btpr180.font.Underline;
import org.golde.btpr180.image.ThermalImage;

public class Test implements TPConstants {

	public static void main(String[] args) throws Exception {

		ThermalPrinter printer = new ThermalPrinter("BTP-R180(U) 1");

		//printer.write(GS);
		//printer.write('k');
		
		//Maxicode example code, nothing can read it? Not sure where the bug lies.
		//printer.write(new byte[] {0x0C, 0x33, 0x32, 0x37, 0x38, 0x39, 0x35, 0x35, 0x35, 0x35, 0x38, 0x34, 0x30, 0x36, 0x36, 0x36, 0x54, 0x48, 0x49, 0x53, 0x20, 0x50, 0x41, 0x43, 0x4B, 0x41, 0x47, 0x45, 0x49, 0x53, 0x20, 0x47, 0x4F, 0x49, 0x4E, 0x47, 0x20, 0x54, 0x4F, 0x20, 0x44, 0x41, 0x54, 0x41, 0x4D, 0x41, 0x58, 0x43, 0x4F, 0x52, 0x50, 0x2E, 0x00});
		
		//printer.write(new byte[] {77, 10, 123, 66, 'H', 'e', 'l', 'l', 'o'});

//		CODE128 barCode = new CODE128();
//		barCode.setData("Hello World");
//		printer.write(barCode);
//		
//		printer.feed(3);
//		
//		QRCode qrCode = new QRCode();
//		qrCode.setData("Hello World, I am a QR code. I am adding more data to this QR code. Hi! 123456789");
//		qrCode.setJustification(Justification.Center);
//		printer.write(qrCode);
//		printer.feed(3);
//		printer.writeLF("I am text");
//		printer.feed(5);

		//debugAllStyles(printer);
		
		ThermalImage image = new ThermalImage(ImageIO.read(new File("res/jordan.png")));
		printer.write(image);
		printer.feed(5);
		
//		Style style = new Style()
//		.setBold(true)
//		.setColorMode(ColorMode.WhiteOnBlack)
//		.setJustification(Justification.Right)
//		.setUnderline(Underline.TwoDotThick)
//		.setFontSize(FontSize._8, FontSize._8);
//		
//		printer.write(style, "Hi");
//		printer.feed(5);
		
		printer.cut(CutType.FULL);
		
		

		printer.close();

	}

	static void debugAllStyles(ThermalPrinter printer) throws UnsupportedEncodingException, IOException {

		printer.writeLF(new Style(), "Left");
		printer.writeLF(new Style().setJustification(Justification.CENTER), "Center");
		printer.writeLF(new Style().setJustification(Justification.RIGHT), "Right");
		printer.feed(5);

		printer.writeLF(new Style().setBold(true), "Bold");
		printer.feed(5);

		printer.writeLF(new Style().setColorMode(ColorMode.BLACK_ON_WHITE), "Black on White");
		printer.writeLF(new Style().setColorMode(ColorMode.WHITE_ON_BLACK), "White on Black");
		printer.feed(5);

		printer.writeLF(new Style().setUnderline(Underline.NONE), "Zero dots thick");
		printer.writeLF(new Style().setUnderline(Underline.ONE_DOT_THICK), "One dots thick");
		printer.writeLF(new Style().setUnderline(Underline.TWO_DOTS_THICK), "Two dots thick");
		printer.feed(5);

		printer.writeLF(new Style().setFontName(Font.A), "Font A");
		printer.writeLF(new Style().setFontName(Font.B), "Font B");
		printer.writeLF(new Style().setFontName(Font.C), "Font C");
		printer.feed(5);

//		for(int width = 1; width < 9; width++) {
//			FontSize widthS = FontSize.valueOf("_" + width);
//			for(int height = 1; height < 9; height++) {
//
//				FontSize heightS = FontSize.valueOf("_" + height);
//				printer.writeLF(new Style().setFontSize(widthS, heightS), width + "x" + height);
//				printer.feed(3);
//			}
//		}



	}

}
