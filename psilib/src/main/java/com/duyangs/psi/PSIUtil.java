package com.duyangs.psi;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.annotation.NonNull;

/**
 * "Get mobile information tools" PSI
 * create by DuYang
 * e-mail:duyangs1994@gmail.com
 * update time 2018/2/2.
 */

public class PSIUtil {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    private PSIUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * init PSIUtil
     *
     * @param app application
     */
    public static void init(@NonNull final Application app) {
        PSIUtil.sApplication = app;
    }

    /**
     * get Application
     *
     * @return Application
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }


}
