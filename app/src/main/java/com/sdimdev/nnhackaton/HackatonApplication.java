package com.sdimdev.nnhackaton;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class HackatonApplication extends Application {
    public static HackatonApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
