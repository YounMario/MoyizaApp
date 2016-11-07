package com.younchen.emoji.listener;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by 龙泉 on 2016/7/27.
 */
public interface BitmapLoadListener {

    void onBitmapLoaded(Bitmap bitmap);

    void onBitmapFailed(Drawable errorDrawable);

    void onPrepareLoad(Drawable placeHolderDrawable);

}
