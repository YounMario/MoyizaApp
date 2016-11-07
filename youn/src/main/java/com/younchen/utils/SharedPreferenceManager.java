package com.younchen.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * sharedPreferenceManager
 *
 * @author longquan 2015
 */
public class SharedPreferenceManager {
    // **

    private static SharedPreferences mSharedPreferences;

    //
    private static Context m_context;

    private static synchronized SharedPreferences getPreferneces(Context context) {

        if (context == null) {
            m_context = context;
        }

        if (mSharedPreferences == null) {
            mSharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(m_context);
        }
        return mSharedPreferences;
    }

    // ************************************************************


    // 保存用户的token
    public static void saveUserToken(String userId, String token) {
        putString(userId, token);
    }

    // 获取用户的token
    public static String getUserToken(String userId) {
        return getString(userId);
    }

    // 获取用户未读消息
    public static int getUnreadMsg(String key) {
        return getInt(key);
    }

    // *****************************

    /**
     * 保存字符
     *
     * @return
     */
    public static void putString(String key, String value) {
        getPreferneces(m_context).edit().putString(key, value).commit();
    }

    /**
     * 读取字符
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return getPreferneces(m_context).getString(key, null);

    }

    /**
     * 保存布尔
     *
     * @return
     */
    public static void putBoolean(String key, Boolean value) {
        getPreferneces(m_context).edit().putBoolean(key, value).commit();
    }

    /**
     * key 用户昵称+ 用户id
     */
    public static void putUnreadCount(String key, Integer count) {
        getPreferneces(m_context).edit().putInt(key, count).commit();
    }

    /**
     * t 读取布尔
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return getPreferneces(m_context).getBoolean(key, defValue);

    }

    public static void putLong(String key, long value) {
        getPreferneces(m_context).edit().putLong(key, value).commit();
    }

    public static long getLong(String key) {
        return getPreferneces(m_context).getLong(key,
                System.currentTimeMillis());
    }

    public static void putInt(String key, int value) {
        getPreferneces(m_context).edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return getPreferneces(m_context).getInt(key, -1);
    }

}
