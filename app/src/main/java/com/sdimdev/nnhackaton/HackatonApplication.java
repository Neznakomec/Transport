package com.sdimdev.nnhackaton;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentManager;

import com.sdimdev.nnhackaton.data.persistence.RoomMobileDataBase;

public class HackatonApplication extends Application {
    public static HackatonApplication app;

    private RoomMobileDataBase database;

    @Override
    public void onCreate() {
        FragmentManager.enableDebugLogging(true);
        super.onCreate();
        app = this;

        database = Room.databaseBuilder(this, RoomMobileDataBase.class, "database")
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public HackatonApplication getInstance() {
        return app;
    }

    public RoomMobileDataBase getDatabase() {
        return database;
    }
}
