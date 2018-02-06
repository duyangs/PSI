package com.duyangs.psi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * "get wifi info" PSI
 * create by DuYang
 * e-mail:duyangs1994@gmail.com
 * update time 2018/2/2.
 */

public class WifiIUtils {

    /**
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @return WIFI Address
     */
    @SuppressLint("MissingPermission")
    public static int getWIFIAddress() {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) PSIUtil.getApp().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (wifiManager == null){
            return 0;
        }
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo != null ? wifiInfo.getIpAddress() : 0;
    }

    /**
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
     * <uses-permission android:name="android.permission.WRITE_SECURE_SETTINGS"/>
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static List<ScanResult> getWIFIList() {

        if (Build.VERSION.SDK_INT >= 23 && !SystemIUtils.isOpenGPS()) {
            Settings.Secure.putInt(PSIUtil.getApp().getContentResolver(), Settings.Secure.LOCATION_MODE, 1);
        }

        List<ScanResult> wifiList;
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) PSIUtil.getApp().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null){
            return new ArrayList<>();
        }
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        wifiList = wifiManager.getScanResults();
        return wifiList != null ? wifiList : new ArrayList<ScanResult>();
    }

}
