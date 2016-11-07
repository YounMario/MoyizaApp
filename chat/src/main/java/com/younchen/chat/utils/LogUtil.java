package com.younchen.chat.utils;

import android.util.Log;

/**
 * Created by Administrator on 2015/11/23.
 */
public class LogUtil {

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
}
