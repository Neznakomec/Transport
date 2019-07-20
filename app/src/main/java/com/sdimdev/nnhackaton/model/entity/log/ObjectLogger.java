package com.sdimdev.nnhackaton.model.entity.log;

public interface ObjectLogger<T> extends Logger {
	void log(T t);
}
