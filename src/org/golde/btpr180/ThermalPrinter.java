package org.golde.btpr180;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.print.PrintService;

import org.golde.btpr180.barcode.IBarCode;
import org.golde.btpr180.font.Style;
import org.golde.btpr180.image.ThermalImage;
import org.golde.btpr180.image.generator.GeneratorRasterImage;
import org.golde.btpr180.image.generator.IImageGenerator;

public class ThermalPrinter implements TPConstants {

	private final OutputStream os;
	private Style style = new Style();
	private static final String charSet = "cp437";

	public ThermalPrinter(PrintService printService) throws IOException {
		this(new ThermalPrinterOutputStream(printService));
	}
	
	public ThermalPrinter(String name) throws IOException {
		this(new ThermalPrinterOutputStream(name));
	}
	
	public ThermalPrinter() throws IOException {
		this(new ThermalPrinterOutputStream());
	}
	
	public ThermalPrinter(ThermalPrinterOutputStream tpos) {
		this.os = tpos;
	}

	//	public ThermalPrinter initialize() throws IOException {
	//        write(ESC);
	//        write('@');
	//        style.reset();
	//        return this;
	//    }

	public ThermalPrinter cut(CutType type) throws IOException {
		write(GS);
		write('V');
		write(type.getValue());
		return this;
	}

	public ThermalPrinter write(IBarCode barCode) throws Exception {

		System.out.println(Arrays.toString(barCode.getData()));
		write(barCode.getData());

		write(NUL);

		return this;
	}

	public ThermalPrinter write(ThermalImage image) throws IOException {
		return write(image, new GeneratorRasterImage());
	}

	public ThermalPrinter write(ThermalImage image, IImageGenerator<?> generator) throws IOException {

		byte[] bytes = generator.getBytes(image);
		write(bytes);
		
		return this;
	}

	public ThermalPrinter write(String text) throws UnsupportedEncodingException, IOException {
		return write(style, text);
	}

	public ThermalPrinter writeLF(String text) throws UnsupportedEncodingException, IOException {
		write(style, text);
		write(LF);
		return this;
	}

	public ThermalPrinter write(Style style, String text) throws UnsupportedEncodingException, IOException {
		byte[] configBytes = style.getConfigBytes();
		write(configBytes, 0, configBytes.length);
		write(text.getBytes(charSet));
		return this;
	}

	public ThermalPrinter writeLF(Style style, String text) throws UnsupportedEncodingException, IOException {
		byte[] configBytes = style.getConfigBytes();
		write(configBytes, 0, configBytes.length);
		write(text.getBytes(charSet));
		write(LF);
		return this;
	}


	public ThermalPrinter feed(Style style, int nLines) throws IOException, IllegalArgumentException{
		if (nLines < 1 || nLines > 255) {
			throw new IllegalArgumentException("nLines must be between 1 and 255");
		}
		byte[] configBytes = style.getConfigBytes();
		write(configBytes, 0, configBytes.length);
		for(int n = 0; n < nLines; n++){
			write(LF);
		}
		return this;
	}

	public ThermalPrinter feed(int nLines) throws IOException, IllegalArgumentException {
		return feed(style, nLines);
	}

	//write custom command
	public ThermalPrinter write(int b) throws IOException {
		this.os.write(b);
		return this;
	}

	public ThermalPrinter write(byte[] b, int off, int len) throws IOException {
		this.os.write(b, off, len);
		return this;
	}

	public ThermalPrinter write(byte... b) throws IOException {
		this.os.write(b);
		return this;
	}

	public void close() throws IOException {
		this.os.close();
	}

	public void flush() throws IOException {
		this.os.flush();
	}


}
