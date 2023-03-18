package com.autovend.software.test;
import com.autovend.software.*;

import com.autovend.Barcode;
import com.autovend.BarcodedUnit;
import com.autovend.Bill;
import com.autovend.Numeral;
import com.autovend.devices.OverloadException;
import com.autovend.products.BarcodedProduct;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import static org.junit.Assert.*;

public class PayWithCashTest {

    @Test
    public void cash_Algorithm() throws OverloadException {

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
        BigDecimal cashInserted =BigDecimal.valueOf(50);
        obj.Cash_Algorithm(controller,bp,bill,false,cashInserted);
    }
}
