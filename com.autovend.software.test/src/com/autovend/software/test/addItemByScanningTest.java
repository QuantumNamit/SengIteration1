// Group Members
// Abrar Zawad Safwan -30150892
// Faiyaz Altaf Pranto - 30162576
// Namit Aneja -30146188
// Victor Campos
// Bheesha Kumari


package com.autovend.software.test;
import com.autovend.software.*;
import com.autovend.Barcode;
import com.autovend.BarcodedUnit;
import com.autovend.Numeral;
import com.autovend.devices.OverloadException;
import com.autovend.devices.SimulationException;
import com.autovend.products.BarcodedProduct;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class addItemByScanningTest {

    /**
     * This is the test to  check whether scan function worked successfully or not
     *
     */
    @Test
    public void scanItem_checking_successful_scan() throws Exception {

        // Initialising objects
        Barcode barcode =new Barcode(Numeral.five);
        BarcodedUnit unit=new BarcodedUnit(barcode,1.2);
        String description ="item";
        BigDecimal price =new BigDecimal(2);
        double expectedWeight =1.2;

        BarcodedProduct product =new BarcodedProduct(barcode,description,price,expectedWeight);
        addItemByScanning item=new addItemByScanning(unit, product);

        Currency currency=Currency.getInstance("USD");
        int[] deno={5,6};
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        BigDecimal[] coindeno=new BigDecimal[2];
        coindeno[0]=a;
        coindeno[1]=b;
        SystemController slf=new SystemController(currency,deno,coindeno,12,2);


        item.scanItem(unit,product,slf);
        assertEquals(slf.expectedWeightUpdate(product),slf.bagging_area_weight_change(slf,barcode,1.2),0.00001);

    }


    /**
     *
     * This is the test to check weight discrepancy
     */
    @Test( expected=Exception.class)
    public void scanItem_checking_weight_discrepancy() throws Exception {

        // Initialising objects
        Barcode barcode =new Barcode(Numeral.five);
        BarcodedUnit unit=new BarcodedUnit(barcode,1.2);
        String description ="item";
        BigDecimal price =new BigDecimal(2);
        double expectedWeight =2.0;

        BarcodedProduct product =new BarcodedProduct(barcode,description,price,expectedWeight);
        addItemByScanning item=new addItemByScanning(unit, product);

        Currency currency=Currency.getInstance("USD");
        int[] deno={5,6};
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        BigDecimal[] coindeno=new BigDecimal[2];
        coindeno[0]=a;
        coindeno[1]=b;

        SystemController slf=new SystemController(currency,deno,coindeno,3,3);

        item.scanItem(unit,product,slf);

    }

    /**
     * Test to check whether the simulation exception is thrown or not if we add a null unit
     */
    @Test(expected = SimulationException.class)
    public void scanning_null_item(){

        Barcode barcode =new Barcode(Numeral.five);
        BarcodedUnit unit=null;
        String description ="item";
        BigDecimal price =new BigDecimal(2);
        double expectedWeight =2.0;

        BarcodedProduct product =new BarcodedProduct(barcode,description,price,expectedWeight);
        addItemByScanning item=new addItemByScanning(unit, product);

    }


    /**
     * Test to check whether the simulation exception is thrown or not if we add a null barcode
     */
    @Test(expected =SimulationException.class)
    public void scanItem_if_barcode_isNull() throws Exception {

        // Initialising

        Barcode barcode =null;
        BarcodedUnit unit=new BarcodedUnit(barcode,1.2);

    }


    /**
     * Test to check whether the Overload exception is thrown or not if we add a item with weight more than the weight limit
     */
    @Test( expected= OverloadException.class)
    public void exceeding_weight_limit() throws Exception {


        Barcode barcode =new Barcode(Numeral.five);
        BarcodedUnit unit=new BarcodedUnit(barcode,3.3);
        String description ="item";
        BigDecimal price =new BigDecimal(2);
        double expectedWeight =3.4;

        BarcodedProduct product =new BarcodedProduct(barcode,description,price,expectedWeight);
        addItemByScanning item=new addItemByScanning(unit, product);

        Currency currency=Currency.getInstance("USD");
        int[] deno={5,6};
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        BigDecimal[] coindeno=new BigDecimal[2];
        coindeno[0]=a;
        coindeno[1]=b;
        SystemController slf=new SystemController(currency,deno,coindeno,2,1);


        item.scanItem(unit,product,slf);

    }

}
