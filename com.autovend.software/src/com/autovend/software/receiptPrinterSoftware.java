package com.autovend.software;
import com.autovend.*;
import com.autovend.devices.EmptyException;
import com.autovend.devices.OverloadException;
import com.autovend.devices.ReceiptPrinter;
import com.autovend.devices.SelfCheckoutStation;
import com.autovend.products.BarcodedProduct;

import java.util.ArrayList;

public class receiptPrinterSoftware implements AttendantObserver,CustomerObserver{

        private ArrayList<BarcodedProduct> items;
        private ReceiptPrinter printer;

        private SelfCheckoutStationController slf;
        private int paper_units;
        private int ink_units;

        //Constructor
        public receiptPrinterSoftware(ArrayList<BarcodedProduct> items){
            this.items = items;
        }


        // print receipt
        public void printReceipt(SelfCheckoutStationController slf,ReceiptPrinter printer,receiptPrinterController controller,AttendantObserver observer ,boolean paidInFull,int paperRemaining,int inkRemaining) throws OverloadException, EmptyException {
            try {

                if (paidInFull) {
                    for (BarcodedProduct unit : items) {
                        String itemString = "Item: " + unit.getDescription() + ", Weight: " + unit.getExpectedWeight()+ "lbs, Price: " + unit.getPrice() + "$\n";
                        for (int i = 0; i < itemString.length(); i++) {
                            printer.print(itemString.charAt(i));
                        }
                    }
                printer.cutPaper();
                notifyCustomerSessionComplete();
                notifyCustomer();
            }

                // Check if printer has run out of paper or ink
                if (controller.outOfPaper(paperRemaining) || controller.outOfInk(inkRemaining)) {
                    // Abort printing and suspending station
                    controller.abortPrinting(slf);
                    notifyAttendant(printer,controller,observer,paperRemaining,inkRemaining);
                }
            } catch (Exception e) {
                // Handle printer exception
             System.out.println("Error while printing receipt: " + e.getMessage());
            }
        }

    @Override
    public void putPaper(ReceiptPrinter printer) throws OverloadException {
        printer.addPaper(paper_units);
    }

    @Override
    public void putInk(ReceiptPrinter printer) throws OverloadException {
        printer.addInk(ink_units);
    }

    @Override
    public void notifyCustomer() {
        System.out.println("Thankyou For Shopping with Us");
    }

    @Override
    public void notifyCustomerSessionComplete(){
        System.out.println("Session Complete ");
    }

    @Override
    public void notifyAttendant(ReceiptPrinter printer,receiptPrinterController controller,AttendantObserver observer,int paperRemaining ,int inkRemaining) throws OverloadException {
        // Inform attendant to print duplicate receipt and perform maintenance
        System.out.println("Receipt printer ran out of paper or ink. Please print a duplicate receipt and perform maintenance on the station.");
        if(controller.outOfPaper(paperRemaining)){

            observer.putPaper(printer);
        }
        else if (controller.outOfInk(inkRemaining)){
            observer.putInk(printer);
        }

    }

}

