package com.sdimdev.nnhackaton.data.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sdimdev.nnhackaton.data.persistence.dao.mobile.ScanInfoDao;
import com.sdimdev.nnhackaton.data.persistence.entity.mobile.RawDataRecord;
import com.sdimdev.nnhackaton.data.persistence.entity.mobile.ScanInfo;

@Database(entities = {ScanInfo.class, RawDataRecord.class}, version = Const.MOBILE_DB_VERSION)
public abstract class RoomMobileDataBase extends RoomDatabase {
    public abstract ScanInfoDao getScanInfoDao();
}