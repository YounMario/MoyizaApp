package com.younchen.chat.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.younchen.chat.R;
import com.younchen.chat.utils.DimenUtils;

/**
 * Created by pc on 2016/3/24.
 */
public class LineVertical extends ImageView {

    private int width;
    private int height;
    private Paint paint;

    public LineVertical(Context context) {
        this(context, null);
    }

    public LineVertical(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineVertical(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        width = DimenUtils.getWindowWidth();
        paint = new Paint();
        paint.setColor(getContext().getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rect = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRect(rect, paint);
        super.onDraw(canvas);
    }
}
