package com.younchen.chat.utils;

import android.app.Application;

import com.younchen.chat.App;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * Created by pc on 2016/3/29.
 */
public class IconUtils {

    private static WeakHashMap<Integer, String> cache = new WeakHashMap<>();


    public static synchronized String iconText(int resId) {
        if (cache.containsKey(resId)) return cache.get(resId);
        String iconText = App.getIns().getResources().getString(resId);
        if (iconText != null) {
            cache.put(resId, iconText);
        }
        return iconText;
    }

}
