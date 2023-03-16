package com.autovend.software;
import com.autovend.*;
import com.autovend.devices.OverloadException;
import com.autovend.devices.ReceiptPrinter;
import com.autovend.devices.SelfCheckoutStation;
import com.autovend.products.BarcodedProduct;
import java.util.ArrayList;

public class receiptPrinterSoftware  implements AttendantObserver,CustomerObserver{

	        private ArrayList<BarcodedProduct> items;
	        private ReceiptPrinterController controller;
	        private ReceiptPrinter printer;
	        private int paperRemaining;
	        private int inkRemaining;
	        private SelfCheckoutStationController slf;
	        private AttendantObserver observer;
	        private int paper_units;
	        private int ink_units;

	        public receiptPrinterSoftware(ArrayList<BarcodedProduct> items){
	            this.items = items;
	        }

	        public void printReceipt(boolean paidInFull, BarcodedProduct product) {
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
	                    controller.abortPrinting();
	                    notifyAttendant();
	                }
	            } catch (Exception e) {
	                // Handle printer exception
	                System.out.println("Error while printing receipt: " + e.getMessage());
	            }
	        }

	    @Override
	    public void notifyAttendant() throws OverloadException {
	        // Inform attendant to print duplicate receipt and perform maintenance
	        System.out.println("Receipt printer ran out of paper or ink. Please print a duplicate receipt and perform maintenance on the station.");
	        if(controller.outOfPaper(paperRemaining)){

	            observer.putPaper();
	        }
	        else if (controller.outOfInk(inkRemaining)){
	            observer.putInk();
	        }

	    }

	    @Override
	    public void putPaper() throws OverloadException {
	        printer.addPaper(paper_units);
	    }

	    @Override
	    public void putInk() throws OverloadException {
	        printer.addInk(ink_units);
	    }

	    @Override
	    public void notifyCustomer() {
	        System.out.println("Thankyou For Shopping with Us");
	    }

	    @Override
	    public void notifyCustomerSessionComplete() {

	    }
	}


	



