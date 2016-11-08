package com.younchen.chat;


import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.multidex.MultiDexApplication;
import android.widget.ImageView;

import com.android.volley.toolbox.HttpManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.younchen.chat.entity.result.LoginResult;
import com.younchen.chat.utils.DimenUtils;
import com.younchen.emoji.core.EmojiConfigure;
import com.younchen.emoji.core.EmojiLoaderManager;
import com.younchen.emoji.core.IImageLoader;
import com.younchen.emoji.listener.BitmapLoadListener;
import com.younchen.utils.FileUtil;

import java.io.File;

import io.rong.imlib.RongIMClient;


/**
 * @author longquan
 * @date 2014年11月21日
 * @description
 */
public class App extends MultiDexApplication {
    private static Context context;
    private static String token;

    //
    public static int screenWidth = -1;// 屏幕宽
    public static int screenHeigh = -1;// 屏幕高

    private static boolean isChatActivity;// 是否是聊天页
    private static boolean isMyCabinetActivity;// 是否是 我的包裹页面


    private LoginResult.LoginDate loginData;
    private static App inst;


    // private PushAgent mPushAgent;

    // ***********************************************

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        inst = this;
        context = getApplicationContext();
        FileUtil.getInstance().setAppFilePath("Moyiza");
        HttpManager.init(context);
        initImageLoader(this);
        initRong();
        initEmoji();
    }

    private void initEmoji() {
        EmojiLoaderManager.getInstance().init(new EmojiConfigure() {
            @Override
            public Context getContext() {
                return App.this.getApplicationContext();
            }

            @Override
            public IImageLoader getImageLoader() {
                return new EmojiImageLoader();
            }
        });
    }

    public static App getIns() {
        return inst;
    }

    public int getScreenWidth() {
        if (screenWidth == -1)
            screenWidth = DimenUtils.getRealWidth();
        return screenWidth;
    }

    public int getScreenHeigh() {
        if (screenHeigh == -1) {
            screenHeigh = DimenUtils.getRealHeight();
        }
        return screenHeigh;
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

    private class EmojiImageLoader implements IImageLoader {

        private Target target;

        public EmojiImageLoader() {
        }

        @Override
        public void loadImage(ImageView view, String url, int height, int width) {
            Picasso.with(getApplicationContext())
                    .load(new File(url))
                    .resize(width, height)
                    .centerCrop()
                    .into(view);
        }

        @Override
        public void loadImage(ImageView view, String url) {
            Picasso.with(getApplicationContext())
                    .load(new File(url))
                    .fit()
                    .into(view);
        }

        @Override
        public void loadBitMap(String url, final BitmapLoadListener listener) {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    listener.onBitmapLoaded(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    listener.onBitmapFailed(errorDrawable);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    listener.onPrepareLoad(placeHolderDrawable);
                }
            };
            Picasso.with(getApplicationContext()).load(new File(url)).into(target);
        }

        @Override
        public void loadBitMap(String url, int width, int height, final BitmapLoadListener listener) {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    listener.onBitmapLoaded(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    listener.onBitmapFailed(errorDrawable);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    listener.onPrepareLoad(placeHolderDrawable);
                }
            };
            Picasso.with(getApplicationContext()).load(new File(url)).resize(width, height).centerCrop().into(target);
        }

        @Override
        public void cancelLoading() {
            if (target != null) {
                Picasso.with(getApplicationContext()).cancelRequest(target);
            }
        }

    }
}


