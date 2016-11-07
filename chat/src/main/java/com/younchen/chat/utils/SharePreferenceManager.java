package com.younchen.chat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.younchen.chat.App;

/**
 * Created by pc on 2016/2/14.
 */
public class SharePreferenceManager {

    private final String FILE_CREATED = "file_created";

    private static SharedPreferences mSharedPreferences;

    //

    private static synchronized SharedPreferences getInstance() {

        if (mSharedPreferences == null) {
            mSharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(App.getAppContext());
        }
        return mSharedPreferences;
    }

    public boolean isCreatedFile() {
        return getBoolean(FILE_CREATED, false);
    }

    public void setCreateFile() {
        putBoolean(FILE_CREATED, true);
    }

    /**
     * 保存字符
     *
     * @return
     */
    public static void putString(String key, String value) {
        getInstance().edit().putString(key, value).commit();
    }

    /**
     * 读取字符
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return getInstance().getString(key, null);

    }

    /**
     * 保存布尔
     *
     * @return
     */
    public synchronized void putBoolean(String key, Boolean value) {
        getInstance().edit().putBoolean(key, value).commit();
    }

    /**
     * t 读取布尔
     *
     * @param key
     * @return
     */
    public synchronized boolean getBoolean(String key, boolean defValue) {
        return getInstance().getBoolean(key, defValue);
    }

    public void putLong(String key, long value) {
        getInstance().edit().putLong(key, value).commit();
    }

    public long getLong(String key) {
        return getInstance().getLong(key,
                System.currentTimeMillis());
    }

    public void putInt(String key, int value) {
        getInstance().edit().putInt(key, value).commit();
    }

    public int getInt(String key) {
        return getInstance().getInt(key, -1);
    }

}
