// Group Members
// Abrar Zawad Safwan -30150892
// Faiyaz Altaf Pranto - 30162576
// Namit Aneja -30146188

package com.autovend.software.test;
import com.autovend.software.*;
import com.autovend.Barcode;
import com.autovend.BarcodedUnit;
import com.autovend.Numeral;
import com.autovend.devices.EmptyException;
import com.autovend.devices.OverloadException;
import com.autovend.products.BarcodedProduct;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import static org.junit.Assert.*;

/**
 * Test File for receiptPrinterSoftware
 */

public class receiptPrinterSoftwareTest {


    /**
     *
     * Test case to check the successful execution of the printReceipt function
     * and when the customer paid the full price
     */

    @Test
    public void printReceipt() throws EmptyException, OverloadException {
        Barcode barcode = new Barcode(Numeral.five);
        String description ="Item";
        BigDecimal price = new BigDecimal(5);
        double expected_weight=3;
        BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);

        ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
        items.add(bp);


        receiptPrinterSoftware rp =new receiptPrinterSoftware(items);


        BarcodedUnit unit=new BarcodedUnit(barcode,1.2);
        Currency currency = Currency.getInstance("USD");
        int [] deno = {1,2};
        BigDecimal[] coindeno=new BigDecimal[2];
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        coindeno[0]=a;
        coindeno[1]=b;

        SystemController controller=new SystemController(currency,deno,coindeno,3,3);
        controller.addInk(controller,1<<20);
        controller.addPaper(controller,22);

        rp.printReceipt(controller,true,55,1<<20);
        

    }

    /**
     *
     * Test case to check the print receipt function if the customer did not paid the full price
     */

        @Test
        public void  no_receipt_printed_not_paid_in_full () throws EmptyException, OverloadException {
            Barcode barcode = new Barcode(Numeral.five);
            String description ="Item";
            BigDecimal price = new BigDecimal(5);
            double expected_weight=3;
            BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);
            ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
            items.add(bp);
            receiptPrinterSoftware rp =new receiptPrinterSoftware(items);


            BarcodedUnit unit=new BarcodedUnit(barcode,1.2);
            Currency currency = Currency.getInstance("USD");
            int [] deno = {1,2};
            BigDecimal[] coindeno=new BigDecimal[2];
            BigDecimal a =new BigDecimal(1);
            BigDecimal b =new BigDecimal(2);
            coindeno[0]=a;
            coindeno[1]=b;
            SystemController controller=new SystemController(currency,deno,coindeno,3,3);
            controller.addInk(controller,1<<20);
            controller.addPaper(controller,22);
            rp.printReceipt(controller,false,55,1<<20);
    }


    /**
     *
     * Test case to check how the print receipt function works if we are out of paper
     */
    @Test
    public void out_of_paper() throws EmptyException, OverloadException {
        Barcode barcode = new Barcode(Numeral.five);
        String description ="Item";
        BigDecimal price = new BigDecimal(5);
        double expected_weight=3;
        BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);
        ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
        items.add(bp);
        receiptPrinterSoftware rp =new receiptPrinterSoftware(items);


        Currency currency = Currency.getInstance("USD");
        int [] deno = {1,2};
        BigDecimal[] coindeno=new BigDecimal[2];
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        coindeno[0]=a;
        coindeno[1]=b;

        SystemController controller=new SystemController(currency,deno,coindeno,3,3);
        controller.addInk(controller,1<<20);
        controller.addPaper(controller,22);

        rp.printReceipt(controller,true,0,1);
    }

    /**
     *
     * Test case to check how the print receipt function works if we are out of ink
     */
    @Test
    public void out_of_ink() throws EmptyException, OverloadException {
        Barcode barcode = new Barcode(Numeral.five);
        String description ="Item";
        BigDecimal price = new BigDecimal(5);
        double expected_weight=3;
        BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);
        ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
        items.add(bp);

        receiptPrinterSoftware rp =new receiptPrinterSoftware(items);

        Currency currency = Currency.getInstance("USD");
        int [] deno = {1,2};
        BigDecimal[] coindeno=new BigDecimal[2];
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        coindeno[0]=a;
        coindeno[1]=b;
        SystemController controller=new SystemController(currency,deno,coindeno,3,3);
        controller.addInk(controller,1<<20);
        controller.addPaper(controller,22);
        rp.printReceipt(controller,true,1,0);

    }

    /**
     *
     * Test case to check how the print receipt function works if we are out of ink as well as out of paper
     */

    @Test
    public void out_of_ink_and_out_of_paper() throws EmptyException, OverloadException {
        Barcode barcode = new Barcode(Numeral.five);
        String description ="Item";
        BigDecimal price = new BigDecimal(5);
        double expected_weight=3;
        BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);
        ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
        items.add(bp);


        receiptPrinterSoftware rp =new receiptPrinterSoftware(items);
        Currency currency = Currency.getInstance("USD");
        int [] deno = {1,2};
        BigDecimal[] coindeno=new BigDecimal[2];
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        coindeno[0]=a;
        coindeno[1]=b;
        SystemController controller=new SystemController(currency,deno,coindeno,3,3);
        controller.addInk(controller,1<<20);
        controller.addPaper(controller,22);
        rp.printReceipt(controller,true,0,0);
    }

    /**
     *
     * Test case to check whether the attendant has been notified or not when the printer got out of paper
     */
    @Test
    public void notifyAttendant_out_of_paper() throws OverloadException, EmptyException {

        Barcode barcode = new Barcode(Numeral.five);
        String description ="Item";
        BigDecimal price = new BigDecimal(5);
        double expected_weight=3;
        BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);
        ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
        items.add(bp);
        receiptPrinterSoftware rp =new receiptPrinterSoftware(items);


        Currency currency = Currency.getInstance("USD");
        int [] deno = {1,2};
        BigDecimal[] coindeno=new BigDecimal[2];
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        coindeno[0]=a;
        coindeno[1]=b;

        SystemController controller=new SystemController(currency,deno,coindeno,3,3);
        controller.addInk(controller,1<<20);
        controller.addPaper(controller,22);
        rp.printReceipt(controller,true,1,0);

    }

    /**
     *
     * Test case to check whether the attendant has been notified or not when the printer got out of  ink
     */

    @Test
    public void notifyAttendant_out_of_ink() throws OverloadException, EmptyException {
        Barcode barcode = new Barcode(Numeral.five);
        String description ="Item";
        BigDecimal price = new BigDecimal(5);
        double expected_weight=3;
        BarcodedProduct bp = new BarcodedProduct(barcode,description,price,expected_weight);
        ArrayList<BarcodedProduct> items=new ArrayList<BarcodedProduct>();
        items.add(bp);
        receiptPrinterSoftware rp =new receiptPrinterSoftware(items);

        Currency currency = Currency.getInstance("USD");
        int [] deno = {1,2};
        BigDecimal[] coindeno=new BigDecimal[2];
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        coindeno[0]=a;
        coindeno[1]=b;
        SystemController controller=new SystemController(currency,deno,coindeno,3,3);
        controller.addInk(controller,1<<20);
        controller.addPaper(controller,22);
        rp.printReceipt(controller,true,1,0);
    }
}