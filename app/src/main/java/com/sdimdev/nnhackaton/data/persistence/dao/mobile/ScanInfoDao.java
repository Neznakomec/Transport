package com.sdimdev.nnhackaton.data.persistence.dao.mobile;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.sdimdev.nnhackaton.data.persistence.entity.mobile.RawDataRecord;
import com.sdimdev.nnhackaton.data.persistence.entity.mobile.ScanInfo;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class ScanInfoDao {
    @Query("SELECT * FROM ScanInfo")
    abstract Flowable<ScanInfo> observeResult();
    @Query("SELECT * FROM ScanInfo")
    abstract Flowable<List<ScanInfo>> observeResultList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract long insert(ScanInfo toDb);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract long insert(RawDataRecord toDb);

    @Transaction
    public void insert(ScanInfo toDb, RawDataRecord o) {
        long id = insert(toDb);
        o.setId(id);
        insert(o);
    }
}
