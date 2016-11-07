package com.younchen.chat.thread;

import java.io.Serializable;
import java.util.concurrent.Future;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


/**
 * 带handler 的Runnable
 *
 * @author longquan
 * @date 2015年3月31日
 * @description
 */
public class HandlerRunnable implements Runnable {

    public static interface Listenner<T extends Serializable> {// 异步任务接口

        public T doInBackground();// 后台执行线程

        public void onPostExecute(T result);// ui线程上执行
    }

    // *******************************************
    private Listenner listenner;
    public Future future;

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bun = msg.getData();
            listenner.onPostExecute(bun.getSerializable("serializable"));
        }
    };

    public HandlerRunnable(Listenner listenner) {
        this.listenner = listenner;
    }


    public void cancel() {
        if (future != null)
            future.cancel(true);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        Serializable ser = listenner.doInBackground();
        Message msg = Message.obtain();
        Bundle bun = new Bundle();
        bun.putSerializable("serializable", ser);
        msg.setData(bun);
        handler.sendMessage(msg);
    }

}
