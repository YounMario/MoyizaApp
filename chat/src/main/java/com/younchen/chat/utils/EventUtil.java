package com.younchen.chat.utils;

import android.view.MotionEvent;

/**
 * Created by Administrator on 2015/11/23.
 */
public class EventUtil {

    public static String ation(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            return "ACTION_DOWN";
        } else if (me.getAction() == MotionEvent.ACTION_MOVE) {
            return "ACTION_MOVE";
        } else if (me.getAction() == MotionEvent.ACTION_UP) {
            return "ACTION_UP";
        } else {
            return "";
        }

    }
}
