package com.autovend.software;

	
	import com.autovend.SellableUnit;
	import com.autovend.devices.ElectronicScale;
	import com.autovend.devices.SelfCheckoutStation;
	import com.autovend.devices.SimulationException;
	import com.autovend.products.BarcodedProduct;

	import java.math.BigDecimal;

	public class addItem {

	    // information about the item
	    private SellableUnit item;
	    private double weight;
	    private BigDecimal price;
	    private Boolean isPerUnit;
	    private ElectronicScale baggingArea ;
	    private double expectedWeight;
	    private double expectedWeightOfBaggingArea=0;

	    private SelfCheckoutStationController controller ;

	    private boolean block ;

	// constructor

	    public addItem(SellableUnit item , BarcodedProduct product) {

	        // if item trying to add is null
	        if (item == null)
	            throw new SimulationException(new NullPointerException("item"));

	        this.item=item;
	        this.price=product.getPrice();
	        this.weight=item.getWeight();
	        this.isPerUnit=product.isPerUnit();
	        this.expectedWeight=product.getExpectedWeight();
	    }

	// weight update function
	    public double expectedWeightUpdate(SellableUnit item ,BarcodedProduct product){

	       return expectedWeightOfBaggingArea+= product.getExpectedWeight();


	    }
	    public double bagging_area_weight_change(SelfCheckoutStation slf){

	        baggingArea=slf.baggingArea;
	        // will notify the weight change event as well
	        baggingArea.add(item);
	        return item.getWeight();

	    }

	    // Block and Unblock the system
	    public void block_selfcheckout(){
	        controller.block();
	    }

	    public void unblock_selfcheckout(){
	        controller.unblock();
	    }



	}


