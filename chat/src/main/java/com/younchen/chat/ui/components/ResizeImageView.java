package com.younchen.chat.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.younchen.chat.utils.DimenUtils;
import com.younchen.chat.utils.YounLog;
import com.younchen.utils.DimenUtil;

/**
 * Created longquan pc on 2016/4/25.
 */
public class ResizeImageView extends ImageView {

    private int measuredWith;
    private int measuredHeight;

    public ResizeImageView(Context context) {
        super(context);
    }

    public ResizeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int w = MeasureSpec.getSize(widthMeasureSpec);
//        int h = MeasureSpec.getSize(heightMeasureSpec);
//        // measuredHeight = (measuredWith * DimenUtils.getRealHeight() / DimenUtils.getRealWidth());
//        double scale = 0;
//        YounLog.i("longquan", "ResizeImageView onMesure w:" + w + " onMesure h:" + h);
//        if (w != 0 && h != 0) {
//            scale = (w * 1.0 / DimenUtils.getRealWidth());
//            int newHeight = (int) (h / scale);
//            setMeasuredDimension(DimenUtils.getRealWidth(), newHeight);
//        } else
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        YounLog.i("longquan", "onLayout:" + getMeasuredWidth() + " h:" + getMeasuredHeight());
        //setMeasuredDimension(measuredWith, 700);
        super.onLayout(changed, left, top, right, bottom);
    }
}
