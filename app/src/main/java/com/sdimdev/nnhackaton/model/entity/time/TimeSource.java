package com.sdimdev.nnhackaton.model.entity.time;

import java.util.Date;

public interface TimeSource {
	long getTime();

	Date getDateTime();
}
