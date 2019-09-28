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

    private static final String DEBUG_PATH = "/storage/emulated/0/folder/database-name.db3";
    private static final String RELEASE_PATH_DB_NAME = "database_name.db3";
    @Override
    public void onCreate() {
        FragmentManager.enableDebugLogging(true);
        super.onCreate();
        app = this;

        String dbName = BuildConfig.DEBUG ? DEBUG_PATH : RELEASE_PATH_DB_NAME;
        database = Room.databaseBuilder(this, RoomMobileDataBase.class, dbName)
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
