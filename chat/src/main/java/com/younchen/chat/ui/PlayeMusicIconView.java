package com.younchen.chat.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pc on 2016/1/24.
 */
public class PlayeMusicIconView extends View {

    private boolean isplaying;
    private Paint paint;
    private int[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW};

    private int h1, h2, h3;
    private boolean up1, up2, up3;
    private Rect r1, r2, r3;
    private int flameIndex;

    public PlayeMusicIconView(Context context) {
        super(context);
        init();
    }

    public PlayeMusicIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayeMusicIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    public void init() {
        flameIndex = 1;
        paint = new Paint();
        isplaying = false;
        h1 = 10;
        h2 = 20;
        h3 = 13;

        up1 = true;
        up2 = true;
        up3 = true;

        r1 = new Rect(0, 0, 0, 0);
        r2 = new Rect(0, 0, 0, 0);
        r3 = new Rect(0, 0, 0, 0);
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(colors[0]);
        int unitH = getHeight() / 20;
        int unitW = getWidth() / 5;
        drawRect1(canvas, unitW, unitH);
        drawRect2(canvas, unitW, unitH);
        drawRect3(canvas, unitW, unitH);
        if (isplaying) {
            postInvalidate();
        }
    }

    private void drawRect1(Canvas canvas, int unitW, int unitH) {
        r1.set(0, getHeight() - h1 * unitH, unitW, getHeight());
        canvas.drawRect(r1, paint);
        if (up1) {
            h1 += 1;
        } else {
            h1 -= 1;
        }
        if (h1 > 20 || h1 == 0) {
            up1 = !up1;
        }
    }

    private void drawRect2(Canvas canvas, int unitW, int unitH) {
        r2.set(unitW * 2, getHeight() - h2 * unitH, unitW * 3, getHeight());
        canvas.drawRect(r2, paint);
        if (up2) {
            h2 += 1;
        } else {
            h2 -= 1;
        }
        if (h2 > 20 || h2 <= 0) {
            up2 = !up2;
        }

    }

    private void drawRect3(Canvas canvas, int unitW, int unitH) {
        r3.set(unitW * 4, getHeight() - h3 * unitH, unitW * 5, getHeight());
        canvas.drawRect(r3, paint);
        if (up3) {
            h3 += 1;
        } else {
            h3 -= 1;
        }
        if (h3 > 20 || h3 <= 0) {
            up3 = !up3;
        }
    }


    public void start() {
        isplaying = true;
        postInvalidateDelayed(100);
    }

    public void stop() {
        isplaying = false;
    }
}
