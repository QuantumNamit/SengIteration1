// Group Members
// Abrar Zawad Safwan -30150892
// Faiyaz Altaf Pranto - 30162576
// Namit Aneja -30146188
// Bheesha Kumari - 30158810

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

/** Handles the Payment System that involves cash
 *  Contains The Cash Algorithm After Cash Insertion
 *  The SystemController class has the method to handle change
 **/



    /**
     2. System: Reduces the remaining amount due by the value of the inserted cash.
     3. System: Signals to the Customer I/O the updated amount due after the insertion of each coin or banknote.
     4. Customer I/O: Updates the amount due displayed to the customer.
     * @throws OverloadException
     * @throws SimulationException
     * @throws DisabledException
     **/

    public void Cash_Algorithm(SystemController controller,BarcodedProduct product ,Bill bill,Boolean paidinFull,int cash_Inserted, ArrayList<Product> products) throws DisabledException, SimulationException, OverloadException {

        int amount_paid=0;
        int Total_Amount=controller.total_amount(products);

        controller.enable(controller);

        // Scenario 1 Cash I/O: Signals the insertion of  banknotes to the System.
        BillValidator billValidator =controller.billValidator;
        controller.getting_Bill_Value(billValidator,bill);

        while (!paidinFull) {
            if (cash_Inserted >0) {

                amount_paid += cash_Inserted;
                amount_due= Total_Amount - amount_paid;

                // 5. System: If the remaining amount due is greater than 0, go to 1.

                if (amount_due>0) {
                    paidinFull = false;
                    controller.notifyCustomer_due_balance(amount_due);
                    break;

                }

                // 6. System: If the remaining amount due is less than 0, signal to Cash I/O the amount of change due.
                else if (amount_due < 0) {

                    // Calculates the change
                    change = amount_due*-1;

                    controller.notifyCustomerChange(getChange());
                    ArrayList<Bill> bills=new ArrayList<>();

                    // 8. Once payment in full is made and change returned to the customer, see Print Receipt.
                    controller.giving_out_change(controller,getChange(),allDenominationBills(bills));        // Calling to method for Change
                    paidinFull = true;
                    controller.notifyPrint_Receipt();

                }
                else {
                    paidinFull = true;
                    // calls print receipt when paid in full is true
                    controller.notifyPrint_Receipt();
                }
            }
        }
    }

    // Encapsulation for Change
    public int getChange() {
        return change;
    }
    // Encapsulation for Amount Due
    public int getAmount_Due() {
        return amount_due;
    }


}
