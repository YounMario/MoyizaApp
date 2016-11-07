package com.younchen.emoji.core;

import android.view.View;
import android.widget.ImageView;

import com.younchen.emoji.listener.BitmapLoadListener;

/**
 * Created by mryou on 2016/7/26.
 */

public interface IImageLoader {

    public void loadImage(ImageView view, String url, int height, int width);

    public void loadImage(ImageView view, String url);

    public void loadBitMap(String url, BitmapLoadListener listener);

    public void loadBitMap(String url, int width, int height, BitmapLoadListener listener);

    public void cancelLoading();

}
