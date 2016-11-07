package com.younchen.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

/**
 * Created by pc on 2016/4/20.
 */
public class ProcessUtil {

    private int mMyUid = -1;
    private UsageStatsManager mUsageStatsManager;
    UsageEvents.Event mTmpUsageEvent;
    UsageEvents.Event mLastAppMoveToFGEvent;
    private ComponentName mCurrentComp = null;
    long mLastEventTime = -1;
    private static final String SETTING_PKG = "com.android.settings";
    private static final long USAGE_STATS_TIME_CHANGE = (2 * 1000) + 500;
    private int mLastPermissionGrantedTimestamp = -1;
    private ActivityManager mActivityManager;

    @TargetApi(21)
    public ComponentName getLauncherTopApp(Context context) {
        long end = System.currentTimeMillis();
        if (mUsageStatsManager == null) {
            mUsageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");// Context.USAGE_STATS_SERVICE);
        }

        final long begin = (mLastEventTime == -1 || mLastEventTime >= end) ?
                (end - 60 * 1000) : mLastEventTime;
        // Add extra 2 seconds to the end since UsageStatsService use SystemClock.elaspedRealTime()
        // instead of System.currentTimeMillis() to calculate the app usage time.
        // Therefore, its time might shift from the current time we got.
        final long changedEnd = end + USAGE_STATS_TIME_CHANGE;
        final UsageEvents events = mUsageStatsManager.queryEvents(begin, changedEnd);

        if (mTmpUsageEvent == null) {
            mTmpUsageEvent = new UsageEvents.Event();
        }

        while (events.hasNextEvent()) {
            events.getNextEvent(mTmpUsageEvent);
            if (mTmpUsageEvent.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                mLastAppMoveToFGEvent = mTmpUsageEvent;
                mLastEventTime = mLastAppMoveToFGEvent.getTimeStamp();
            }
        }
        if (mLastAppMoveToFGEvent != null) {
            if (SETTING_PKG.equals(mLastAppMoveToFGEvent.getPackageName())) {
                mLastPermissionGrantedTimestamp = -1;
            }
            mCurrentComp = new ComponentName(mLastAppMoveToFGEvent.getPackageName(), mLastAppMoveToFGEvent.getClassName());
        }

        return mCurrentComp;
    }

    public ComponentName queryTopComponent(Context context) {
        List<ActivityManager.RunningTaskInfo> runningTasks = mActivityManager.getRunningTasks(2);
        if (runningTasks != null && runningTasks.size() > 0) {
            return runningTasks.get(0).topActivity;
        }
        return null;
    }

}
