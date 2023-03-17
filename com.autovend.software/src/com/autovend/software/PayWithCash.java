// Group Members
// Abrar Zawad Safwan -30150892
// Add your names here*


package com.autovend.software;

import java.math.BigDecimal;
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

public PayWithCash() {
	//Dummy Object
	Bill bill = new Bill(value, currency);
	SelfCheckoutStation Object1 = new SelfCheckoutStation(currency, Denominations, coinDenominations, scaleMaximumWeight,scaleSensitivity);
	
	/*
	Map<Integer, BillDispenser> bill_dispenser = Object1.billDispensers;
	BillSlot bill_input = Object1.billInput; 	// input channel
	BillSlot bill_output = Object1.billOutput; 	// output channel
	BillStorage bill_storage = Object1.billStorage;
	BillValidator billValidator = Object1.billValidator;
	*/
	
	
}
public void initialization(Bill bill, BillValidator billValidator) {
	Cash_Inserted= Getting_Bill_Value(bill, billValidator);
	
	//Total_Amount = ;
	Amount_Due=Total_Amount;
	Amount_Paid=0;
	Change=0;
	paidinFull=false;
}

public void Cash_Algorithm() {
	
	while(paidinFull==false) {
	
		if (Cash_Inserted>0) {
		Amount_Paid+=Cash_Inserted;
		Amount_Due = Total_Amount- Amount_Paid;
		
		if (Amount_Due > 0) {
			paidinFull =false;
		// Notify customer about new amount due	
		}
		else if (Amount_Due < 0) {
		// Calculates the change	
		Change = Amount_Due*-1;
		// Notify the customer
			paidinFull=true;
		}
		else {paidinFull=true;
		
		}
	}
	}
}

public void Change_Function() {
	// Number of Bills to output
	int num_of_Bills;

	while (Change>5) {
		if (Change>=100 ) {
			num_of_Bills= Change / 100;
			// Emit {num_of_bills} of 100
			Change = Change - num_of_Bills*100;
			}
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
		
	}
	
}

public int Getting_Bill_Value(Bill bill, BillValidator billVAlidator) {
	int x=0;
	if (billVAlidator.accept(bill)) {
		x=bill.getValue();
	}
	return x;
}

@Override
public void notifyCustomer() {
	// TODO Auto-generated method stub
	
}

@Override
public void notifyCustomerSessionComplete() {
	// TODO Auto-generated method stub
	
}


}