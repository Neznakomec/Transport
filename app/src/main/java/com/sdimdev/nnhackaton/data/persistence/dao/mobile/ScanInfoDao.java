package com.sdimdev.nnhackaton.data.persistence.dao.mobile;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sdimdev.nnhackaton.data.persistence.entity.mobile.ScanInfo;

import io.reactivex.Flowable;

@Dao
public interface ScanInfoDao {
    @Query("SELECT * FROM ScanInfo")
    Flowable<ScanInfo> observeResult();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ScanInfo toDb);
}
