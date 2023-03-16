package com.autovend.software;

import com.autovend.devices.OverloadException;

public interface AttendantObserver {

    public void notifyAttendant() throws OverloadException;
    public void putPaper() throws OverloadException;
    public void putInk() throws OverloadException;
}
