package com.sdimdev.nnhackaton.data.persistence;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

public class DataBaseFactory {
    private Context context;

    public DataBaseFactory(Context context) {
        this.context = context;
    }

    public RoomMobileDataBase provideRoomMobileDataBase() {
        return Room.databaseBuilder(context, RoomMobileDataBase.class, Const.MOBILE_DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .build();
    }
}
