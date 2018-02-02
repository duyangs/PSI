package com.duyangs.example.psi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.duyangs.psi.SystemIUtils;
import com.duyangs.psi.WifiIUtils;

import java.lang.ref.PhantomReference;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PHONE_STATE = 1001;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    private void show() {
        textView = findViewById(R.id.text);
        textView.setText("SYSTEM INFO \n"
                + "PHONE MODEL: " + SystemIUtils.getPModel() + "\n"
                + "PHONE SDK INT: " + SystemIUtils.getPSDKInt() + "\n"
                + "SYSTEM RELEASE: " + SystemIUtils.getSystemRelease() + "\n"
                + "IS PHONE: " + SystemIUtils.isPhone() + "\n"
                + "IMEI: " + SystemIUtils.getIMEI() + "\n"
                + "MEID: " + SystemIUtils.getMEID() + "\n"
                + "MNC: " + SystemIUtils.getMNC() + "\n"
                + "MCC: " + SystemIUtils.getMCC() + "\n"
                + "IP01: " + SystemIUtils.getLocalInetAddress() + "\n"
                + "IP02: " + SystemIUtils.getLocalIpAddress() + "\n"
                + "MAC BY IP: " + SystemIUtils.getMacAddress() + "\n"
                + "MAC BY Hardware: " + SystemIUtils.getMachineHardwareAddress() + "\n"
                + "MAC: " + SystemIUtils.getMac() + "\n"
                + "\n"
                + "WIFI INFO \n"
                + "WIFI ADDRESS: " + WifiIUtils.getWIFIAddress() + "\n"
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.CHANGE_WIFI_STATE,
                            Manifest.permission.ACCESS_WIFI_STATE},
                    REQUEST_PHONE_STATE);
        } else {
            show();
        }
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
