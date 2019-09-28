package com.sdimdev.nnhackaton;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import io.reactivex.plugins.RxJavaPlugins;

public class HackatonApplication extends Application {
    private static final String DEBUG_PATH = "hackaton.db";
    public static HackatonApplication app;

    @Override
    public void onCreate() {
        FragmentManager.enableDebugLogging(true);
        super.onCreate();
        app = this;
        RxJavaPlugins.setErrorHandler(throwable -> Log.e("ERROR", "NOT IMPLEMENTED ERROR", throwable));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
