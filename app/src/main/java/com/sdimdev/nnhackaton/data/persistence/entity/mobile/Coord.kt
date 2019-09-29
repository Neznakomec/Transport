package com.sdimdev.nnhackaton.data.persistence.entity.mobile

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ScanInfo")
class ScanInfo(
        @ColumnInfo(name = "date")
        var date: Long? = null,
        @ColumnInfo(name = "operator")
        var operator: String,
        @ColumnInfo(name = "SignalStrength")
        @SerializedName("Level")
        var strength: String?,
        @ColumnInfo(name = "NetworkType")
        var networkType: String?,
        @ColumnInfo(name = "Latitude")
        @SerializedName("Lat")
        var lat: Double? = null,
        @ColumnInfo(name = "Longidute")
        @SerializedName("Lng")
        var lon: Double? = null,
        @ColumnInfo(name = "MobileId")
        val mobileId: String? = null,
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "Id")
        var id: Long = 0)