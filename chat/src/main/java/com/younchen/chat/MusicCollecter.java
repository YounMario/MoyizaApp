package com.younchen.chat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.util.Log;

import com.younchen.chat.utils.ToastUtil;

/**
 * Created by pc on 2016/3/31.
 */
public class MusicCollecter {

    private static MusicCollecter musicCollecter;
    private Context context;
    private AlarmManager am;
    private TaskReciver taskReciver;
    private String action = "runing upload date";

    public MusicCollecter() {
        context = App.getIns().getApplicationContext();
        initTask();
    }

    private void initTask() {
        Log.i("longquan", "init task");
        setAlam();
    }


    private void setAlam() {
        Intent intent = new Intent(action);
        IntentFilter intentFilter = new IntentFilter(action);
        taskReciver = new TaskReciver();
        PendingIntent sender = PendingIntent.getBroadcast(context,
                0, intent, 0);

        long firstTime = SystemClock.elapsedRealtime();

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                firstTime, 5 * 1000, sender);
        context.registerReceiver(taskReciver, intentFilter);
    }

    public static class TaskReciver
            extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("longquan", "running");
        }
    }
}
