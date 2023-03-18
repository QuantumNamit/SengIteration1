package com.autovend.software;
import com.autovend.*;
import com.autovend.devices.EmptyException;
import com.autovend.devices.OverloadException;
import com.autovend.devices.ReceiptPrinter;
import com.autovend.products.BarcodedProduct;

import java.util.ArrayList;


/*
                              Scenario to Focus on:
        1. System: The bill record will be updated with details of the payment(s).
        2. System: Signals to the Receipt Printer to print the bill record.
        3. ReceiptPrinter: Prints the receipt.
        4. System: Signals to Customer I/O that the customer’s session is complete.
        5. Customer I/O: Thanks the Customer.
        6. Customer I/O: Ready for a new customer session.
*/


public class receiptPrinterSoftware {

        private ArrayList<BarcodedProduct> items;
        private ReceiptPrinter printer;
        private SystemController slf;
        private int paper_units;
        private int ink_units;

    /**
     * Constructor
     * @param items- Array list of Barcoded Products
     */
        public receiptPrinterSoftware(ArrayList<BarcodedProduct> items){

            this.items = items;
        }


    /**
     *
     * @param controller- The system controller managing everything
     * @param paidInFull- if the customer has full paid the price
     * @param paperRemaining- paper remaining inside the printer
     * @param inkRemaining-ink remaining inside the printer
     * @throws OverloadException- throws overload exception
     * @throws EmptyException-throws empty exception
     */

        public void printReceipt(SystemController controller,boolean paidInFull,int paperRemaining,int inkRemaining) throws OverloadException, EmptyException {

                controller.enable(controller);
                ReceiptPrinter printer=controller.printer;

                if (paidInFull) {
                    for (BarcodedProduct unit : items) {
                        String itemString = "Item: " + unit.getDescription() + ", Weight: " + unit.getExpectedWeight()+ "lbs, Price: " + unit.getPrice() + "$\n";
                        for (int i = 0; i < itemString.length(); i++) {

                            // Check if printer has run out of paper or ink
                            // Created 3 cases

                            // case 1 when the printer is only out of paper

                            if (controller.outOfPaper(printer,paperRemaining) && ! (controller.outOfInk(printer,inkRemaining))) {

                                // Abort printing and suspending station
                                controller.abortPrinting(controller);

                                // The controller would reactToOutOfPaperEvent and inside the reactToOutOfPaperEvent we notify to the attendant (covered Exception)
                                controller.reactToOutOfPaperEvent(printer);

                            }


                            // case 2 when the printer is only out of ink

                            else if (!(controller.outOfPaper(printer,paperRemaining)) &&  controller.outOfInk(printer,inkRemaining)) {

                                // Abort printing and suspending station
                                controller.abortPrinting(controller);

                                // The controller would reactToOutOfIInkEvent and inside the reactToOutOfInkEvent we notify to the attendant (covered Exception)
                                controller.reactToOutOfInkEvent(printer);

                            }

                            // case 3 when the printer is out of both paper and ink
                            else if ((controller.outOfPaper(printer,paperRemaining)) &&  controller.outOfInk(printer,inkRemaining)) {

                                // Abort printing and suspending station
                                controller.abortPrinting(controller);

                                // The controller would reactToOutOfInkEvent and inside the reactToOutOfInkEvent we notify to the attendant (covered Exception)
                                controller.reactToOutOfInkEvent(printer);

                                // The controller would reactToOutOfPaperEvent and inside the reactToOutOfPaperEvent we notify to the attendant (covered Exception)
                                controller.reactToOutOfPaperEvent(printer);
                            }
                            // printing the character on the receipt
                            printer.print(itemString.charAt(i));
                        }
                    }

                // once successfully printed all the things on the receipt
                // printer should cut the paper
                printer.cutPaper();

               // If the session is successfully completed then system will notify the Customer I/O  (covered Scenario 4)
                controller.notifyCustomerSessionComplete();
                controller.notifyCustomer_thanking();
                }

                }


                }

