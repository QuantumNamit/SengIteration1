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
import java.util.Currency;

public class SystemController extends SelfCheckoutStation {

    private SystemController selfCheckoutStationController;
    private double expectedWeightUpdate=0;
    private BarcodedUnit item;


    /**
     * Creates a self-checkout station.
     *
     * @param currency           The kind of currency permitted.
     * @param billDenominations  The set of denominations (i.e., $5, $10, etc.) to accept.
     * @param coinDenominations  The set of denominations (i.e., $0.05, $0.10, etc.) to accept.
     * @param scaleMaximumWeight The most weight that can be placed on the scale before it
     *                           overloads.
     * @param scaleSensitivity   Any weight changes smaller than this will not be detected or
     *                           announced.
     * @throws SimulationException If any argument is null or negative.
     * @throws SimulationException If the number of bill or coin denominations is &lt;1.
     */
    public SystemController(Currency currency, int[] billDenominations, BigDecimal[] coinDenominations, int scaleMaximumWeight, int scaleSensitivity) {
        super(currency, billDenominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);


    }

    public double getWeight(SellableUnit item){
        return item.getWeight();
    }

    public BigDecimal getPrice(BarcodedProduct product){
        return product.getPrice();
    }


    // weight update function
    public double expectedWeightUpdate(BarcodedProduct product) throws OverloadException {

        expectedWeightUpdate= product.getExpectedWeight();

        return expectedWeightUpdate;


    }

    public double bagging_area_weight_change(SystemController controller, Barcode barcode, double weightOfitem) throws OverloadException {

        ElectronicScale baggingArea =controller.baggingArea;
        // will notify the weight change event as well
        item=new BarcodedUnit(barcode,weightOfitem);

        if(item.getWeight()>baggingArea.getWeightLimit()){

            throw new OverloadException();
        }
        else {
            return item.getWeight();
        }
    }


    // Blocks the Self Checkout Station
    public void disable(SystemController selfCheckoutStationController){
        selfCheckoutStationController.baggingArea.disable();
        selfCheckoutStationController.billStorage.disable();
        selfCheckoutStationController.scale.disable();
        selfCheckoutStationController.handheldScanner.disable();
        selfCheckoutStationController.mainScanner.disable();
        selfCheckoutStationController.billInput.disable();
        selfCheckoutStationController.billOutput.disable();
        selfCheckoutStationController.printer.disable();

    }

    public void enable(SystemController selfCheckoutStationController){
        selfCheckoutStationController.baggingArea.enable();
        selfCheckoutStationController.billStorage.enable();
        selfCheckoutStationController.scale.enable();
        selfCheckoutStationController.handheldScanner.enable();
        selfCheckoutStationController.mainScanner.enable();
        selfCheckoutStationController.billInput.enable();
        selfCheckoutStationController.billOutput.enable();
        selfCheckoutStationController.printer.enable();


}
}



