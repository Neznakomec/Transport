package com.sdimdev.nnhackaton.model.entity.log;

import kotlin.NotImplementedError;

public interface Logger {
	void log(String message);

	default void log(String message, Throwable throwable) {
		throw new NotImplementedError();
	}
}
