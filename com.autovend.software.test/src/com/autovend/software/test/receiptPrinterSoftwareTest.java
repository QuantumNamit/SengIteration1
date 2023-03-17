package com.autovend.software.test;
import com.autovend.software.*;
import com.autovend.Barcode;
import com.autovend.BarcodedUnit;
import com.autovend.Numeral;
import com.autovend.devices.EmptyException;
import com.autovend.devices.OverloadException;
import com.autovend.devices.ReceiptPrinter;
import com.autovend.products.BarcodedProduct;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import static org.junit.Assert.*;

public class receiptPrinterSoftwareTest {

    @Test
    public void printReceipt() throws EmptyException, OverloadException {
        Barcode barcode = new Barcode(Numeral.five);
        String description ="Item";
        BigDecimal price = new BigDecimal(5);
        double expected_weight=3;
        BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);
        ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
        items.add(bp);
        ReceiptPrinter printer =new ReceiptPrinter();
        printer.addInk(1<<20);
        printer.addPaper(55);

        receiptPrinterSoftware rp =new receiptPrinterSoftware(items);
        receiptPrinterController controller = new receiptPrinterController();

        BarcodedUnit unit=new BarcodedUnit(barcode,1.2);
        Currency currency = Currency.getInstance("USD");
        int [] deno = {1,2};
        BigDecimal[] coindeno=new BigDecimal[2];
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        coindeno[0]=a;

        SelfCheckoutStationController slf=new SelfCheckoutStationController(currency,deno,coindeno,3,3);


        AttendantObserver observer = new AttendantObserver() {
            @Override
            public void notifyAttendant(ReceiptPrinter printer,receiptPrinterController controller, AttendantObserver observer, int paperRemaining, int inkRemaining) throws OverloadException {

            }

            @Override
            public void putPaper(ReceiptPrinter printer) throws OverloadException {

            }

            @Override
            public void putInk(ReceiptPrinter printer) throws OverloadException {

            }
        } ;

