package com.sdimdev.nnhackaton.data.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sdimdev.nnhackaton.data.persistence.DataBaseProvider;
import com.sdimdev.nnhackaton.di.DIManager;
import com.sdimdev.nnhackaton.presentation.view.search.CoordinateCollect;

import java.util.Timer;
import java.util.TimerTask;

public class CollectService extends Service {

    private Context context;
	private DataBaseProvider dataBaseProvider;

	// constant
	public static final long NOTIFY_INTERVAL = 10 * 1000; // 10 seconds

	// run on another Thread to avoid crash
	private Handler mHandler = new Handler();
	// timer handling
	private Timer mTimer = null;

    @Override
    public void onCreate() {
        super.onCreate();
		context = getApplicationContext();
		dataBaseProvider = DIManager.get().getAppComponent().provideDataBaseProvider();

		// cancel if already existed
		if(mTimer != null) {
			mTimer.cancel();
		} else {
			// recreate new
			mTimer = new Timer();
		}
		// schedule task
		mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);

        //
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

	class TimeDisplayTimerTask extends TimerTask {

		@Override
		public void run() {
			// run on another thread
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					new CoordinateCollect(context, dataBaseProvider).startScan();
				}

			});
		}
	}
}
