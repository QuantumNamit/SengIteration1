package com.autovend.software;
import com.autovend.Barcode;
import com.autovend.BarcodedUnit;
import com.autovend.SellableUnit;
import com.autovend.devices.ElectronicScale;
import com.autovend.devices.OverloadException;
import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SimulationException;
import com.autovend.products.BarcodedProduct;

import java.math.BigDecimal;

public class addItem {

    // information about the item
    private BarcodedUnit item;
    private double weight;
    private BigDecimal price;
    private Boolean isPerUnit;
    private double expectedWeight;
    private double expectedNewWeight=0;
    private int weightLimitinGrams;
    private  int sensitivity;



    private SelfCheckoutStationController controller ;

// constructor

    public addItem(BarcodedUnit item , BarcodedProduct product) {

        // if item trying to add is null
        if (item == null)
            throw new SimulationException(new NullPointerException("item"));

        this.item=item;
        this.price=product.getPrice();
        this.weight=item.getWeight();
        this.isPerUnit=product.isPerUnit();
        this.expectedWeight=product.getExpectedWeight();
    }

// weight update function
    public double expectedWeightUpdate(ElectronicScale baggingArea ,BarcodedProduct product) throws OverloadException {
        double current_weight=baggingArea.getCurrentWeight();
        expectedNewWeight= product.getExpectedWeight()+ current_weight;

       return expectedNewWeight;


    }
    public double bagging_area_weight_change(ElectronicScale baggingArea,Barcode barcode,double weightOfitem) throws OverloadException {

        // will notify the weight change event as well
        item=new BarcodedUnit(barcode,weightOfitem);

        if(item.getWeight()>baggingArea.getWeightLimit()){
            throw new OverloadException();
        }
        else {
            //weight before adding the item

            double before_weight=baggingArea.getCurrentWeight();
            baggingArea.add(item);
            double after_weight=baggingArea.getCurrentWeight();
            return after_weight-before_weight;
        }

    }

    // Block and Unblock the system
    public void block_selfcheckout(SelfCheckoutStationController controller){
        controller.disable(controller);
    }

    public void unblock_selfcheckout(SelfCheckoutStationController controller){
        controller.enable(controller);
    }



}
