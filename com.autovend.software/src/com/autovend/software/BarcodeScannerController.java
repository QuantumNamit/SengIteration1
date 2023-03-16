package com.autovend.software;

import com.autovend.Barcode;
import com.autovend.devices.AbstractDevice;
import com.autovend.devices.BarcodeScanner;
import com.autovend.devices.observers.AbstractDeviceObserver;
import com.autovend.devices.observers.BarcodeScannerObserver;
import com.autovend.external.ProductDatabases;
import com.autovend.products.BarcodedProduct;

import java.util.ArrayList;

public class BarcodeScannerController implements BarcodeScannerObserver {

	    private ArrayList<BarcodedProduct> bp =new ArrayList<>();
	    
	    @Override
	    public void reactToEnabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {

	    }

	    @Override
	    public void reactToDisabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {

	    }

	    @Override
	    public void reactToBarcodeScannedEvent(BarcodeScanner barcodeScanner, Barcode barcode) {

	        BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
	        bp.add(product);

	    }
	}
