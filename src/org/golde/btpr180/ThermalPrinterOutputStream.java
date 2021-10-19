package org.golde.btpr180;

import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

public class ThermalPrinterOutputStream extends PipedOutputStream {

    protected final PipedInputStream pipedInputStream;
    protected final Thread threadPrint;

    public ThermalPrinterOutputStream(String name) throws IOException {
    	this(getPrintServiceByName(name));
    }
    
    public ThermalPrinterOutputStream() throws IOException {
        this(getDefaultPrintService());
    }
    
    public ThermalPrinterOutputStream(PrintService printService) throws IOException {

        UncaughtExceptionHandler uncaughtException = (Thread t, Throwable e) -> {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(),e);
        };

        pipedInputStream = new PipedInputStream();
        super.connect(pipedInputStream);

        Runnable runnablePrint = () -> {
            try {
                DocFlavor df = DocFlavor.INPUT_STREAM.AUTOSENSE;
                Doc d = new SimpleDoc(pipedInputStream, df, null);

                DocPrintJob job = printService.createPrintJob();
                job.print(d, null);
            } catch (PrintException ex) {
                throw new RuntimeException(ex);
            }
        };

        threadPrint = new Thread(runnablePrint);
        threadPrint.setUncaughtExceptionHandler(uncaughtException);
        threadPrint.start();
    }

   

    
    public void setUncaughtException(UncaughtExceptionHandler uncaughtException) {
        threadPrint.setUncaughtExceptionHandler(uncaughtException);
    }

    public static String[] getListPrintServicesNames() {
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        String[] printServicesNames = new String[printServices.length];
        for (int i = 0; i < printServices.length; i++) {
            printServicesNames[i] = printServices[i].getName();
        }
        return printServicesNames;
    }

    public static PrintService getDefaultPrintService() {
        PrintService foundService = PrintServiceLookup.lookupDefaultPrintService();
        if (foundService == null) {
            throw new IllegalArgumentException("Default Print Service is not found");
        }
        return foundService;

    }

    public static PrintService getPrintServiceByName(String printServiceName) {
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        PrintService foundService = null;

        for (PrintService service : printServices) {
            if (service.getName().compareTo(printServiceName) == 0) {
                foundService = service;
                break;
            }
        }
        if (foundService != null) {
            return foundService;
        }

        for (PrintService service : printServices) {
            if (service.getName().compareToIgnoreCase(printServiceName) == 0) {
                foundService = service;
                break;
            }
        }
        if (foundService != null) {
            return foundService;
        }

        for (PrintService service : printServices) {
            if (service.getName().toLowerCase().contains(printServiceName.toLowerCase())) {
                foundService = service;
                break;
            }
        }
        if (foundService == null) {
            throw new IllegalArgumentException("Failed to find the printer with the given name: " + printServiceName);
        }
        return foundService;
    }

}
