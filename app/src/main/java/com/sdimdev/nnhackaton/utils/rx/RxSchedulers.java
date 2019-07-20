package com.sdimdev.nnhackaton.utils.rx;

import io.reactivex.Scheduler;

public interface RxSchedulers {


	Scheduler network();

	/**
	 * Io thread for all non network io
	 */
	Scheduler io();

	/**
	 * computation thread
	 */
	Scheduler computation();

	Scheduler newThread();

	Scheduler immediate();

	Scheduler mainThread();
}