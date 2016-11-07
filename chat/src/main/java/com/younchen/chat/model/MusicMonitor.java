package com.younchen.chat.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.younchen.chat.utils.ToastUtil;
import com.younchen.chat.utils.YounLog;

/**
 * Created by longquan on 2016/5/9.
 */
public class MusicMonitor {

    private Context context;

    public MusicMonitor(Context context) {
        this.context = context;
        initRciver();
    }

    public void onDestory() {
        try {
            context.unregisterReceiver(mReceiver);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void initRciver() {
        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");

        iF.addAction("com.htc.music.metachanged");

        iF.addAction("fm.last.android.metachanged");
        iF.addAction("com.sec.android.app.music.metachanged");
        iF.addAction("com.nullsoft.winamp.metachanged");
        iF.addAction("com.amazon.mp3.metachanged");
        iF.addAction("com.miui.player.metachanged");
        iF.addAction("com.real.IMP.metachanged");
        iF.addAction("com.sonyericsson.music.metachanged");
        iF.addAction("com.rdio.android.metachanged");
        iF.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
        iF.addAction("com.andrew.apollo.metachanged");

        context.registerReceiver(mReceiver, iF);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String cmd = intent.getStringExtra("command");
            String artist = intent.getStringExtra("artist");
            String album = intent.getStringExtra("album");
            String track = intent.getStringExtra("track");
            StringBuffer sb = new StringBuffer();
            sb.append("command:").append(cmd).append(" artist:").append(artist).append(" ablum").append(album).append(" track").append(track);
            YounLog.i("musicTest",sb.toString());
        }
    };
}
