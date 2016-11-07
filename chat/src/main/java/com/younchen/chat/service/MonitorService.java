package com.younchen.chat.service;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangye on 16-5-19.
 */
public class MonitorService extends Service {
    private static final String TAG = "MonitorService";
    private static final ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    private static final String CMLOCKER = "com.cmcm.locker";
    private static final String CMLUNCHER = "com.ksmobile.launcher";

    private static final String CM_LOCKER_COVER_ACTIVITY = "com.cleanmaster.ui.cover.DismissActivity";
    private static final String CM_LOCKER_MAIN_ACTIVITY="com.cleanmaster.settings.SettingsTabActivity";
    private static final String GP_PACKAGE_NAME="com.android.vending";

    private String lastTopPkgName = "";
    private String lastBeforeChangeActivityName = "";
    private ActivityManager activitymanager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        mExecutor.submit(new MonitorThread(this));
        return START_STICKY;
    }

    private static final String[] sCls = new String[]{"com.keniu.security.util.MyAlertDialog",
            "com.android.server.am.AppErrorDialog", "com.google.android.finsky.activities.MainActivity"
    };

    private static final String[] whiteList = new String[]{
            "com.ksmobile.launcher", "com.cmcm.locker"
    };

    private class MonitorThread extends Thread {
        private String LOCKER_ERROR_DIALOG_CLASS = "com.keniu.security.util.MyAlertDialog";
        private String SYSTEM_ERROR_DIALOG_CLASS = "com.android.server.am.AppErrorDialog";
        private String GOOGLE_PLAY_GP_ACTIVITY = "com.google.android.finsky.activities.MainActivity";


        private final Context mContext;
        private int count;

        public MonitorThread(Context context) {
            mContext = context;
        }

        @Override
        public void run() {
            while (true) {
                ComponentName nowApp = getTopAppPkgName(mContext);
                Log.d(TAG, "monitor package name: " + (nowApp == null ? "null" : nowApp.getPackageName()) + " className:" + (nowApp == null ? "null" : nowApp.getClassName()));

                String packageName = nowApp.getPackageName();
                String className = nowApp.getClassName();
                matched(packageName, className, count);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lastTopPkgName = packageName;
                if (!className.equals(lastBeforeChangeActivityName)) {
                    lastBeforeChangeActivityName = className;
                }

            }
        }


        private void matched(String pkgName, String className, int count) {
            try {
                if (unlockScreenOrCrash(className)) {
                    //解锁或者 崩溃时先把 locker kill掉 然后重启
                    doRestartCMLocker();
                } else if (crashIntheLocker(pkgName)) {
                    Log.d(TAG, "crash in the locker!!!");
                    doRestartCMLocker();
                }else if(className.equals(GOOGLE_PLAY_GP_ACTIVITY)){
                    Log.d(TAG, "inter the gp !!!");
                    KillGp();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void KillGp() {
        try {
            Log.d(TAG,"kill gp");
            killProcess(GP_PACKAGE_NAME);
        } catch (IOException e) {
            Log.d(TAG,"kill gp failed");
            e.printStackTrace();
        }
    }


    private boolean crashIntheLocker(String pkgName) {
        if (pkgName.equals(CMLUNCHER) && lastTopPkgName.equals(CMLOCKER)) {
            return true;
        }
        return false;
    }

    private void killProcess(String packagename) throws IOException {
        if (packagename.equals(CMLUNCHER)) return;
        Log.d(TAG, "kill process" + packagename);
        Process suProcess = Runtime.getRuntime().exec("su");
        DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
        os.writeBytes("adb shell" + "\n");
        os.flush();
        os.writeBytes("am force-stop " + packagename + "\n");
        os.flush();
        os.close();
    }

    private void doRestartCMLocker() throws Exception {
        killProcess(CMLOCKER);
        Thread.sleep(1000);
        //尝试重启
        startLocker();
    }

    private void startLocker() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String classname = "com.cleanmaster.ui.cover.MainActivity";
        String packageName = "com.cmcm.locker";
        ComponentName cn = new ComponentName(packageName, classname);
        intent.setComponent(cn);
        startActivity(intent);
    }

    private boolean unlockScreenOrCrash(String className) {
        if (!className.equals(lastBeforeChangeActivityName) && lastBeforeChangeActivityName.equals(CM_LOCKER_COVER_ACTIVITY)) {
            Log.d(TAG, "unlock screen !!!");
            return true;
        }
        return false;
    }


    private static long lastQueryTime = 0;

    public static ComponentName getTopAppPkgName(Context appContext) {

        ComponentName topAppComponentName = null;
        if (Build.VERSION.SDK_INT >= 21) {

            if (true) {
//                OpLog.toFile(TAG, "isUsageAccessEnable true");
                topAppComponentName = getMoveToFgComponent(appContext);
            }

            if (topAppComponentName == null || TextUtils.isEmpty(topAppComponentName.getPackageName())) {
                topAppComponentName = getTopAppPkgNameAbove21(appContext);
            }

            lastQueryTime = System.currentTimeMillis();

        } else {
            // topAppComponentName = getTopAppPkgNameBelow21(appContext);
        }

        if (null == topAppComponentName)
            topAppComponentName = new ComponentName("", "");

        return topAppComponentName;
    }

    private static UsageStatsManager sUsageManager = null;

    @SuppressLint("NewApi")
    private static UsageStatsManager getUsageManager(Context ctx) {
        if (sUsageManager == null) {
            synchronized (MonitorService.class) {
                if (sUsageManager == null) {
                    sUsageManager = (UsageStatsManager) ctx.getSystemService(USAGE_STATS_SERVICE);
                }
            }
        }
        return sUsageManager;
    }

    private static long sLastEventTime = -1;
    private static UsageEvents.Event sQueryEvent;
    private static final long USAGE_STATS_TIME_CHANGE = (2 * 1000) + 500;

    @SuppressLint("NewApi")
    public static ComponentName getMoveToFgComponent(Context ctx) {
        if (Build.VERSION.SDK_INT < 21) {
            return null;
        }
//        long nowTime = System.currentTimeMillis();
//        if (lastQueryTime == 0 || lastUsageTime == 0 || (nowTime - lastQueryTime) > 20000) {
//            lastUsageTime = nowTime - 5000;
//        }

        long end = System.currentTimeMillis();
        UsageStatsManager usageManager = getUsageManager(ctx);
        final long begin = (sLastEventTime == -1 || sLastEventTime >= end) ?
                (end - 60 * 1000) : sLastEventTime - 10;//这里-10的原因是要保证拿到一个【发生了移动到前台的App】，
        // 如果是准确sLastEventTime，有可能到现在为止都没有应用变化，导致取不到App

        // Add extra 2 seconds to the end since UsageStatsService use SystemClock.elaspedRealTime()
        // instead of System.currentTimeMillis() to calculate the app usage time.
        // Therefore, its time might shift from the current time we got.
        final long changedEnd = end + USAGE_STATS_TIME_CHANGE;
        final UsageEvents events = usageManager.queryEvents(begin, changedEnd);

        String pkgName = null;
        String clsName = null;

        if (sQueryEvent == null) {
            sQueryEvent = new UsageEvents.Event();
        }

        while (events.hasNextEvent()) {
            events.getNextEvent(sQueryEvent);
            if (sQueryEvent.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                pkgName = sQueryEvent.getPackageName();
                clsName = sQueryEvent.getClassName();
                sLastEventTime = sQueryEvent.getTimeStamp();
            }
        }

        ComponentName comp = null;
        if (pkgName != null && clsName != null) {
            comp = new ComponentName(pkgName, clsName);
        }

        return comp;
    }

    @SuppressLint("NewApi")
    public static boolean isUsageAccessEnable(Context context) {
        if (context == null) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 21) {
            return true;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow("android:get_usage_stats", applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressLint("NewApi")
    private static ComponentName getTopAppPkgNameAbove21(Context appContext) {
        //Android 5.0 获取 TOP APP的性能太差，不能每秒都枚举，两次枚举直接的间隔必须大于 5 秒

        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null)
            return null;

        List<ActivityManager.RunningAppProcessInfo> appList = am.getRunningAppProcesses();
        if (null == appList || appList.size() <= 0) {
            return null;
        }

        ComponentName componentName = null;

        for (ActivityManager.RunningAppProcessInfo process : appList) {
            int flags = getProcessFlag(process);
            boolean hasActivity = ((flags & FLAG_HAS_ACTIVITY) > 0);

            if (hasActivity && process.importanceReasonCode == 0 &&
                    (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)) {

                String pkgName = null;
                if (process.pkgList != null && process.pkgList.length > 0) {
                    pkgName = process.pkgList[0];
                } else {
                    pkgName = process.processName;
                }

                // Special handling for QQ, as it confuses our top app determination
                if (process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                        process.processName.equalsIgnoreCase("com.tencent.mobileqq")) {
                    continue;
                }

                componentName = new ComponentName(pkgName, "");
                break;
            }

        }
        if (componentName == null) {
            componentName = new ComponentName("", "");
        }
        return componentName;
    }


    private final static int FLAG_HAS_ACTIVITY = 1 << 2;
    private static Field mFlagsField;

    private static int getProcessFlag(ActivityManager.RunningAppProcessInfo processInfo) {
        if (null == processInfo) {
            return FLAG_HAS_ACTIVITY;
        }

        int flags = FLAG_HAS_ACTIVITY;
        try {
            if (mFlagsField == null) {
                mFlagsField = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("flags");
            }

            flags = mFlagsField.getInt(processInfo);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

        return flags;
    }

}
