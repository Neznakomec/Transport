package com.sdimdev.nnhackaton.presentation.view.search

import android.content.Context
import android.telephony.*
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sdimdev.nnhackaton.data.persistence.DataBaseProvider
import com.sdimdev.nnhackaton.data.persistence.entity.mobile.RawDataRecord
import com.sdimdev.nnhackaton.data.persistence.entity.mobile.ScanInfo
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.util.*


class CoinsFragmentKt(private val fragment: CoinsFragment, private val dataBaseProvider: DataBaseProvider) {
    val TAG: String = "OPERATORS";
    private val context: Context? = fragment.context;

    fun startScan() {
        val telephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var strength: String? = null
        var operator: String? = null
        val cellInfos = telephonyManager.allCellInfo
        var name = ""
        val sb = StringBuffer()
        var type: String? = null
        var mnc: Int = 0
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
                        mnc = cellInfoWcdma.cellIdentity.mnc
                    } else if (cellInfos[i] is CellInfoGsm) {
                        val cellInfogsm = cellInfos[i] as CellInfoGsm
                        val cellSignalStrengthGsm = cellInfogsm.cellSignalStrength
                        type = "Gsm"
                        strength = cellSignalStrengthGsm.dbm.toString()
                        mnc = cellInfogsm.cellIdentity.mnc
                    } else if (cellInfos[i] is CellInfoLte) {
                        val cellInfoLte = cellInfos[i] as CellInfoLte
                        val cellSignalStrengthLte = cellInfoLte.cellSignalStrength
                        type = "Lte"
                        strength = cellSignalStrengthLte.dbm.toString()
                        mnc = cellInfoLte.cellIdentity.mnc
                    }


                    val subscriptionManager = SubscriptionManager.from(fragment.getActivity())
                    val activeSubscriptionInfoList = subscriptionManager.activeSubscriptionInfoList

                    var subscriptionInfo: SubscriptionInfo? = null
                    for (i in activeSubscriptionInfoList.indices) {
                        val temp = activeSubscriptionInfoList[i]
                        val tempMnc = temp.mnc

                        if (tempMnc == mnc) {
                            subscriptionInfo = temp
                            name = temp.carrierName as String
                        }
                    }
                    scanInfo = ScanInfo(Date().time,
                            name, strength, type, mobileId = telephonyManager.imei)

                    scanInfo?.lat = Random().nextDouble() * 100.0;
                    scanInfo?.lon = Random().nextDouble() * 100.0;

                    val gson = Gson()
                    val json = gson.toJson(subscriptionInfo)
                    val json2 = gson.toJson(cellInfos[i])

                    val jo: JsonObject = JsonObject()
                    jo.add("subscriptionInfo", gson.toJsonTree(subscriptionInfo))
                    jo.add("cellInfo", gson.toJsonTree(cellInfos[i]))
                    //val map = mutableMapOf<String, String>()
                    //map.put("subscriptionInfo", json)
                    //map.put("cellInfo", json2)


                    scanInfo?.let {
                        Toast.makeText(context, scanInfo.toString(), Toast.LENGTH_SHORT)
                        Log.d(TAG, scanInfo.toString())
                        sendScanInfo(it, RawDataRecord(id = 0, json = Gson().toJson(jo)))
                    }
                }
            }


        }
    }

    fun sendScanInfo(scanInfo: ScanInfo, raw: RawDataRecord) {
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
        val db = dataBaseProvider.roomMobileDataBase
        val scanInfoDao = db.scanInfoDao
        val disposable = Completable.fromAction({
            val id = scanInfoDao.insert(scanInfo)
            raw.id = id
            scanInfoDao.insert(raw)
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .andThen({
                    fragment.onCodeChecked(1)
                })
                .subscribe({
                    Log.d(TAG, "fine")
                }, {
                    Log.e(TAG, "error", it)
                });


    }

}