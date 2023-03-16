package com.autovend.software;

	
	import com.autovend.Barcode;
	import com.autovend.SellableUnit;
	import com.autovend.devices.BarcodeScanner;
	import com.autovend.devices.SelfCheckoutStation;
	import com.autovend.devices.SimulationException;
	import com.autovend.products.BarcodedProduct;

	import java.math.BigDecimal;


	public class addItemByScanning  extends addItem implements CustomerObserver {


	    private SelfCheckoutStation slf;
	    private Barcode barcode;
	    private String description;
	    private double weight;
	    private BigDecimal cost;

	    public addItemByScanning(SellableUnit item, BarcodedProduct product){

	        // all the general information about the item

	        super(item, product);

	        // information about the item if adding the item by scanning the barcode

	            this.barcode=product.getBarcode();
	            this.description= product.getDescription();

	    }


	   public void scanItem(BarcodeScanner barcodeScanner,SellableUnit item, BarcodedProduct product) throws Exception {

	       // laser scanner scans the items and if successful it will notify all the Barcode Scanner observers
	       if(barcodeScanner.scan(item)){

	            //  should block out the self checkout station form further customer interaction
	           block_selfcheckout();

	           weight= item.getWeight();
	           cost=product.getPrice();

	           expectedWeightUpdate(item,product);

	           // notify customer to place the item

	           notifyCustomer();

	           // bagging area weight change signal
	           bagging_area_weight_change(slf);
	           if(bagging_area_weight_change(slf)!=expectedWeightUpdate(item,product)){
	              throw new Exception("Weight Discrepancy Occurred");
	           }

	           // unblock selfcheckout station
	           unblock_selfcheckout();


	       }

	   }


	    @Override
	    public void notifyCustomer() {
	        System.out.println("Enter the item to the Bagging Area ");
	    }
	}