        rp.printReceipt(slf ,printer,controller ,observer,true,55,1<<20);

    }

    @Test
    public void out_of_paper() throws EmptyException, OverloadException {
        Barcode barcode = new Barcode(Numeral.five);
        String description ="Item";
        BigDecimal price = new BigDecimal(5);
        double expected_weight=3;
        BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);
        ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
        items.add(bp);
        ReceiptPrinter printer =new ReceiptPrinter();
        printer.addPaper(55);
        printer.addInk(1<<20);

        receiptPrinterSoftware rp =new receiptPrinterSoftware(items);
        receiptPrinterController controller = new receiptPrinterController();



        Currency currency = Currency.getInstance("USD");
        int [] deno = {1,2};
        BigDecimal[] coindeno=new BigDecimal[2];
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        coindeno[0]=a;
        coindeno[1]=b;

        SelfCheckoutStationController slf=new SelfCheckoutStationController(currency,deno,coindeno,3,3);

        AttendantObserver observer = new AttendantObserver() {
            @Override
            public void notifyAttendant(ReceiptPrinter printer,receiptPrinterController controller, AttendantObserver observer, int paperRemaining, int inkRemaining) throws OverloadException {

            }

            @Override
            public void putPaper(ReceiptPrinter printer) throws OverloadException {

            }

            @Override
            public void putInk(ReceiptPrinter printer) throws OverloadException {

            }
        };
        rp.printReceipt(slf,printer,controller,observer,true,0,1);

    }

    @Test
    public void out_of_ink() throws EmptyException, OverloadException {
        Barcode barcode = new Barcode(Numeral.five);
        String description ="Item";
        BigDecimal price = new BigDecimal(5);
        double expected_weight=3;
        BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);
        ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
        items.add(bp);
        ReceiptPrinter printer =new ReceiptPrinter();
        printer.addPaper(55);
        printer.addInk(1<<20);

        receiptPrinterSoftware rp =new receiptPrinterSoftware(items);
        receiptPrinterController controller = new receiptPrinterController();


        Currency currency = Currency.getInstance("USD");
        int [] deno = {1,2};
        BigDecimal[] coindeno=new BigDecimal[2];
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        coindeno[0]=a;
        coindeno[1]=b;

        SelfCheckoutStationController slf=new SelfCheckoutStationController(currency,deno,coindeno,3,3);

        AttendantObserver observer = new AttendantObserver() {
            @Override
            public void notifyAttendant(ReceiptPrinter printer,receiptPrinterController controller, AttendantObserver observer, int paperRemaining, int inkRemaining) throws OverloadException {

            }

            @Override
            public void putPaper(ReceiptPrinter printer) throws OverloadException {

            }

            @Override
            public void putInk(ReceiptPrinter printer) throws OverloadException {

            }
        };
        rp.printReceipt(slf,printer,controller,observer,true,1,0);

    }
    @Test
    public void notifyAttendant_out_of_paper() throws OverloadException {

        Barcode barcode = new Barcode(Numeral.five);
        String description ="Item";
        BigDecimal price = new BigDecimal(5);
        double expected_weight=3;
        BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);
        ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
        items.add(bp);
        ReceiptPrinter printer =new ReceiptPrinter();
        printer.addPaper(55);
        printer.addInk(1<<20);

        receiptPrinterSoftware rp =new receiptPrinterSoftware(items);
        receiptPrinterController controller = new receiptPrinterController();

        AttendantObserver observer =new AttendantObserver() {
            @Override
            public void notifyAttendant(ReceiptPrinter printer,receiptPrinterController controller, AttendantObserver observer, int paperRemaining, int inkRemaining) throws OverloadException {

            }

            @Override
            public void putPaper(ReceiptPrinter printer) throws OverloadException {

            }

            @Override
            public void putInk(ReceiptPrinter printer) throws OverloadException {

            }
        };

        rp.notifyAttendant(printer,controller,observer,0,0);

    }
    @Test
    public void notifyAttendant_out_of_ink() throws OverloadException {

        Barcode barcode = new Barcode(Numeral.five);
        String description ="Item";
        BigDecimal price = new BigDecimal(5);
        double expected_weight=3;
        BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);
        ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
        items.add(bp);
        ReceiptPrinter printer =new ReceiptPrinter();
        printer.addPaper(55);
        printer.addInk(1<<20);

        receiptPrinterSoftware rp =new receiptPrinterSoftware(items);
        receiptPrinterController controller = new receiptPrinterController();

        AttendantObserver observer =new AttendantObserver() {
            @Override
            public void notifyAttendant(ReceiptPrinter printer,receiptPrinterController controller, AttendantObserver observer, int paperRemaining, int inkRemaining) throws OverloadException {

            }

            @Override
            public void putPaper(ReceiptPrinter printer) throws OverloadException {

            }

            @Override
            public void putInk(ReceiptPrinter printer) throws OverloadException {

            }
        };

        rp.notifyAttendant(printer,controller,observer,1,0);

    }

    @Test
    public void putPaper() throws OverloadException {

        Barcode barcode = new Barcode(Numeral.five);
        String description ="Item";
        BigDecimal price = new BigDecimal(5);
        double expected_weight=3;
        ReceiptPrinter printer=new ReceiptPrinter();
        printer.addPaper(10);
        BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);
        ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
        items.add(bp);
        receiptPrinterSoftware rp =new receiptPrinterSoftware(items);
        rp.putPaper(printer);


    }

    @Test
    public void putInk() throws OverloadException {

        Barcode barcode = new Barcode(Numeral.five);
        String description ="Item";
        BigDecimal price = new BigDecimal(5);
        double expected_weight=3;
        ReceiptPrinter printer=new ReceiptPrinter();
        printer.addPaper(10);
        BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);
        ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
        items.add(bp);
        receiptPrinterSoftware rp =new receiptPrinterSoftware(items);
        rp.putInk(printer);


    }

}