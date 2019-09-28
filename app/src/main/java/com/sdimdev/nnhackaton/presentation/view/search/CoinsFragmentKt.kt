package com.sdimdev.nnhackaton.presentation.view.search

import android.content.Context
import android.telephony.CellInfoGsm
import android.telephony.CellInfoLte
import android.telephony.CellInfoWcdma
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import com.sdimdev.nnhackaton.HackatonApplication
import com.sdimdev.nnhackaton.data.persistence.entity.mobile.ScanInfo
import org.json.JSONObject
import java.util.*
import android.R.attr.name
import android.R.id
import com.sdimdev.nnhackaton.data.persistence.dao.mobile.ScanInfoDao
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class CoinsFragmentKt(private val fragment: CoinsFragment) {
    val TAG: String = "OPERATORS";
    private val context: Context? = fragment.context;

    fun startScan() {
        val telephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var strength: String? = null
        var operator: String? = null
        val cellInfos = telephonyManager.allCellInfo
        val name = telephonyManager.networkOperatorName
        val sb = StringBuffer()
        var type: String? = null
        var scanInfo: ScanInfo? = null
        Log.d(TAG, Objects.toString(cellInfos))
        if (cellInfos != null) {
            for (i in cellInfos.indices) {
                if (cellInfos[i].isRegistered) {
                    if (cellInfos[i] is CellInfoWcdma) {
                        val cellInfoWcdma = cellInfos[i] as CellInfoWcdma
                        val cellSignalStrengthWcdma = cellInfoWcdma.cellSignalStrength
                        type = "Wcdma"
                        strength = cellSignalStrengthWcdma.dbm.toString()
                    } else if (cellInfos[i] is CellInfoGsm) {
                        val cellInfogsm = cellInfos[i] as CellInfoGsm
                        val cellSignalStrengthGsm = cellInfogsm.cellSignalStrength
                        type = "Gsm"
                        strength = cellSignalStrengthGsm.dbm.toString()
                    } else if (cellInfos[i] is CellInfoLte) {
                        val cellInfoLte = cellInfos[i] as CellInfoLte
                        val cellSignalStrengthLte = cellInfoLte.cellSignalStrength
                        type = "Lte"
                        strength = cellSignalStrengthLte.dbm.toString()
                    }
                    scanInfo = ScanInfo(Date().time,
                            name, strength, type, mobileId = telephonyManager.imei)
                }
            }

            scanInfo?.lat = Random().nextDouble() * 100.0;
            scanInfo?.lon = Random().nextDouble() * 100.0;
            scanInfo?.let {
                Toast.makeText(context, scanInfo.toString(), Toast.LENGTH_SHORT)
                Log.d(TAG, scanInfo.toString())
                sendScanInfo(it)
            }
        }
    }

    fun sendScanInfo(scanInfo: ScanInfo) {
        val json = JSONObject()
        val point = JSONObject()
        point.put("lat", scanInfo.lat)
        point.put("lon", scanInfo.lon)
        json.put("point", point)
        json.put("date", scanInfo.date)
        json.put("operator", scanInfo.operator)
        json.put("level", Integer.valueOf(scanInfo.strength))
        json.put("typeConnection", scanInfo.networkType)
        json.put("mobile", scanInfo.mobileId)

        // save to db
        val db = HackatonApplication.app.getDatabase()
        val scanInfoDao = db.scanInfoDao
        Completable.fromAction( {
            scanInfoDao.insert(scanInfo)
        }).subscribeOn(Schedulers.io())
                .subscribe();


    }

}