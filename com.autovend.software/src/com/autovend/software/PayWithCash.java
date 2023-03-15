


package com.autovend.software;

import com.autovend.Bill;
import com.autovend.devices.observers.*;
import com.autovend.devices.*;
import com.autovend.external.*;
import com.autovend.products.*;


public class PayWithCash {
	// Variable for cash inserted
		private int  Cash_Inserted;
		// Variable for total Bill
		private int Total_Amount;
		// Variable for total Due
		private int amount_Due;
		// variable for amount Paid
		private int amount_Paid;
		// Variable for giving the change
		private int change;

public PayWithCash() {

}

public void insertedCash() {
	if (Cash_Inserted>0) {
		amount_Paid+=Cash_Inserted;
		amount_Due = Total_Amount- amount_Paid; // change later
	}
		
}


}