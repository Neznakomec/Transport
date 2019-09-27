package com.sdimdev.nnhackaton.data.persistence;

import android.content.Context;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;

public class DataBaseProvider {
    private DataBaseFactory dataBaseFactory;
    private Context ctx;

    private RoomMobileDataBase roomMobileDataBase;
    private Relay<Boolean> databaseLoadRelay = BehaviorRelay.createDefault(true);

    public DataBaseProvider(DataBaseFactory dataBaseFactory, Context ctx) {
        this.ctx = ctx;
        this.dataBaseFactory = dataBaseFactory;
        init();
    }

    public void init() {
        roomMobileDataBase = dataBaseFactory.provideRoomMobileDataBase();
        getRoomMobileDataBase();
        databaseLoadRelay.accept(true);
    }

    public RoomMobileDataBase getRoomMobileDataBase() {
        if (roomMobileDataBase == null || !roomMobileDataBase.isOpen())
            roomMobileDataBase = dataBaseFactory.provideRoomMobileDataBase();
        return roomMobileDataBase;
    }

    public Observable<Boolean> observeDatabaseChanged() {
        return databaseLoadRelay;
    }

    public void disconnect() {
        if (roomMobileDataBase != null) {
            roomMobileDataBase.close();
            roomMobileDataBase = null;
        }
    }

    public void recreate() {
        removeBase();
        init();
        getRoomMobileDataBase();
    }

    public void removeBase() {
        disconnect();
        if (ctx.getDatabasePath(Const.MOBILE_DB_NAME).exists())
            ctx.deleteDatabase(Const.MOBILE_DB_NAME);
        if (ctx.getDatabasePath(Const.MOBILE_DB_NAME).exists())
            ctx.getDatabasePath(Const.MOBILE_DB_NAME).delete();
    }
}
