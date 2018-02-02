package com.duyangs.example.psi;

import android.app.Application;

import com.duyangs.psi.PSIUtil;


/**
 * "" PSI
 * create by DuYang
 * e-mail:duyangs1994@gmail.com
 * update time 2018/2/2.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PSIUtil.init(this);
    }
}
