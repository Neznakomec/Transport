package com.sdimdev.nnhackaton.data.persistence.entity.mobile

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "RawData")
class RawDataRecord(
        @ColumnInfo(name = "Id")
        var id: Long = 0,
        @ColumnInfo(name = "RawJson")
        var json: String?)