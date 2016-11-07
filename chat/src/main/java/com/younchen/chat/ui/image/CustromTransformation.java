package com.younchen.chat.ui.image;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;
import com.younchen.chat.App;
import com.younchen.chat.utils.YounLog;

import java.io.ByteArrayOutputStream;

/**
 * Created by longquan on 2016/4/26.
 */
public class CustromTransformation implements Transformation {

    private final int MAX_WIDTH = 720;
    private final int MAX_HEIGHT = 720;

    private String key;

    public CustromTransformation(String key) {
        this.key = key;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int targetWidth, targetHeight;
        double aspectRatio;

        if (source.getWidth() > source.getHeight()) {
            targetWidth = MAX_WIDTH;
            aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            targetHeight = (int) (targetWidth * aspectRatio);
        } else {
            targetHeight = MAX_HEIGHT;
            aspectRatio = (double) source.getWidth() / (double) source.getHeight();
            targetWidth = (int) (targetHeight * aspectRatio);
        }

        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
        tryCompress(result);
        if (result != source) {
            source.recycle();
        }
        return result;
    }

    private void tryCompress(Bitmap result) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            result.compress(Bitmap.CompressFormat.JPEG, 60, baos);
            baos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String key() {
        return key;
    }
}
