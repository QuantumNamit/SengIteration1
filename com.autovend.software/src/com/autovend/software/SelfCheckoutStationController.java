package com.autovend.software;
import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SimulationException;

import java.math.BigDecimal;
import java.util.Currency;

public class SelfCheckoutStationController extends SelfCheckoutStation {

    private SelfCheckoutStationController selfCheckoutStationController;


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
    public SelfCheckoutStationController(Currency currency, int[] billDenominations, BigDecimal[] coinDenominations, int scaleMaximumWeight, int scaleSensitivity) {
        super(currency, billDenominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);


    }

    // Blocks the Self Checkout Station
    public void disable(SelfCheckoutStationController selfCheckoutStationController){
        selfCheckoutStationController.baggingArea.disable();
        selfCheckoutStationController.billStorage.disable();
        selfCheckoutStationController.scale.disable();
        selfCheckoutStationController.handheldScanner.disable();
        selfCheckoutStationController.mainScanner.disable();
        selfCheckoutStationController.billInput.disable();
        selfCheckoutStationController.billOutput.disable();
        selfCheckoutStationController.printer.disable();

    }

    public void enable(SelfCheckoutStationController selfCheckoutStationController){
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
