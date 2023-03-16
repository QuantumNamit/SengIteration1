/*
package com.autovend.software;

import com.autovend.*;
import com.autovend.devices.observers.*;
import com.autovend.devices.*;
import com.autovend.external.*;
import com.autovend.products.*;


public class PayWithCash implements CustomerObserver {
	// Variable for cash inserted
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

public PayWithCash() {
	//Dummy Object
	Bill Object1 = new Bill(0, null);
	
	
	// Gets the bill value
	Cash_Inserted= Object1.getValue();
	
	//Total_Amount = ;
	
	Amount_Due=Total_Amount;
	Amount_Paid=0;
	Change=0;
	paidinFull=false;
}

public void insertedCash() {
	
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

@Override
public void notifyCustomer() {
	// TODO Auto-generated method stub
	
}


}*/