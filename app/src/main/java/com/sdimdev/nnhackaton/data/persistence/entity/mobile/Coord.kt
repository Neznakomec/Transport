package com.sdimdev.nnhackaton.data.persistence.entity.mobile

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "ScanInfo")
class ScanInfo(
        @ColumnInfo(name = "date")
        val date: Long? = null,
        @ColumnInfo(name = "operator")
        val operator: String,
        @ColumnInfo(name = "SignalStrength")
        val strength: String?,
        @ColumnInfo(name = "NetworkType")
        val networkType: String?,
        @ColumnInfo(name = "Latitude")
        var lat: Double? = null,
        @ColumnInfo(name = "Longidute")
        var lon: Double? = null,
        @ColumnInfo(name = "MobileId")
        val mobileId: String? = null) {

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "Id")
        lateinit var id: Int
}