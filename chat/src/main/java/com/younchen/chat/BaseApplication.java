package com.younchen.chat;


import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.android.volley.toolbox.HttpManager;
import com.younchen.chat.entity.result.LoginResult;
import com.younchen.utils.FileUtil;

import io.rong.imlib.RongIMClient;


/**
 * @author longquan
 * @date 2014年11月21日
 * @description
 */
public class BaseApplication extends Application {
    private static String TAG = "BaseApplication";
    private static Context context;
    private static String token;

    //
    public static int screenWidth;// 屏幕宽
    public static int screenHeigh;// 屏幕高

    private static boolean isChatActivity;// 是否是聊天页
    private static boolean isMyCabinetActivity;// 是否是 我的包裹页面


    private final String PAKAGE_NAME = "com.hlb.allcabinet";
    private LoginResult.LoginDate loginData;

    // private PushAgent mPushAgent;

    // ***********************************************

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        context = getApplicationContext();
        FileUtil.getInstance().setAppFilePath("Moyiza");
        HttpManager.init(context);
        initImageLoader(this);
        initRong();
        initEmoji();

    }

    private void initEmoji() {

    }


    /**
     * 获取context
     *
     * @return
     */
    public static Context getAppContext() {
        return context;
    }

    public void initRong() {
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIMClient.init(this);
        }
    }

    /**
     * 初始化imgLoader
     *
     * @param context
     */
    public static void initImageLoader(Context context) {


        /**
         * 设置缓存路径。 discCache(new UnlimitedDiscCache(file, new
         * Md5FileNameGenerator()))
         */

        // Initialize ImageLoader with configuration.
    }

    public void setLoginDate(LoginResult.LoginDate data) {
        this.loginData = data;
    }

    public LoginResult.LoginDate getLoginDate() {
        return this.loginData;
    }


    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}


