package com.sdimdev.nnhackaton.model.entity.time;


import java.util.Date;

public class StartWithTimeSource implements TimeSource {
    private long startTime = System.currentTimeMillis();
    private Date time;

    public StartWithTimeSource(long time) {
        this.time = new Date(time);
    }

    public StartWithTimeSource(Date time) {
        this.time = time;
    }

    @Override
    public long getTime() {
        return time.getTime() + System.currentTimeMillis() - startTime;
    }

    @Override
    public Date getDateTime() {
        return new Date(getTime());
    }
}
