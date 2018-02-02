package com.duyangs.psi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * "get wifi info" PSI
 * create by DuYang
 * e-mail:duyangs1994@gmail.com
 * update time 2018/2/2.
 */

public class WifiIUtils {

    /**
     *<p>需添加权限
     * {@code <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
     *  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     * @return
     */
    @SuppressLint("MissingPermission")
    public static int getWIFIAddress(){
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) PSIUtil.getApp().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo != null ? wifiInfo.getIpAddress() : 0;
    }

}
