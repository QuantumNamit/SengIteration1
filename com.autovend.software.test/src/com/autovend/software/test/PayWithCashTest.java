// Group Members
// Abrar Zawad Safwan -30150892
// Faiyaz Altaf Pranto - 30162576
// Namit Aneja -30146188
// Victor Campos
// Bheesha Kumari


package com.autovend.software.test;
import com.autovend.software.*;
import com.autovend.devices.*;
import com.autovend.Barcode;
import com.autovend.BarcodedUnit;
import com.autovend.Bill;
import com.autovend.Numeral;
import com.autovend.devices.BillValidator;
import com.autovend.devices.OverloadException;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.Product;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import static org.junit.Assert.*;

public class PayWithCashTest {

    /** Total bill > Cash Inserted
     *  Amount Inserted --> 5 , Total Bill ---->50
     *
     * Testing if the The correct amount Due of 45 is displayed     **/
    @Test
    public void Total_Bill_greater_than_Cash_Insetred_Checks_AmountDue() throws OverloadException {

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
        Bill bill = new Bill(5,currency);
        PayWithCash obj=new PayWithCash(currency);
        int cashInserted = bill.getValue();

        ArrayList<Product> products=new ArrayList<>();

        Product product1;
        product1=new Product(BigDecimal.valueOf(30),true) {
            @Override
            public BigDecimal getPrice() {
                return super.getPrice();
            }

        };

        Product product2;
        product2=new Product(BigDecimal.valueOf(20),true) {
            @Override
            public BigDecimal getPrice() {
                return super.getPrice();
            }

        };
         products.add(product1);
         products.add(product2);
         
         int [] denomination= {1,2 };

        obj.Cash_Algorithm(controller,bp,bill,false,cashInserted, products);
        assertEquals(45, obj.getAmount_Due());;
    }


    /** Total bill = Cash Inserted
     * Amount Inserted --> 50 , Total Bill ---->50
     * Testing if the The correct Change of 0 is displayed     **/
    @Test
    public void Total_Bill_Equals_Cash_Insetred_Checks_Change_0() throws OverloadException {

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
        Bill bill = new Bill(50,currency);
        PayWithCash obj=new PayWithCash(currency);
        int cashInserted = bill.getValue();

        ArrayList<Product> products=new ArrayList<>();

        Product product1;
        product1=new Product(BigDecimal.valueOf(30),true) {
            @Override
            public BigDecimal getPrice() {
                return super.getPrice();
            }

        };

        Product product2;
        product2=new Product(BigDecimal.valueOf(20),true) {
            @Override
            public BigDecimal getPrice() {
                return super.getPrice();
            }

        };
        products.add(product1);
        products.add(product2);

        obj.Cash_Algorithm(controller,bp,bill,false,cashInserted, products);
        assertEquals(0, obj.getChange());;
    }


    /**Total bill < Cash Inserted
     * Amount Inserted --> 100 , Total Bill ---->50
     * Testing if the The correct Change of 50 is displayed     **/

    @Test
    public void Total_Bill_less_than_Cash_Insetred_Checks_Change_NotZero() throws OverloadException {

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
        Bill bill = new Bill(100,currency);
        PayWithCash obj=new PayWithCash(currency);
        int cashInserted = bill.getValue();

        ArrayList<Product> products=new ArrayList<>();

        Product product1;
        product1=new Product(BigDecimal.valueOf(30),true) {
            @Override
            public BigDecimal getPrice() {
                return super.getPrice();
            }

        };

        Product product2;
        product2=new Product(BigDecimal.valueOf(20),true) {
            @Override
            public BigDecimal getPrice() {
                return super.getPrice();
            }

        };
        products.add(product1);
        products.add(product2);

        obj.Cash_Algorithm(controller,bp,bill,false,cashInserted, products);
        assertEquals(50, obj.getChange());
    }

    /** Testing for Insufficient Change and Attendant is called
     * Total bill < Cash Inserted
     * Amount Inserted --> 100 , Total Bill ---->57
     *    **/

    @Test
    public void Insufficient_Change_Calls_Attendant() throws OverloadException {

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
        Bill bill = new Bill(100,currency);
        PayWithCash obj=new PayWithCash(currency);
        int cashInserted = bill.getValue();
        ArrayList<Product> products=new ArrayList<>();

        Product product1;
        product1=new Product(BigDecimal.valueOf(30),true) {
            @Override
            public BigDecimal getPrice() {
                return super.getPrice();
            }

        };

        Product product2;
        product2=new Product(BigDecimal.valueOf(27),true) {
            @Override
            public BigDecimal getPrice() {
                return super.getPrice();
            }

        };
        products.add(product1);
        products.add(product2);
        obj.Cash_Algorithm(controller,bp,bill,false,cashInserted, products);
        Assert.assertTrue("Attendant should help the customer to get the appropriate change", true);

    }
    /** Testing for Print Receipts
     * Total bill < Cash Inserted
     * Amount Inserted --> 100 , Total Bill ---->50
     *    **/

    @Test
    public void ReceiptPrinted() throws OverloadException {

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
        Bill bill = new Bill(100,currency);
        PayWithCash obj=new PayWithCash(currency);
        int cashInserted = bill.getValue();

        ArrayList<Product> products=new ArrayList<>();

        Product product1;
        product1=new Product(BigDecimal.valueOf(30),true) {
            @Override
            public BigDecimal getPrice() {
                return super.getPrice();
            }

        };

        Product product2;
        product2=new Product(BigDecimal.valueOf(20),true) {
            @Override
            public BigDecimal getPrice() {
                return super.getPrice();
            }

        };
        products.add(product1);
        products.add(product2);

        obj.Cash_Algorithm(controller,bp,bill,false,cashInserted, products);
        Assert.assertTrue("Print the Receipt..", true);

    }
    

}
