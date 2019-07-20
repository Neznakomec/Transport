package com.sdimdev.nnhackaton.utils.system.other;

import android.util.Log;

public class ExceptionHandler {
	// uncaught exception handler variable
	private Thread.UncaughtExceptionHandler defaultUEH;

	// handler listener
	private Thread.UncaughtExceptionHandler _unCaughtExceptionHandler =
			new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread thread, Throwable ex) {
					//TODO save exception to db
					Log.d("Error", "uncaughtException of Thread:" + thread.getName(), ex);
					// re-throw critical exception further to the os (important)
					defaultUEH.uncaughtException(thread, ex);
				}
			};

	public void register() {
		defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);
	}

}
