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
        new CoordinateCollect(context, dataBaseProvider).startScan();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
