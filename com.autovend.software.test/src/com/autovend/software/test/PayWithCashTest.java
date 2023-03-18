package com.autovend.software.test;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Map;

import org.junit.Test;

import com.autovend.*;
import com.autovend.devices.*;
import com.autovend.devices.observers.*;
import com.autovend.external.*;
import com.autovend.products.*;
import com.autovend.software.*;



public class PayWithCashTest {
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
	// variable for total Amount Paid
	private int Amount_Paid;
	// Variable for Change after bill paid
	private int Change;
	// Boolean Variable for payment completion
	private boolean paidinFull;
	private int scaleMaximumWeight;
	private int scaleSensitivity;
	private ArrayList<BarcodedProduct> items;
	
	/* Bill 
	Bill bill = new Bill(value, currency);
	Bill bill_100 = new Bill (100, currency.getInstance("CAD"));
	Bill bill_50 = new Bill (50, currency.getInstance("CAD"));
	Bill bill_20 = new Bill (20, currency.getInstance("CAD"));
	Bill bill_10 = new Bill (10, currency.getInstance("CAD"));
	Bill bill_5 = new Bill (5, currency.getInstance("CAD"));
	*/
	SystemController controller=new SystemController (currency, CashDenominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);
	
	Map<Integer, BillDispenser> bill_dispenser = controller.billDispensers;
	BillSlot bill_input = controller.billInput; 	// input channel
	BillSlot bill_output = controller.billOutput; 	// output channel
	BillStorage bill_storage = controller.billStorage;
	BillValidator billValidator = controller.billValidator;


	/** Tests for if bill value is inserted properly **/
	@Test
	public void Check_CashInserted() {
		PayWithCash Test1= new PayWithCash();
		Bill bill_20 = new Bill(20, Currency.getInstance("CAD"));
        int value = Test1.Getting_Bill_Value(bill_20);
        assertEquals(20, value);

	}
	/** Tests if the Amount is less than total **/
	@Test
	public void Check_CashAlgorithm_AmountLess_thanTotal() {
		int Cash_inserted= 20;
		int Total_Bill=55;
		PayWithCash Test2= new PayWithCash(Cash_inserted, Total_Bill);
		int Amount_Due= Test2.getAmount_Due();
		assertEquals(35, Amount_Due);
	
	}
	/** Tests if the Amount is greater than total **/
	@Test 
	public void Check_CashAlgorithm_AmountGreater_thanTotal() {
		int Cash_inserted= 50;
		int Total_Bill=24;
		PayWithCash Test2= new PayWithCash(Cash_inserted, Total_Bill);
		int Change_Due= Test2.getChange();
		assertEquals(26, Change_Due);
		
	}
	
	}











