package com.sdimdev.nnhackaton.model.entity.time;

import java.util.Date;

public class SystemTimeSourceImpl implements TimeSource {
	@Override
	public long getTime() {
		return System.currentTimeMillis();
	}

	@Override
	public Date getDateTime() {
		return new Date(getTime());
	}
}
