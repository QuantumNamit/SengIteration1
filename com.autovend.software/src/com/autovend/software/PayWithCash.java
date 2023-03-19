// Group Members
// Abrar Zawad Safwan -30150892
// Faiyaz Altaf Pranto - 30162576
// Namit Aneja -30146188
// Bheesha Kumari -30158810

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






public class PayWithCash  {

    private Currency currency;						// Currency variable
    private ArrayList<BarcodedProduct> items;		// Array list for items
    public int change;								// Variable for Change
    public int amount_due;							// Represents the amount due
    

    // Currency Initiator
    public PayWithCash(Currency currency) {
       this.currency=currency;

    }
    // Array list for Bills
    public  ArrayList<Bill>  allDenominationBills(ArrayList<Bill> bills){
    	// 100$ Bill Object
        Bill bill_100 = new Bill (100, currency.getInstance("CAD"));
        bills.add(0,bill_100);

        // 50$ Bill Object
        Bill bill_50 = new Bill (50, currency.getInstance("CAD"));
        bills.add(1,bill_50);

        // 20$ Bill Object
        Bill bill_20 = new Bill (20, currency.getInstance("CAD"));
        bills.add(2,bill_20);
        
        // 10$ Bill Object
        Bill bill_10 = new Bill (10, currency.getInstance("CAD"));
        bills.add(3,bill_10);

        // 5$ Bill Object
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

    public void Cash_Algorithm(SystemController controller,BarcodedProduct product ,Bill bill,Boolean paidinFull,int cash_Inserted, int Total_Amount) throws DisabledException, SimulationException, OverloadException {

    	int amount_paid=0;
        
        
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
