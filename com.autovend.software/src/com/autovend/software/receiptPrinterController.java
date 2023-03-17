package com.autovend.software;
import com.autovend.devices.AbstractDevice;
import com.autovend.devices.ReceiptPrinter;
import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.observers.AbstractDeviceObserver;
import com.autovend.devices.observers.ReceiptPrinterObserver;

public class receiptPrinterController implements ReceiptPrinterObserver {

    private ReceiptPrinter receiptPrinter;


    @Override
    public void reactToEnabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {

    }

    @Override
    public void reactToDisabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {

    }

    @Override
    public void reactToOutOfPaperEvent(ReceiptPrinter printer) {

    }

    @Override
    public void reactToOutOfInkEvent(ReceiptPrinter printer) {

    }

    @Override
    public void reactToPaperAddedEvent(ReceiptPrinter printer) {

    }

    @Override
    public void reactToInkAddedEvent(ReceiptPrinter printer) {

    }


    public boolean outOfInk(int ink_remaining){

            if(ink_remaining==0){
                reactToOutOfInkEvent(receiptPrinter);
                return true;
            }
            else{
                return false;
            }
    }

    public boolean outOfPaper(int paper_remaining){

        if(paper_remaining==0){
            reactToOutOfPaperEvent(receiptPrinter);
            return true;
        }
        else{
            return false;
        }
    }

    public void abortPrinting(SystemController slf){
        // Suspend station
         slf.disable(slf);
    }

}
