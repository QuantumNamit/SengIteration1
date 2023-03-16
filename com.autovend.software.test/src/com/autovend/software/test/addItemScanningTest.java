package com.autovend.software.test;
import com.autovend.Barcode;
import com.autovend.BarcodedUnit;
import com.autovend.Numeral;
import com.autovend.SellableUnit;
import com.autovend.devices.BarcodeScanner;
import com.autovend.devices.ElectronicScale;
import com.autovend.devices.OverloadException;
import com.autovend.devices.SimulationException;
import com.autovend.products.BarcodedProduct;
import com.autovend.software.SelfCheckoutStationController;
import com.autovend.software.addItemByScanning;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import static org.junit.Assert.*;

public class addItemScanningTest {

    @Test
    public void scanItem_checking_successful_scan() throws Exception {



        Barcode barcode =new Barcode(Numeral.five);
        BarcodedUnit unit=new BarcodedUnit(barcode,1.2);
        String description ="item";
        BigDecimal price =new BigDecimal(2);
        double expectedWeight =1.2;

        BarcodeScanner scanner =new BarcodeScanner();
        BarcodedProduct product =new BarcodedProduct(barcode,description,price,expectedWeight);
        addItemByScanning item=new addItemByScanning(unit, product);

        Currency currency=Currency.getInstance("USD");
        int[] deno={5,6};
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        BigDecimal[] coindeno=new BigDecimal[2];
        coindeno[0]=a;
        coindeno[1]=b;
        SelfCheckoutStationController slf=new SelfCheckoutStationController(currency,deno,coindeno,12,2);

        item.scanItem(scanner,unit,product,slf);

    }

    @Test( expected=Exception.class)
    public void scanItem_checking_weight_discrepancy() throws Exception {


        Barcode barcode =new Barcode(Numeral.five);
        BarcodedUnit unit=new BarcodedUnit(barcode,1.2);
        String description ="item";
        BigDecimal price =new BigDecimal(2);
        double expectedWeight =2.0;

        BarcodeScanner scanner =new BarcodeScanner();
        BarcodedProduct product =new BarcodedProduct(barcode,description,price,expectedWeight);
        addItemByScanning item=new addItemByScanning(unit, product);

        Currency currency=Currency.getInstance("USD");
        int[] deno={5,6};
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        BigDecimal[] coindeno=new BigDecimal[2];
        coindeno[0]=a;
        coindeno[1]=b;
        SelfCheckoutStationController slf=new SelfCheckoutStationController(currency,deno,coindeno,3,3);
        item.scanItem(scanner,unit,product,slf);

    }

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

    @Test( expected= OverloadException.class)
    public void exceeding_weight_limit() throws Exception {


        Barcode barcode =new Barcode(Numeral.five);
        BarcodedUnit unit=new BarcodedUnit(barcode,3.3);
        String description ="item";
        BigDecimal price =new BigDecimal(2);
        double expectedWeight =3.3;

        BarcodeScanner scanner =new BarcodeScanner();
        BarcodedProduct product =new BarcodedProduct(barcode,description,price,expectedWeight);
        addItemByScanning item=new addItemByScanning(unit, product);

        Currency currency=Currency.getInstance("USD");
        int[] deno={5,6};
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        BigDecimal[] coindeno=new BigDecimal[2];
        coindeno[0]=a;
        coindeno[1]=b;
        SelfCheckoutStationController slf=new SelfCheckoutStationController(currency,deno,coindeno,2,1);


        item.scanItem(scanner,unit,product,slf);

    }

}