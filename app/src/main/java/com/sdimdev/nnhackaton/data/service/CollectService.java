package com.sdimdev.nnhackaton.data.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.sdimdev.nnhackaton.data.persistence.DataBaseProvider;
import com.sdimdev.nnhackaton.di.DIManager;
import com.sdimdev.nnhackaton.presentation.view.search.CoordinateCollect;

import java.util.Timer;
import java.util.TimerTask;

public class CollectService extends Service {

    private Context context;
	private DataBaseProvider dataBaseProvider;
	private volatile Location lastLocation;

	private CoordinateCollect _collector;
	private FusedLocationProviderClient fusedLocationClient;

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

		if (_collector == null) {
			_collector = new CoordinateCollect(context, dataBaseProvider);
		}

		fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
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
					startCollectingLocation();
					if (lastLocation != null) {
						_collector.setLocation(lastLocation);
					}
					_collector.startScan();
				}

			});
		}
	}

	// Location
	protected LocationRequest createLocationRequest() {
		LocationRequest locationRequest = LocationRequest.create();
		locationRequest.setInterval(10000);
		locationRequest.setFastestInterval(5000);
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		return locationRequest;
	}

	private void startCollectingLocation() {
		//1. check location is enabled
		LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
				.addLocationRequest(createLocationRequest());
		SettingsClient client = LocationServices.getSettingsClient(context);
		Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
		task.addOnSuccessListener(_locationEnabled);
		task.addOnFailureListener(_locationDisabled);
	}

	OnSuccessListener<LocationSettingsResponse> _locationEnabled = new OnSuccessListener<LocationSettingsResponse>() {
		@Override
		public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
			fusedLocationClient.getLastLocation().addOnCompleteListener(_locationListener);
		}
	};

	OnFailureListener _locationDisabled = new OnFailureListener() {
		@Override
		public void onFailure(@NonNull Exception e) {
		}
	};

	OnCompleteListener<Location> _locationListener =new OnCompleteListener<Location>() {
		@Override
		public void onComplete(@NonNull Task<Location> task) {
			// 2. collecting location
			if (task.isSuccessful() && task.getResult() != null) {
				Location location = task.getResult();
				lastLocation = location;
			} else {
			}
		}
	};
}
