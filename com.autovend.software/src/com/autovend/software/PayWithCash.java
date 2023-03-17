// Group Members
// Abrar Zawad Safwan -30150892
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

// Denominations Array
// Bill Dispense Array
// Function - Total Amount 
// Customer Cash Inserted
// Change Function
// Bill Slot observer
// Bill dispenser observer
// Everything with bill

/*
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
*/


public class PayWithCash implements CustomerObserver {
		private Currency currency;
		private int value;
	// Variable for cash inserted
		private int[] Denominations;
		private BigDecimal[] coinDenominations;
		private int  Cash_Inserted;
		// Variable for total Bill
		private int Total_Amount;
		// Variable for total Due
		private int Amount_Due;
		// variable for amount Paid
		private int Amount_Paid;
		// Variable for Change after bill paid
		private int Change;
		// Boolean Variable for payment completion
		private boolean paidinFull;
		private int scaleMaximumWeight;
		private int scaleSensitivity;
		private ArrayList<BarcodedProduct> items;

		
public PayWithCash() {
	//Dummy Object
	Bill bill = new Bill(value, currency);
	
	SystemController controller=new SystemController (currency, Denominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);
	
	Map<Integer, BillDispenser> bill_dispenser = controller.billDispensers;
	BillSlot bill_input = controller.billInput; 	// input channel
	BillSlot bill_output = controller.billOutput; 	// output channel
	BillStorage bill_storage = controller.billStorage;
	BillValidator billValidator = controller.billValidator;
	
		
}

public PayWithCash(int TotalAmount, int BillInserted) {
	
	Cash_Inserted= BillInserted;
	Total_Amount=TotalAmount;
	Amount_Due=Total_Amount;
	Amount_Paid=0;
	Change=0;
	paidinFull=false;
}
// Scenario 1 Cash I/O: Signals the insertion of coins and banknotes to the System.
public int Getting_Bill_Value(Bill bill, BillValidator billVAlidator) {
	int x=0;
	if (billVAlidator.accept(bill)) {
		x=bill.getValue();
	}
	return x;
}

public void initialization(Bill bill, BillValidator billValidator) {
	Cash_Inserted= Getting_Bill_Value(bill, billValidator);
	
	//Total_Amount = ;
	Amount_Due=Total_Amount;
	Amount_Paid=0;
	Change=0;
	paidinFull=false;
}

/* Scenario 2: System: Reduces the remaining amount due by the value of the inserted cash.
4. Customer I/O: Updates the amount due displayed to the customer.
5. System: If the remaining amount due is greater than 0, go to 1.
6. System: If the remaining amount due is less than 0, signal to Cash I/O the amount of change due.
*/
public void Cash_Algorithm(Bill bill) {
	
	while(paidinFull==false) {
	
		if (Cash_Inserted>0) {
		Amount_Paid+=Cash_Inserted;
		Amount_Due = Total_Amount- Amount_Paid;
		
		if (Amount_Due > 0) {
			paidinFull =false;
			System.out.printf("Your new amount due is : %d CAD.", Amount_Due );
			Cash_Inserted=bill.getValue();
		
		}
		else if (Amount_Due < 0) {
		// Calculates the change	
		Change = Amount_Due*-1;
		System.out.printf("Your Change is: %d CAD.", Change );
			paidinFull=true;
			Cash_Inserted=bill.getValue();
		}
		else {
			paidinFull=true;
		}
	}
	}
}


// 7. Cash I/O: Dispense the change due to the customer.
public void Change_Function() {
	// Number of Bills to output
	int num_of_Bills;

	while (Change>5) {
		if (Change>=100 ) {
			num_of_Bills= Change / 100;
			
			Change = Change - num_of_Bills*100;
	
		if (Change >=50) {
			num_of_Bills= Change / 50;
			// Emit {num_of_bills} of 50
			Change = Change - num_of_Bills*50;
		}
		if (Change >=20) {
			num_of_Bills= Change / 20;
			// Emit {num_of_bills} of 20
			Change = Change - num_of_Bills*20;
		}
		if (Change >=10) {
			num_of_Bills= Change / 10;
			// Emit {num_of_bills} of 10
			Change = Change - num_of_Bills*10;
		}
		if (Change >=5) {
			num_of_Bills= Change / 5;
			// Emit {num_of_bills} of 5
			Change = Change - num_of_Bills*5;
		}
		// Exception 2 : Insufficient change
		if (Change >0) {
			System.out.println("Station is Suspended. Please wait for assistance.");
		}
		
	}
}
}

//8. Once payment in full is made and change returned to the customer, see Print Receipt.
// calls print receipt when paid in full is true
public void PrintReceipt() {
	receiptPrinterSoftware receipt = new receiptPrinterSoftware(items);
}	



@Override
public void notifyCustomer() {
	
	// TODO Auto-generated method stub
	
}

@Override
public void notifyCustomerSessionComplete() {
	// TODO Auto-generated method stub
	
}

@Override
public void notifyCustomer_Amount_Due() {
	// TODO Auto-generated method stub
	
}


}