// Group Members
// Abrar Zawad Safwan -30150892
// Faiyaz Altaf Pranto - 30162576
// Namit Aneja -30146188

package com.autovend.software;
import com.autovend.Barcode;
import com.autovend.BarcodedUnit;
import com.autovend.Bill;
import com.autovend.SellableUnit;
import com.autovend.devices.*;
import com.autovend.devices.observers.AbstractDeviceObserver;
import com.autovend.devices.observers.ReceiptPrinterObserver;
import com.autovend.products.BarcodedProduct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

public class SystemController extends SelfCheckoutStation implements ReceiptPrinterObserver {

    private SystemController controller;
    private double expectedWeightUpdate=0;
    private BarcodedUnit item;
    private  int paper_units;
    private int ink_units;
    


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

    // Functions used inside the addItemByScanning

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


    public void notifyCustomer_to_put_item_in_BaggingArea() {
        System.out.println("Enter the item to the Bagging Area ");
    }

    // Functions Used Inside receiptPrinterClass

    public boolean outOfInk(ReceiptPrinter printer,int ink_remaining){

        if(ink_remaining==0){
            reactToOutOfInkEvent(printer);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean outOfPaper(ReceiptPrinter printer,int paper_remaining){

        if(paper_remaining==0){
            reactToOutOfPaperEvent(printer);
            return true;
        }
        else{
            return false;
        }
    }

    public void abortPrinting(SystemController slf){
        // Suspend station
        slf.disable(slf);
    }

    public void addInk(SystemController controller,int quantity) throws OverloadException {
        ReceiptPrinter printer=controller.printer;
        printer.addInk(quantity);

    }

    public void addPaper(SystemController controller,int quantity) throws OverloadException {
        ReceiptPrinter printer=controller.printer;
        printer.addPaper(quantity);

    }
 // Inform attendant to print duplicate receipt and perform maintenance
    public void notifyAttendant_out_of_paper(ReceiptPrinter printer)  {
    	System.out.println("Receipt printer ran out of paper. Please print a duplicate receipt and perform maintenance on the station.");

    }
    // Inform attendant to print duplicate receipt and perform maintenance
    public void notifyAttendant_out_of_ink(ReceiptPrinter printer)  {
    	System.out.println("Receipt printer ran out of ink. Please print a duplicate receipt and perform maintenance on the station.");

    }


    public void notifyCustomer_thanking() {
        System.out.println("Thankyou For Shopping with Us");
    }

    public void notifyCustomerSessionComplete(){
        System.out.println("Session Complete ");
    }





  // Pay with cash Functions

    public int getting_Bill_Value(BillValidator validator,Bill bill) {

        int x=0;
        if (validator.accept(bill)) {
            x = bill.getValue();
            return x;
        }
        return x;

    }

    public void notifyCustomer_due_balance(int amount_due){
        System.out.println("Your new amount due is CAD " + amount_due);
    }

    public  void notifyCustomerChange(int change){
        System.out.println("Your Change is CAD " + change);
    }


    // 7. Cash I/O: Dispense the change due to the customer.
    public void giving_out_change(SystemController controller,int change, ArrayList<Bill> bills) throws DisabledException, SimulationException, OverloadException {
        // Number of Bills to output

        int num_of_Bills;
        BillSlot bill_output = controller.billOutput;
        /** Creating variables for remaining change for each instance  **/
        int remaining_change_100;
        int remaining_change_50;
        int remaining_change_20;
        int remaining_change_10;
        int remaining_change_5;

        // Checks for number of 100$  bills needed for the change
        if (change >=100) {

            num_of_Bills = change / 100;
            for (int i = 0; i<num_of_Bills; i++) {
                bill_output.emit(bills.get(0));
                //Customer Removes the Bill
                bill_output.removeDanglingBill();
            }
            remaining_change_100 = change - num_of_Bills * 100;
        }
        else{
            remaining_change_100=change;
        }
        // Checks for number of 50$  bills needed for the change
        if (remaining_change_100 >=50) {
            num_of_Bills = remaining_change_100 / 50;
            // Emit {num_of_bills} of 50
            for (int i = 0; i<num_of_Bills; i++) {
                bill_output.emit(bills.get(1));
              //Customer Removes the Bill
                bill_output.removeDanglingBill();
            }
            remaining_change_50 = remaining_change_100 - num_of_Bills * 50;
        }
        else{
            remaining_change_50 =remaining_change_100;;
        }
        // Checks for number of 20$  bills needed for the change

        if (remaining_change_50 >=20) {
            num_of_Bills =  remaining_change_50/20;
            for (int i = 0; i<num_of_Bills; i++) {
                bill_output.emit(bills.get(2));
                //Customer Removes the Bill
                bill_output.removeDanglingBill();
            }
            remaining_change_20 = remaining_change_50 - num_of_Bills * 20;
        }

        else{
            remaining_change_20=remaining_change_50;
        }

        // Checks for number of 10$  bills needed for the change
        if (remaining_change_20 >=10) {
            num_of_Bills = remaining_change_20/10;
            for (int i = 0;i<num_of_Bills; i++) {
                bill_output.emit(bills.get(3));
                //Customer Removes the Bill
                bill_output.removeDanglingBill();
            }
            remaining_change_10 = remaining_change_20 - num_of_Bills * 10;
        }
        else{
            remaining_change_10=remaining_change_20;
        }
        // Checks for number of 5$  bills needed for the change
        if (remaining_change_10 >=5) {
            num_of_Bills = remaining_change_10/5;
            
            for (int i = 0; i<num_of_Bills; i++) {
                bill_output.emit(bills.get(4));
              //Customer Removes the Bill
                bill_output.removeDanglingBill();
            }
            remaining_change_5 = remaining_change_10 - num_of_Bills * 5;
        }
        else{
            remaining_change_5=remaining_change_10;

        }


        // Exception 2 : Insufficient change
        if ((remaining_change_5 >0) && (remaining_change_5 <5 ) ) {
            controller.disable(controller);
            controller.notifyAttendForAssistanceInChange();

        }

    }

      public void notifyAttendForAssistanceInChange(){
          System.out.println("Attendant should help the customer to get the appropriate change");
         

        }
      public void notifyPrint_Receipt(){
          System.out.println("Print the Receipt..");
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

    @Override
    public void reactToEnabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {

    }

    @Override
    public void reactToDisabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {

    }

    @Override
    public void reactToOutOfPaperEvent(ReceiptPrinter printer) {
        notifyAttendant_out_of_paper(printer);
    }

    @Override
    public void reactToOutOfInkEvent(ReceiptPrinter printer) {
        notifyAttendant_out_of_ink(printer);

    }

    @Override
    public void reactToPaperAddedEvent(ReceiptPrinter printer) {

    }

    @Override
    public void reactToInkAddedEvent(ReceiptPrinter printer) {

    }

	
}
