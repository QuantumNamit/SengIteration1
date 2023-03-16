package com.autovend.software.test;
import org.junit.Test;

import com.autovend.software.SelfCheckoutStationController;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class SelfCheckoutStationControllerTest {


    @Test
    public void disable() {
        Currency currency=Currency.getInstance("USD");
        int[] deno={5,6};
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        BigDecimal[] coindeno=new BigDecimal[2];
        coindeno[0]=a;
        coindeno[1]=b;


        SelfCheckoutStationController  controller=new SelfCheckoutStationController(currency, deno,coindeno,2,3);
        controller.baggingArea.disable();
        controller.billStorage.disable();
        controller.scale.disable();
        controller.handheldScanner.disable();
        controller.mainScanner.disable();
        controller.billInput.disable();
        controller.billOutput.disable();
        controller.printer.disable();

        controller.disable(controller);
    }

    @Test
    public void enable() {

        Currency currency=Currency.getInstance("USD");
        int[] deno={5,6};
        BigDecimal a =new BigDecimal(1);
        BigDecimal b =new BigDecimal(2);
        BigDecimal[] coindeno=new BigDecimal[2];
        coindeno[0]=a;
        coindeno[1]=b;


        SelfCheckoutStationController  controller=new SelfCheckoutStationController(currency, deno,coindeno,2,3);
        controller.baggingArea.enable();
        controller.billStorage.enable();
        controller.scale.enable();
        controller.handheldScanner.enable();
        controller.mainScanner.enable();
        controller.billInput.enable();
        controller.billOutput.enable();
        controller.printer.enable();

        controller.enable(controller);
    }
}