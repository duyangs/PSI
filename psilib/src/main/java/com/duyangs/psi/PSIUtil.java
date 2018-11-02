package com.duyangs.psi;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * "Get mobile information tools" PSI
 * create by DuYang
 * e-mail:duyangs1994@gmail.com
 * update time 2018/2/2.
 */

public class PSIUtil {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private PSIUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * init PSIUtil
     *
     * @param context Context
     */
    public static void init(@NonNull final Context context) {
        PSIUtil.mContext = context;
    }

    /**
     * get Application
     *
     * @return Application
     */
    public static Context getContext() {
        if (mContext != null) return mContext;
        throw new NullPointerException("u should init first");
    }


}
