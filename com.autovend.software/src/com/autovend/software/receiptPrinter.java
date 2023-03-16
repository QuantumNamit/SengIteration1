package com.autovend.software;
import com.autovend.devices.*;
import com.autovend.devices.observers.*;
import com.autovend.external.*;
import com.autovend.products.*;
import com.autovend.*;

public class receiptPrinter { 
	
	private ArrayList<SellableUnit> items; 
	
	public ReceiptPrinter(ArrayList<SellableUnit> items){
	this.items = items;
	}
	
	public void printReceipt(boolean paidInFull) {
  try {
      if (paidInFull) {
          for (SellableUnit unit : items) {
              String itemString = "Item: " + unit.getDescription() + ", Weight: " + unit.getWeight() + "lbs, Price: " + unit.getPrice() + "$\n";
              for (int i = 0; i < itemString.length(); i++) {
                  print(itemString.charAt(i));
              }
          }
      }
      
      // Check if printer has run out of paper or ink
      if (isOutOfPaper() || isOutOfInk()) {
          // Abort printing 
          abort();
          
          // Suspend station
          Station.suspend();
          
          // Inform attendant to print duplicate receipt and perform maintenance
          System.out.println("Receipt printer ran out of paper or ink. Please print a duplicate receipt and perform maintenance on the station.");
      }
  } catch (PrinterException e) {
      // Handle printer exception
      System.out.println("Error while printing receipt: " + e.getMessage());
  }
}
	
}


