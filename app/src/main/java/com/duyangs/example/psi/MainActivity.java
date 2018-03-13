package com.duyangs.example.psi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.duyangs.psi.LocationIUtils;
import com.duyangs.psi.LocationIUtils2;
import com.duyangs.psi.SDCardIUtils;
import com.duyangs.psi.SystemIUtils;
import com.duyangs.psi.WifiIUtils;


public class MainActivity extends AppCompatActivity {

    //权限请求码
    private static final int PERMISSION_REQUEST_CODE = 0;
    //危险权限需要动态申请
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_WIFI_STATE};

    private static final int REQUEST_PHONE_STATE = 1001;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    private void show() {
        textView = findViewById(R.id.text);
        String stringBuilder = "SYSTEM INFO \n" +
                "PHONE MODEL: " + SystemIUtils.getPModel() + "\n" +
                "PHONE SDK INT: " + SystemIUtils.getPSDKInt() + "\n" +
                "SYSTEM RELEASE: " + SystemIUtils.getSystemRelease() + "\n" +
                "IS PHONE: " + SystemIUtils.isPhone() + "\n" +
                "IMEI: " + SystemIUtils.getIMEI() + "\n" +
                "MEID: " + SystemIUtils.getMEID() + "\n" +
                "MNC: " + SystemIUtils.getMNC() + "\n" +
                "MCC: " + SystemIUtils.getMCC() + "\n" +
                "IP01: " + SystemIUtils.getLocalInetAddress() + "\n" +
                "IPLIST: " + SystemIUtils.getLocalIPList() + "\n" +
                "IP02: " + SystemIUtils.getLocalIpAddress() + "\n" +
                "MAC BY IP: " + SystemIUtils.getMacAddress() + "\n" +
                "MAC BY Hardware: " + SystemIUtils.getMachineHardwareAddress() + "\n" +
                "MAC: " + SystemIUtils.getMac() + "\n" +
                "AndroidId: " + SystemIUtils.getAndroidId() + "\n" +
                "kernelVersion: " + SystemIUtils.getLinuxKernalInfoEx() + "\n" +
                "\n" + "WIFI INFO \n" +
                "WIFI ADDRESS: " + WifiIUtils.getWIFIAddress() + "\n" +
                "WIFI LIST: " + WifiIUtils.getWIFIList() + "\n" +
                "\n" + "SD CARD INFO\n" +
                "IMSI: " + SDCardIUtils.getIMSI() + "\n" +
                "ICCID: " + SDCardIUtils.getICCID() + "\n" +
                "PHONE TYPE: " + SDCardIUtils.getPhoneType() + "\n" +
                "IS SIM CARD READY: " + SDCardIUtils.isSimCardReady() + "\n" +
                "SIM OPERATOR NAME: " + SDCardIUtils.getSimOperatorName() + "\n" +
                "SIM OPERATOR BY MAC: " + SDCardIUtils.getSimOperatorByMnc() + "\n" +
                "CALL ID: " + SDCardIUtils.getCALLID() + "\n" +
                "\n" + "Location Info\n" +
                "GPS: " + LocationIUtils.getGPSLocation() + "\n" +
                "NetWork: " + LocationIUtils.getNetWorkLocation() + "\n" +
                "Best: " + LocationIUtils.getBestLocation(this,new Criteria()) + "\n" +
                "lllll:" + LocationIUtils2.getInstance(this).getLocation();
        textView.setText(stringBuilder);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkPermission()) {  //未获取权限，申请权限
            requestPermission();
        } else {  //已经获取权限
            show();
        }
    }


    /**
     * 检查是否已经授予权限
     *
     * @return boolean permission
     */
    private boolean checkPermission() {
        for (String permission : NEEDED_PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                NEEDED_PERMISSIONS, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //判断请求码，确定当前申请的权限
        if (requestCode == REQUEST_PHONE_STATE) {
            //判断权限是否申请通过
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                show();
            } else {
                Toast.makeText(this, "相关权限未通过，请去设置中允许相关权限", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
