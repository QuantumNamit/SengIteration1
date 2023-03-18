// Group Members
// Abrar Zawad Safwan -30150892
// Faiyaz Altaf Pranto - 30162576
// Add your names here*


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
		private int value;
		// Variable for cash inserted
		private int[] CashDenominations = {5, 10, 20, 50, 100};
		private BigDecimal[] coinDenominations;
		private int  Cash_Inserted;
		// Variable for total Bill
		private int Total_Amount;
		// Variable for total Due
		private int Amount_Due;
		// variable for total Amount Pid
		private int Amount_Paid;
		// Variable for Change after bill paid
		private int Change;
		// Boolean Variable for payment completion
		private boolean paidinFull;
		private int scaleMaximumWeight;
		private int scaleSensitivity;
		private ArrayList<BarcodedProduct> items;
		
		// Bill 
		Bill bill = new Bill(value, currency);
		Bill bill_100 = new Bill (100, currency.getInstance("CAD"));
		Bill bill_50 = new Bill (50, currency.getInstance("CAD"));
		Bill bill_20 = new Bill (20, currency.getInstance("CAD"));
		Bill bill_10 = new Bill (10, currency.getInstance("CAD"));
		Bill bill_5 = new Bill (5, currency.getInstance("CAD"));
		SystemController controller=new SystemController (currency, CashDenominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);
		
		Map<Integer, BillDispenser> bill_dispenser = controller.billDispensers;
		BillSlot bill_input = controller.billInput; 	// input channel
		BillSlot bill_output = controller.billOutput; 	// output channel
		BillStorage bill_storage = controller.billStorage;
		BillValidator billValidator = controller.billValidator;
		
public PayWithCash() {
	//Dummy Object
		
}

public PayWithCash(int TotalAmount, int BillInserted) {
	
	Cash_Inserted= BillInserted;
	Total_Amount=TotalAmount;
	Amount_Due=Total_Amount;
	Amount_Paid=0;
	Change=0;
	paidinFull=false;
}

// Scenario 1 Cash I/O: Signals the insertion of  banknotes to the System.
public int Getting_Bill_Value(Bill bill) {
	int x = 0;
	if (billValidator.accept(bill)) {
		x = bill.getValue();
		return x;
	}
	return x;
	}

public void initialization(Bill bill, int totalAmount) {
	Cash_Inserted=Getting_Bill_Value(bill);
	Total_Amount =totalAmount;
	Amount_Due=Total_Amount;
	Amount_Paid=0;
	Change=0;
	paidinFull=false;
}

/** 
2. System: Reduces the remaining amount due by the value of the inserted cash.
3. System: Signals to the Customer I/O the updated amount due after the insertion of each coin or banknote.
4. Customer I/O: Updates the amount due displayed to the customer.
 * @throws OverloadException 
 * @throws SimulationException 
 * @throws DisabledException 
**/
public void Cash_Algorithm(Bill bill) throws DisabledException, SimulationException, OverloadException {
	
	while(isPaidinFull()==false) {
		if (getCash_Inserted()>0) {
			Amount_Paid+=getCash_Inserted();
			Amount_Due = Total_Amount - Amount_Paid;
		
			// 5. System: If the remaining amount due is greater than 0, go to 1.
			if (getAmount_Due() > 0) {
				paidinFull =false;
				System.out.printf("Your new amount due is : %d CAD.", getAmount_Due() );
			
			}
			// 6. System: If the remaining amount due is less than 0, signal to Cash I/O the amount of change due.
			else if (getAmount_Due() < 0) {
				// Calculates the change	
				Change = getAmount_Due()*-1;
				System.out.printf("Your Change is: %d CAD.", getChange());
				Change_Function();		// Calling to method for Change
				paidinFull=true;
				PrintReceipt();
			}
			else {
				paidinFull=true;
				PrintReceipt();
			}
		}
	}
	// 8. Once payment in full is made and change returned to the customer, see Print Receipt.
	// calls print receipt when paid in full is true
	
		
	
}


// 7. Cash I/O: Dispense the change due to the customer.
public void Change_Function() throws DisabledException, SimulationException, OverloadException {
	// Number of Bills to output
	int num_of_Bills;

	while (getChange()>5) {
		// Checks for number of 100$  bills needed for the change
		if (getChange()>=100 ) {
			num_of_Bills= getChange() / 100;
			for (int i=0; i<num_of_Bills; i++)
			{
				bill_output.emit(bill_100);
			}
			Change = getChange() - num_of_Bills*100;
		}
		// Checks for number of 50$  bills needed for the change
		if (getChange() >=50) {
			num_of_Bills= getChange() / 50;
			// Emit {num_of_bills} of 50
			for (int i=0; i<num_of_Bills; i++)
			{
				bill_output.emit(bill_50);
			}
			Change = getChange() - num_of_Bills*50;
		}
		// Checks for number of 20$  bills needed for the change
		if (getChange() >=20) {
			num_of_Bills= getChange() / 20;
			for (int i=0; i<num_of_Bills; i++)
			{
				bill_output.emit(bill_20);
			}
			Change = getChange() - num_of_Bills*20;
		}
		// Checks for number of 10$  bills needed for the change
		if (getChange() >=10) {
			num_of_Bills= getChange() / 10;
			for (int i=0; i<num_of_Bills; i++)
			{
				bill_output.emit(bill_10);
			}
			Change = getChange() - num_of_Bills*10;
		}
		// Checks for number of 5$  bills needed for the change
		if (getChange() >=5) {
			num_of_Bills= getChange() / 5;
			for (int i=0; i<num_of_Bills; i++)
			{
				bill_output.emit(bill_5);
			}
			Change = getChange() - num_of_Bills*5;
		}
		
	}
	// Exception 2 : Insufficient change
	if ((Change > 0) && (Change<5 )  ) {
		System.out.println("Station is Suspended. Please wait for assistance.");
		// CAll an Attendant
	}
	
}
// Calls PRint Receipt 
public void PrintReceipt() {
	receiptPrinterSoftware receipt = new receiptPrinterSoftware(items);
}	



// For Testing Purposes
/** Return Cash Inserted **/
public int getCash_Inserted() {
	return Cash_Inserted;
}

/** Returns Amount Due **/
public int getAmount_Due() {
	return Amount_Due;
}
/** Return Change Amount **/
public int getChange() {
	return Change;
}

/** Return Paid in Full boolean **/
public boolean isPaidinFull() {
	return paidinFull;
}


}