package com.duyangs.psi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * "" PSI
 * create by DuYang
 * e-mail:duyangs1994@gmail.com
 * update time 2018/2/2.
 */

public class SystemIUtils {


    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }


    /**
     * get phone model
     *
     * @return phone model
     */
    public static String getPModel() {
        return android.os.Build.MODEL;
    }

    /**
     * get phone sdk int
     *
     * @return sdk int
     */
    public static int getPSDKInt() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * get system(Android) release
     *
     * @return release
     */
    public static String getSystemRelease() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 判断设备是否是手机
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPhone() {
        TelephonyManager tm =
                (TelephonyManager) PSIUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取 IMEI 码
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return IMEI 码
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMEI() {
        TelephonyManager tm =
                (TelephonyManager) PSIUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getDeviceId() : null;
    }

    /**
     * 获取 MEID 码
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return IMEI 码
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getMEID() {
        TelephonyManager tm =
                (TelephonyManager) PSIUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getDeviceId() : null;
    }

    /**
     * 获取 MNC 码
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return MNC 码
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getMNC() {
        TelephonyManager tm =
                (TelephonyManager) PSIUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return null;
        }
        String networkOperator = tm.getNetworkOperator();
        if (!TextUtils.isEmpty(networkOperator)) {
            return networkOperator.substring(0, 3);
        }
        return null;
    }

    /**
     * 获取 MCC 码
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return MCC 码
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getMCC() {
        TelephonyManager tm =
                (TelephonyManager) PSIUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return null;
        }
        String networkOperator = tm.getNetworkOperator();
        if (!TextUtils.isEmpty(networkOperator)) {
            return networkOperator.substring(3);
        }
        return null;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return List<InetAddress>
     */
    public static List<InetAddress> getLocalIPList() {

        List<InetAddress> ipList = new ArrayList<>();
        try {
            //列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {//是否还有元素
                NetworkInterface ni = en_netInterface.nextElement();//得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();//得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ipList.add(en_ip.nextElement());
//                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
//                        break;
//                    else
//                        ip = null;
                }

//                if (ip != null) {
//                    break;
//                }
            }
        } catch (SocketException e) {

            e.printStackTrace();
        }
        return ipList;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return Inet Address
     */
    public static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            //列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {//是否还有元素
                NetworkInterface ni = en_netInterface.nextElement();//得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();//得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && !ip.getHostAddress().contains(":"))
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {

            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 获取本地IP
     *
     * @return Ip Address
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 根据IP地址获取MAC地址
     *
     * @return Mac Address
     */
    public static String getMacAddress() {
        String strMacAddr = null;
        try {
            //获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception ignored) {

        }

        return strMacAddr;
    }

    /**
     * 获取设备HardwareAddress地址
     *
     * @return Hardware Address
     */
    public static String getMachineHardwareAddress() {
        Enumeration<NetworkInterface> interfaces;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
            String hardWareAddress = null;
            NetworkInterface iF;
            while (interfaces.hasMoreElements()) {
                iF = interfaces.nextElement();
                try {
                    hardWareAddress = bytesToString(iF.getHardwareAddress());
                    if (hardWareAddress != null)
                        break;
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
            return hardWareAddress;
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * get MAC Address
     *
     * @return MAC Address
     */
    public static String getMac() {
        if (getMacAddress() != null) {
            return getMacAddress();
        } else if (getMachineHardwareAddress() != null) {
            return getMachineHardwareAddress();
        } else {
            return null;
        }
    }

    /***
     * byte转为String
     * @return String
     */
    private static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    /**
     * 获取 AndroidId
     *
     * @return String
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidId() {
        return Settings.Secure.getString(PSIUtil.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取内核版本信息
     *
     * @return Linux Kernal Info
     */
    public static String getLinuxKernalInfoEx() {
        StringBuilder result = new StringBuilder();
        String line;
        String[] cmd = new String[]{"/system/bin/cat", "/proc/version"};
        String workdirectory = "/system/bin/";
        try {
            ProcessBuilder bulider = new ProcessBuilder(cmd);
            bulider.directory(new File(workdirectory));
            bulider.redirectErrorStream(true);
            Process process = bulider.start();
            InputStream in = process.getInputStream();
            InputStreamReader isrout = new InputStreamReader(in);
            BufferedReader brout = new BufferedReader(isrout, 8 * 1024);

            while ((line = brout.readLine()) != null) {
                result.append(line);
                // result += "\n";
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "----Linux Kernal is :" + result);
        return result.toString();
    }

    /**
     * 判断是否开启GPS
     * @return boolean isOpenGPS
     */
    static boolean isOpenGPS() {
        LocationManager locationManager
                = (LocationManager) PSIUtil.getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
