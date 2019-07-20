package com.sdimdev.nnhackaton.model.entity.time;


import java.util.Date;

public class FixedTimeSource implements TimeSource {
    private Date time;

    public FixedTimeSource(long time) {
        this.time = new Date(time);
    }

    public FixedTimeSource(Date time) {
        this.time = time;
    }

    @Override
    public long getTime() {
        return time.getTime();
    }

    @Override
    public Date getDateTime() {
        return time;
    }
}
