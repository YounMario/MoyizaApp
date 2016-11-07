package com.younchen.chat.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.younchen.chat.App;
import com.younchen.chat.R;
import com.younchen.chat.utils.DimenUtils;
import com.younchen.chat.utils.YounLog;

/**
 * Created by pc on 2016/4/26.
 */
public class DynamicHeightImageView extends ImageView {

    private float mHeightRatio;

    public DynamicHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DynamicHeightImageView, 0, 0);
        try {
            mHeightRatio = ta.getFloat(R.styleable.DynamicHeightImageView_height_ratio, 0.0f);
        } finally {
            ta.recycle();
        }
    }

    public DynamicHeightImageView(Context context) {
        super(context);
    }

    public void setHeightRatio(float ratio) {
        if (ratio != mHeightRatio) {
            mHeightRatio = ratio;
            requestLayout();
        }
    }

    public double getHeightRatio() {
        return mHeightRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHeightRatio > 0.0) {
            // set the image views size
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * mHeightRatio);
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
