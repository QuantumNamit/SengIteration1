package com.autovend.software;
import com.autovend.Barcode;
import com.autovend.BarcodedUnit;
import com.autovend.SellableUnit;
import com.autovend.devices.BarcodeScanner;
import com.autovend.devices.BillSlot;
import com.autovend.devices.ElectronicScale;
import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SimulationException;
import com.autovend.products.BarcodedProduct;

import java.math.BigDecimal;


public class addItemByScanning  extends addItem implements CustomerObserver {


    private SelfCheckoutStationController slf;
    private Barcode barcode;
    private String description;
    private double weight;
    private BigDecimal cost;
    BarcodedUnit item;

    public addItemByScanning(BarcodedUnit item, BarcodedProduct product){

        // all the general information about the item

        super(item, product);

        // information about the item if adding the item by scanning the barcode

            this.barcode=product.getBarcode();
            this.description= product.getDescription();

    }


   public void scanItem(BarcodeScanner barcodeScanner, BarcodedUnit item, BarcodedProduct product, SelfCheckoutStationController slf) throws Exception {

       // laser scanner scans the items and if successful it will notify all the Barcode Scanner observers
       if(barcodeScanner.scan(item)){

            //  should block out the self checkout station form further customer interaction
           block_selfcheckout(slf);

           weight= item.getWeight();
           cost=product.getPrice();

           expectedWeightUpdate(slf.baggingArea,product);

           // notify customer to place the item

           notifyCustomer();

       //    bagging_area_weight_change(scale,product.getBarcode(),itemWeight);
           if(expectedWeightUpdate(slf.baggingArea,product)!= bagging_area_weight_change(slf.baggingArea,product.getBarcode(), item.getWeight())){
              throw new Exception("Weight Discrepancy Occurred");
           }

           // unblock selfcheckout station
           unblock_selfcheckout(slf);
          
       }

  }

    @Override
    public void notifyCustomer() {
        System.out.println("Enter the item to the Bagging Area ");
    }

    @Override
    public void notifyCustomerSessionComplete() {

    }
}
