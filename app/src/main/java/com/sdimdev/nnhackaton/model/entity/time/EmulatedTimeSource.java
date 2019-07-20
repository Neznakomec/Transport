package com.sdimdev.nnhackaton.model.entity.time;


import java.util.Date;

public class EmulatedTimeSource implements TimeSource {
    private Date emulatedTime;
    private Date swapedTime;

    public EmulatedTimeSource(Date emulatedTime, Date swapedTime) {
        this.emulatedTime = emulatedTime;
        this.swapedTime = swapedTime;
    }

    public Date getEmulatedTime() {
        return emulatedTime;
    }

    public void setEmulatedTime(Date emulatedTime) {
        this.emulatedTime = emulatedTime;
    }

    public Date getSwapedTime() {
        return swapedTime;
    }

    public void setSwapedTime(Date swapedTime) {
        this.swapedTime = swapedTime;
    }

    @Override
    public long getTime() {
        long now = System.currentTimeMillis();
        long diff = now - swapedTime.getTime();
        long emulatedTimeNow = emulatedTime.getTime() + diff;
        return emulatedTimeNow;
    }

    @Override
    public Date getDateTime() {
        return new Date(getTime());
    }
}
