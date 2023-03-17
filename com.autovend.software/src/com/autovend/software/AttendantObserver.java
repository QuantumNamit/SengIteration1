package com.autovend.software;
import com.autovend.devices.OverloadException;
import com.autovend.devices.ReceiptPrinter;

public interface AttendantObserver {

    public void notifyAttendant(ReceiptPrinter printer ,receiptPrinterController controller ,AttendantObserver observer,int paperRemaining ,int inkRemaining) throws OverloadException;
    public void putPaper(ReceiptPrinter printer) throws OverloadException;
    public void putInk(ReceiptPrinter printer) throws OverloadException;
}
