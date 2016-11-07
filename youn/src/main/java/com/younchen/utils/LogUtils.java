package com.younchen.utils;

import android.util.Log;

/**
 * Created by Administrator on 2015/10/1.
 */
public class LogUtils {

    private static String CLASS_NAME = LogUtils.class.getName();

    public static void e(String e) {
        Log.e(CLASS_NAME, e);
    }

    public static void i(String i) {
        Log.i(CLASS_NAME, i);
    }

    public static void d(String d) {
        Log.d(CLASS_NAME, d);
    }

    public static void v(String v) {
        Log.e(CLASS_NAME, v);
    }
}
