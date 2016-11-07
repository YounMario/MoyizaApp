package com.younchen.chat.utils;

import android.util.Log;

import com.younchen.chat.BuildConfigure;

/**
 * Created by Administrator on 2015/11/23.
 */
public class YounLog {

    private static String tag = "MOYIZA";

    public static void e(String content) {
        Log.e(tag, content);
    }

    public static void i(String content) {
        Log.i(tag, content);
    }

    public static void d(String content) {
        Log.d(tag, content);
    }

    public static void i(String Tag, String msg) {
        if (BuildConfigure.DEBUG) {
            Log.i(Tag, msg);
        }
    }
}
