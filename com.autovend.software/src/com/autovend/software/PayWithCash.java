// Group Members
// Abrar Zawad Safwan -30150892
// Faiyaz Altaf Pranto - 30162576
// Namit Aneja -30146188

package com.autovend.software;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Map;

import com.autovend.*;
import com.autovend.devices.observers.*;
import com.autovend.devices.*;
import com.autovend.external.*;
import com.autovend.products.*;




/**
 Scenario:
 1. Cash I/O: Signals the insertion of coins and banknotes to the System.
 2. System: Reduces the remaining amount due by the value of the inserted cash.
 3. System: Signals to the Customer I/O the updated amount due after the insertion of each coin or
 banknote.
 4. Customer I/O: Updates the amount due displayed to the customer.
 5. System: If the remaining amount due is greater than 0, go to 1.
 6. System: If the remaining amount due is less than 0, signal to Cash I/O the amount of change due.
 7. Cash I/O: Dispense the change due to the customer.
 8. Once payment in full is made and change returned to the customer, see Print Receipt.
 **/


public class PayWithCash  {

    private Currency currency;
    private ArrayList<BarcodedProduct> items;


    public PayWithCash(Currency currency) {
       this.currency=currency;

    }

    public  ArrayList<Bill>  allDenominationBills(ArrayList<Bill> bills){

        Bill bill_100 = new Bill (100, currency.getInstance("CAD"));
        bills.add(0,bill_100);


        Bill bill_50 = new Bill (50, currency.getInstance("CAD"));
        bills.add(1,bill_50);

        Bill bill_20 = new Bill (20, currency.getInstance("CAD"));
        bills.add(2,bill_20);

        Bill bill_10 = new Bill (10, currency.getInstance("CAD"));
        bills.add(3,bill_10);

        Bill bill_5 = new Bill (5, currency.getInstance("CAD"));
        bills.add(4,bill_5);

        return bills;
    }

    /**
     2. System: Reduces the remaining amount due by the value of the inserted cash.
     3. System: Signals to the Customer I/O the updated amount due after the insertion of each coin or banknote.
     4. Customer I/O: Updates the amount due displayed to the customer.
     * @throws OverloadException
     * @throws SimulationException
     * @throws DisabledException
     **/

    public void Cash_Algorithm(SystemController controller,BarcodedProduct product ,Bill bill,Boolean paidinFull,BigDecimal cash_Inserted) throws DisabledException, SimulationException, OverloadException {

        BigDecimal amount_paid=BigDecimal.valueOf(0);
        BigDecimal amount_due;
        BigDecimal change;
        controller.enable(controller);


        // Scenario 1 Cash I/O: Signals the insertion of  banknotes to the System.
        BillValidator billValidator =controller.billValidator;
        controller.getting_Bill_Value(billValidator,bill);

        while (!paidinFull) {
            if (cash_Inserted.compareTo(BigDecimal.valueOf(0))>0) {

                amount_paid = amount_paid.add(cash_Inserted);
                amount_due= controller.getPrice(product).subtract(amount_paid);

                // 5. System: If the remaining amount due is greater than 0, go to 1.

                if (amount_due.compareTo(BigDecimal.valueOf(0)) >0) {
                    paidinFull = false;
                    controller.notifyCustomer_due_balance(amount_due);

                }

                // 6. System: If the remaining amount due is less than 0, signal to Cash I/O the amount of change due.
                else if (amount_due.compareTo(BigDecimal.valueOf(0)) < 0) {

                    // Calculates the change
                    change = amount_due.multiply(BigDecimal.valueOf(-1));

                    controller.notifyCustomerChange(change);
                    ArrayList<Bill> bills=new ArrayList<>();

                    // 8. Once payment in full is made and change returned to the customer, see Print Receipt.
                    controller.giving_out_change(controller,change,allDenominationBills(bills));        // Calling to method for Change
                    paidinFull = true;
                    PrintReceipt();

                }
                else {
                    paidinFull = true;
                    // calls print receipt when paid in full is true
                    PrintReceipt();
                }
            }
        }
    }

    // Calls PRint Receipt
    public void PrintReceipt() {
        receiptPrinterSoftware receipt = new receiptPrinterSoftware(items);
    }

}
