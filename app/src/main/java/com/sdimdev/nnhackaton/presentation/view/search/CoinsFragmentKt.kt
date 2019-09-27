package com.sdimdev.nnhackaton.presentation.view.search

import android.content.Context
import android.telephony.CellInfoGsm
import android.telephony.CellInfoLte
import android.telephony.CellInfoWcdma
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import com.sdimdev.nnhackaton.data.persistence.entity.mobile.ScanInfo
import java.util.*

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
                            name, strength, type)
                }
            }

            scanInfo.let {
                Toast.makeText(context, scanInfo.toString(), Toast.LENGTH_SHORT)
                Log.d(TAG, scanInfo.toString())
            }

        }

    }
}